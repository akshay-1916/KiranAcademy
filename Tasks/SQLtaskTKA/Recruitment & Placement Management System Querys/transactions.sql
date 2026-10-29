
USE recruitment_placement_db;
DROP PROCEDURE IF EXISTS sp_change_application_status;
DROP PROCEDURE IF EXISTS sp_create_offer;
DROP PROCEDURE IF EXISTS sp_confirm_joining;


CREATE PROCEDURE sp_change_application_status(
    IN p_application_id INT, IN p_new_status VARCHAR(30),
    IN p_remarks VARCHAR(500), IN p_user_id INT)
BEGIN
    DECLARE v_old VARCHAR(30);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN ROLLBACK; RESIGNAL; END;

    START TRANSACTION;
    SELECT application_status INTO v_old FROM applications
    WHERE application_id = p_application_id FOR UPDATE;
    UPDATE applications SET application_status = p_new_status
    WHERE application_id = p_application_id;
    INSERT INTO application_history(application_id, old_status, new_status, remarks, changed_by)
    VALUES (p_application_id, v_old, p_new_status, p_remarks, p_user_id);
    COMMIT;
END$$

CREATE PROCEDURE sp_create_offer(
    IN p_application_id INT, IN p_designation VARCHAR(120),
    IN p_package DECIMAL(6,2), IN p_joining DATE, IN p_user_id INT)
BEGIN
    DECLARE v_status VARCHAR(30);
    DECLARE v_offer_id INT;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN ROLLBACK; RESIGNAL; END;

    START TRANSACTION;
    SELECT application_status INTO v_status FROM applications
    WHERE application_id = p_application_id FOR UPDATE;
    IF v_status <> 'SELECTED' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Offer allowed only for SELECTED applications';
    END IF;

    INSERT INTO offers(offer_code, application_id, designation, offered_package_lpa,
                       offer_date, expected_joining_date)
    VALUES (CONCAT('TMP-', UUID()), p_application_id, p_designation, p_package, CURDATE(), p_joining);
    SET v_offer_id = LAST_INSERT_ID();
    UPDATE offers SET offer_code = CONCAT('OFR-2026-', LPAD(v_offer_id, 4, '0'))
    WHERE offer_id = v_offer_id;

    UPDATE applications SET application_status = 'OFFERED' WHERE application_id = p_application_id;
    INSERT INTO application_history(application_id, old_status, new_status, remarks, changed_by)
    VALUES (p_application_id, 'SELECTED', 'OFFERED', 'Offer released', p_user_id);
    COMMIT;
END$$

CREATE PROCEDURE sp_confirm_joining(
    IN p_offer_id INT, IN p_joining_date DATE, IN p_user_id INT)
BEGIN
    DECLARE v_app INT; DECLARE v_status VARCHAR(20);
    DECLARE v_cand INT; DECLARE v_job INT; DECLARE v_comp INT;
    DECLARE v_old VARCHAR(30);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN ROLLBACK; RESIGNAL; END;

    START TRANSACTION;
    SELECT application_id, offer_status INTO v_app, v_status
    FROM offers WHERE offer_id = p_offer_id FOR UPDATE;
    IF v_status <> 'ACCEPTED' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Offer must be ACCEPTED before joining';
    END IF;

    SELECT a.candidate_id, a.job_id, j.company_id, a.application_status
      INTO v_cand, v_job, v_comp, v_old
    FROM applications a JOIN jobs j ON j.job_id = a.job_id
    WHERE a.application_id = v_app;

    INSERT INTO placements(candidate_id, company_id, job_id, offer_id, designation, final_package_lpa,
                           expected_joining_date, actual_joining_date, joining_status)
    SELECT v_cand, v_comp, v_job, o.offer_id, o.designation, o.offered_package_lpa,
           o.expected_joining_date, p_joining_date, 'JOINED'
    FROM offers o WHERE o.offer_id = p_offer_id;

    UPDATE candidates   SET placement_status = 'PLACED'   WHERE candidate_id = v_cand;
    UPDATE applications SET application_status = 'JOINED' WHERE application_id = v_app;
    INSERT INTO application_history(application_id, old_status, new_status, remarks, changed_by)
    VALUES (v_app, v_old, 'JOINED', 'Joining confirmed', p_user_id);
    COMMIT;
END$$

DELIMITER ;


SET @sel := (SELECT MIN(application_id) FROM applications WHERE application_status = 'SELECTED');
CALL sp_create_offer(@sel, 'Software Engineer', 5.50, DATE_ADD(CURDATE(), INTERVAL 45 DAY), 2);
SELECT application_id, application_status FROM applications WHERE application_id = @sel;   -- OFFERED


START TRANSACTION;
UPDATE candidates SET placement_status = 'PLACED' WHERE candidate_id = 1;
SELECT candidate_id, placement_status FROM candidates WHERE candidate_id = 1;  -- PLACED (inside txn)
ROLLBACK;
SELECT candidate_id, placement_status FROM candidates WHERE candidate_id = 1;  -- back to original
