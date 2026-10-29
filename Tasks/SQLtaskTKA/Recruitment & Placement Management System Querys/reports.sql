
USE recruitment_placement_db;

SELECT primary_technology, COUNT(*) AS candidate_count
FROM candidates
GROUP BY primary_technology
ORDER BY candidate_count DESC;

SELECT c.company_name, j.job_code, j.job_title, j.location, j.openings, j.application_deadline
FROM jobs j
JOIN companies c ON c.company_id = j.company_id
WHERE j.status = 'OPEN'
ORDER BY c.company_name, j.job_title;

SELECT application_status, COUNT(*) AS total
FROM applications
GROUP BY application_status
ORDER BY total DESC;

SELECT stage, COUNT(DISTINCT application_id) AS applications_reached
FROM (
    SELECT application_id, new_status AS stage FROM application_history
    WHERE new_status IN ('APPLIED','ELIGIBILITY_VERIFIED','SHORTLISTED',
                         'INTERVIEW_SCHEDULED','INTERVIEWED','SELECTED','OFFERED','JOINED')
) s
GROUP BY stage
ORDER BY FIELD(stage,'APPLIED','ELIGIBILITY_VERIFIED','SHORTLISTED',
               'INTERVIEW_SCHEDULED','INTERVIEWED','SELECTED','OFFERED','JOINED');

SELECT c.company_name, COUNT(p.placement_id) AS placed_count,
       ROUND(AVG(p.final_package_lpa), 2) AS avg_package_lpa
FROM placements p
JOIN companies c ON c.company_id = p.company_id
WHERE p.joining_status = 'JOINED'
GROUP BY c.company_id, c.company_name
ORDER BY placed_count DESC;

SELECT cd.primary_technology, COUNT(p.placement_id) AS placed_count
FROM placements p
JOIN candidates cd ON cd.candidate_id = p.candidate_id
WHERE p.joining_status = 'JOINED'
GROUP BY cd.primary_technology
ORDER BY placed_count DESC;

SELECT u.full_name AS recruiter,
       COUNT(*)                                              AS applications_handled,
       SUM(a.application_status IN ('SELECTED','OFFERED','JOINED')) AS selected_or_beyond,
       SUM(a.application_status = 'JOINED')                  AS joined,
       SUM(a.application_status = 'REJECTED')                AS rejected
FROM applications a
JOIN users u ON u.user_id = a.assigned_recruiter_id
GROUP BY u.user_id, u.full_name
ORDER BY joined DESC, selected_or_beyond DESC;

SELECT ir.scheduled_date, ir.scheduled_time, cd.full_name AS candidate,
       j.job_title, co.company_name, ir.round_type, ir.mode, ir.interviewer_name
FROM interview_rounds ir
JOIN applications a ON a.application_id = ir.application_id
JOIN candidates  cd ON cd.candidate_id  = a.candidate_id
JOIN jobs        j  ON j.job_id         = a.job_id
JOIN companies   co ON co.company_id    = j.company_id
WHERE ir.status = 'SCHEDULED'
ORDER BY ir.scheduled_date, ir.scheduled_time;

SELECT COUNT(o.offer_id)                                   AS total_offers,
       SUM(p.joining_status = 'JOINED')                    AS joined,
       ROUND(100 * SUM(p.joining_status = 'JOINED') / COUNT(o.offer_id), 2) AS conversion_pct
FROM offers o
LEFT JOIN placements p ON p.offer_id = o.offer_id;

SELECT j.job_code, j.job_title, c.company_name, j.posted_date,
       DATEDIFF(CURDATE(), j.posted_date) AS days_open
FROM jobs j
JOIN companies c ON c.company_id = j.company_id
WHERE j.status = 'OPEN'
ORDER BY days_open DESC;


SELECT cd.candidate_code, cd.full_name, cd.percentage, cd.passing_year, cd.backlog_count
FROM candidates cd
JOIN jobs j ON j.job_id = 7
WHERE cd.profile_status = 'ACTIVE'
  AND cd.percentage   >= j.min_percentage
  AND cd.passing_year BETWEEN j.min_passing_year AND j.max_passing_year
  AND cd.backlog_count <= j.max_backlogs;

SELECT cd.full_name, COUNT(*) AS matched_skills
FROM job_skills js
JOIN candidate_skills cs ON cs.skill_id = js.skill_id
JOIN candidates cd       ON cd.candidate_id = cs.candidate_id
WHERE js.job_id = 7
GROUP BY cd.candidate_id, cd.full_name
ORDER BY matched_skills DESC, cd.full_name;

SELECT co.company_name, ROUND(AVG(f.overall_rating), 2) AS avg_overall_rating, COUNT(*) AS feedback_count
FROM interview_feedback f
JOIN interview_rounds ir ON ir.interview_id = f.interview_id
JOIN applications a      ON a.application_id = ir.application_id
JOIN jobs j              ON j.job_id = a.job_id
JOIN companies co        ON co.company_id = j.company_id
GROUP BY co.company_id, co.company_name
ORDER BY avg_overall_rating DESC;
