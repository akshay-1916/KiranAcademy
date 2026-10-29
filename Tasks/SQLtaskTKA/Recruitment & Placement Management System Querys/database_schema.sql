

CREATE DATABASE recruitment_placement_db
   
USE recruitment_placement_db;

CREATE TABLE roles (
    role_id     INT          NOT NULL AUTO_INCREMENT,
    role_code   VARCHAR(30)  NOT NULL,
    role_name   VARCHAR(80)  NOT NULL,
    is_active   TINYINT(1)   NOT NULL DEFAULT 1,
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_roles PRIMARY KEY (role_id),
    CONSTRAINT uq_roles_code UNIQUE (role_code)
) ENGINE=InnoDB;


CREATE TABLE users (
    user_id        INT          NOT NULL AUTO_INCREMENT,
    username       VARCHAR(80)  NOT NULL,
    password_hash  VARCHAR(255) NOT NULL,           -- hash only, never plain text
    full_name      VARCHAR(120) NOT NULL,
    email          VARCHAR(150) NULL,
    role_id        INT          NOT NULL,
    status         VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    last_login_at  DATETIME     NULL,
    created_at     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_users PRIMARY KEY (user_id),
    CONSTRAINT uq_users_username UNIQUE (username),
    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles(role_id),
    CONSTRAINT chk_users_status CHECK (status IN ('ACTIVE','INACTIVE','LOCKED'))
) ENGINE=InnoDB;

CREATE TABLE qualifications (
    qualification_id    INT          NOT NULL AUTO_INCREMENT,
    qualification_code  VARCHAR(30)  NOT NULL,
    qualification_name  VARCHAR(100) NOT NULL,
    is_active           TINYINT(1)   NOT NULL DEFAULT 1,
    CONSTRAINT pk_qualifications PRIMARY KEY (qualification_id),
    CONSTRAINT uq_qualifications_code UNIQUE (qualification_code)
) ENGINE=InnoDB;


CREATE TABLE locations (
    location_id    INT          NOT NULL AUTO_INCREMENT,
    city           VARCHAR(100) NOT NULL,
    state          VARCHAR(100) NULL,
    country        VARCHAR(100) NOT NULL DEFAULT 'India',
    is_active      TINYINT(1)   NOT NULL DEFAULT 1,
    CONSTRAINT pk_locations PRIMARY KEY (location_id),
    CONSTRAINT uq_locations_city UNIQUE (city, state, country)
) ENGINE=InnoDB;


CREATE TABLE candidates (
    candidate_id        INT           NOT NULL AUTO_INCREMENT,
    candidate_code      VARCHAR(30)   NOT NULL,            -- e.g. CAN-2026-0001
    full_name           VARCHAR(150)  NOT NULL,
    email               VARCHAR(150)  NOT NULL,
    mobile              VARCHAR(20)   NOT NULL,
    date_of_birth       DATE          NULL,
    gender              VARCHAR(20)   NULL,
    qualification       VARCHAR(80)   NOT NULL,
    college             VARCHAR(180)  NULL,
    university          VARCHAR(180)  NULL,
    passing_year        SMALLINT      NOT NULL,
    percentage          DECIMAL(5,2)  NULL,
    cgpa                DECIMAL(4,2)  NULL,
    backlog_count       INT           NOT NULL DEFAULT 0,
    primary_technology  VARCHAR(80)   NULL,
    current_location    VARCHAR(100)  NULL,
    preferred_location  VARCHAR(100)  NULL,
    profile_status      VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    placement_status    VARCHAR(20)   NOT NULL DEFAULT 'NOT_PLACED',
    created_at          DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP
                                      ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_candidates PRIMARY KEY (candidate_id),
    CONSTRAINT uq_candidates_code   UNIQUE (candidate_code),
    CONSTRAINT uq_candidates_email  UNIQUE (email),
    CONSTRAINT uq_candidates_mobile UNIQUE (mobile),
    CONSTRAINT chk_cand_percentage  CHECK (percentage IS NULL OR percentage BETWEEN 0 AND 100),
    CONSTRAINT chk_cand_backlog     CHECK (backlog_count >= 0),
    CONSTRAINT chk_cand_profile     CHECK (profile_status IN ('ACTIVE','INACTIVE','ON_HOLD','BLACKLISTED')),
    CONSTRAINT chk_cand_placement   CHECK (placement_status IN ('NOT_PLACED','PLACED'))
) ENGINE=InnoDB;

CREATE INDEX idx_cand_primary_tech    ON candidates(primary_technology);
CREATE INDEX idx_cand_passing_year    ON candidates(passing_year);
CREATE INDEX idx_cand_placement_status ON candidates(placement_status);


CREATE TABLE skills (
    skill_id    INT          NOT NULL AUTO_INCREMENT,
    skill_name  VARCHAR(100) NOT NULL,
    category    VARCHAR(60)  NULL,
    is_active   TINYINT(1)   NOT NULL DEFAULT 1,
    CONSTRAINT pk_skills PRIMARY KEY (skill_id),
    CONSTRAINT uq_skills_name UNIQUE (skill_name)
) ENGINE=InnoDB;


CREATE TABLE candidate_skills (
    candidate_skill_id  INT           NOT NULL AUTO_INCREMENT,
    candidate_id        INT           NOT NULL,
    skill_id            INT           NOT NULL,
    proficiency_level   VARCHAR(20)   NULL,
    years_experience    DECIMAL(4,1)  NOT NULL DEFAULT 0,
    is_primary          TINYINT(1)    NOT NULL DEFAULT 0,
    CONSTRAINT pk_candidate_skills PRIMARY KEY (candidate_skill_id),
    CONSTRAINT uq_candidate_skill UNIQUE (candidate_id, skill_id),
    CONSTRAINT fk_cs_candidate FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id),
    CONSTRAINT fk_cs_skill     FOREIGN KEY (skill_id)     REFERENCES skills(skill_id),
    CONSTRAINT chk_cs_level CHECK (proficiency_level IS NULL
                                   OR proficiency_level IN ('BASIC','INTERMEDIATE','ADVANCED'))
) ENGINE=InnoDB;

CREATE INDEX idx_cs_candidate ON candidate_skills(candidate_id);
CREATE INDEX idx_cs_skill     ON candidate_skills(skill_id);

CREATE TABLE companies (
    company_id    INT           NOT NULL AUTO_INCREMENT,
    company_code  VARCHAR(30)   NOT NULL,                  -- e.g. COM-001
    company_name  VARCHAR(180)  NOT NULL,
    industry      VARCHAR(100)  NULL,
    website       VARCHAR(200)  NULL,
    location      VARCHAR(120)  NULL,
    company_type  VARCHAR(30)   NULL,
    hr_name       VARCHAR(120)  NULL,
    hr_email      VARCHAR(150)  NULL,
    hr_mobile     VARCHAR(20)   NULL,
    status        VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_companies PRIMARY KEY (company_id),
    CONSTRAINT uq_companies_code UNIQUE (company_code),
    CONSTRAINT chk_comp_type   CHECK (company_type IS NULL
                                      OR company_type IN ('MNC','STARTUP','MID_SIZE','CONSULTANCY')),
    CONSTRAINT chk_comp_status CHECK (status IN ('ACTIVE','INACTIVE'))
) ENGINE=InnoDB;

CREATE INDEX idx_comp_name   ON companies(company_name);
CREATE INDEX idx_comp_status ON companies(status);


CREATE TABLE jobs (
    job_id                INT           NOT NULL AUTO_INCREMENT,
    job_code              VARCHAR(40)   NOT NULL,
    company_id            INT           NOT NULL,
    job_title             VARCHAR(150)  NOT NULL,
    employment_type       VARCHAR(30)   NOT NULL DEFAULT 'FULL_TIME',
    location              VARCHAR(120)  NOT NULL,
    openings              INT           NOT NULL,
    min_package_lpa       DECIMAL(6,2)  NULL,
    max_package_lpa       DECIMAL(6,2)  NULL,
    min_percentage        DECIMAL(5,2)  NULL,
    min_passing_year      SMALLINT      NULL,
    max_passing_year      SMALLINT      NULL,
    max_backlogs          INT           NOT NULL DEFAULT 0,
    experience_min_years  DECIMAL(4,1)  NOT NULL DEFAULT 0,
    qualification_rule    VARCHAR(255)  NULL,
    posted_date           DATE          NOT NULL,
    application_deadline  DATE          NOT NULL,
    status                VARCHAR(20)   NOT NULL DEFAULT 'OPEN',
    created_by            INT           NOT NULL,
    created_at            DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_jobs PRIMARY KEY (job_id),
    CONSTRAINT uq_jobs_code UNIQUE (job_code),
    CONSTRAINT fk_jobs_company FOREIGN KEY (company_id) REFERENCES companies(company_id),
    CONSTRAINT fk_jobs_creator FOREIGN KEY (created_by) REFERENCES users(user_id),
    CONSTRAINT chk_jobs_openings  CHECK (openings > 0),
    CONSTRAINT chk_jobs_package   CHECK (min_package_lpa IS NULL OR max_package_lpa IS NULL
                                         OR min_package_lpa <= max_package_lpa),
    CONSTRAINT chk_jobs_pct       CHECK (min_percentage IS NULL OR min_percentage BETWEEN 0 AND 100),
    CONSTRAINT chk_jobs_deadline  CHECK (application_deadline >= posted_date),
    CONSTRAINT chk_jobs_backlogs  CHECK (max_backlogs >= 0),
    CONSTRAINT chk_jobs_emp_type  CHECK (employment_type IN ('FULL_TIME','INTERNSHIP','CONTRACT')),
    CONSTRAINT chk_jobs_status    CHECK (status IN ('DRAFT','OPEN','CLOSED','CANCELLED'))
) ENGINE=InnoDB;

CREATE INDEX idx_jobs_company  ON jobs(company_id);
CREATE INDEX idx_jobs_status   ON jobs(status);
CREATE INDEX idx_jobs_deadline ON jobs(application_deadline);
CREATE INDEX idx_jobs_location ON jobs(location);


CREATE TABLE job_skills (
    job_skill_id    INT          NOT NULL AUTO_INCREMENT,
    job_id          INT          NOT NULL,
    skill_id        INT          NOT NULL,
    minimum_level   VARCHAR(20)  NULL,
    mandatory_flag  TINYINT(1)   NOT NULL DEFAULT 1,
    CONSTRAINT pk_job_skills PRIMARY KEY (job_skill_id),
    CONSTRAINT uq_job_skill UNIQUE (job_id, skill_id),
    CONSTRAINT fk_js_job   FOREIGN KEY (job_id)   REFERENCES jobs(job_id),
    CONSTRAINT fk_js_skill FOREIGN KEY (skill_id) REFERENCES skills(skill_id),
    CONSTRAINT chk_js_level CHECK (minimum_level IS NULL
                                   OR minimum_level IN ('BASIC','INTERMEDIATE','ADVANCED'))
) ;


CREATE TABLE applications (
    application_id         INT           NOT NULL AUTO_INCREMENT,
    application_code       VARCHAR(40)   NOT NULL,
    candidate_id           INT           NOT NULL,
    job_id                 INT           NOT NULL,
    applied_at             DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    eligibility_status     VARCHAR(20)   NOT NULL DEFAULT 'PENDING',
    eligibility_reason     VARCHAR(500)  NULL,
    application_status     VARCHAR(30)   NOT NULL DEFAULT 'APPLIED',
    source                 VARCHAR(50)   NULL,
    assigned_recruiter_id  INT           NULL,
    updated_at             DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP
                                         ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_applications PRIMARY KEY (application_id),
    CONSTRAINT uq_application_code UNIQUE (application_code),
    CONSTRAINT uq_candidate_job    UNIQUE (candidate_id, job_id),   -- no double apply
    CONSTRAINT fk_app_candidate FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id),
    CONSTRAINT fk_app_job       FOREIGN KEY (job_id)       REFERENCES jobs(job_id),
    CONSTRAINT fk_app_recruiter FOREIGN KEY (assigned_recruiter_id) REFERENCES users(user_id),
    CONSTRAINT chk_app_elig   CHECK (eligibility_status IN ('PENDING','ELIGIBLE','NOT_ELIGIBLE')),
    CONSTRAINT chk_app_status CHECK (application_status IN
        ('APPLIED','ELIGIBILITY_VERIFIED','SHORTLISTED','INTERVIEW_SCHEDULED',
         'INTERVIEWED','SELECTED','REJECTED','OFFERED','JOINED','WITHDRAWN'))
) ENGINE=InnoDB;

CREATE INDEX idx_app_status    ON applications(application_status);
CREATE INDEX idx_app_recruiter ON applications(assigned_recruiter_id);


CREATE TABLE application_history (
    history_id      BIGINT        NOT NULL AUTO_INCREMENT,
    application_id  INT           NOT NULL,
    old_status      VARCHAR(30)   NULL,
    new_status      VARCHAR(30)   NOT NULL,
    remarks         VARCHAR(500)  NULL,
    changed_by      INT           NOT NULL,
    changed_at      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_application_history PRIMARY KEY (history_id),
    CONSTRAINT fk_ah_application FOREIGN KEY (application_id) REFERENCES applications(application_id),
    CONSTRAINT fk_ah_user        FOREIGN KEY (changed_by)     REFERENCES users(user_id)
) ENGINE=InnoDB;

CREATE INDEX idx_ah_application ON application_history(application_id);
CREATE INDEX idx_ah_changed_at  ON application_history(changed_at);


CREATE TABLE interview_rounds (
    interview_id         INT           NOT NULL AUTO_INCREMENT,
    application_id       INT           NOT NULL,
    round_number         INT           NOT NULL,
    round_type           VARCHAR(50)   NOT NULL,
    scheduled_date       DATE          NOT NULL,
    scheduled_time       TIME          NULL,
    mode                 VARCHAR(20)   NOT NULL,
    interviewer_name     VARCHAR(120)  NULL,
    meeting_or_location  VARCHAR(255)  NULL,
    status               VARCHAR(20)   NOT NULL DEFAULT 'SCHEDULED',
    created_at           DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_interview_rounds PRIMARY KEY (interview_id),
    CONSTRAINT uq_app_round UNIQUE (application_id, round_number),
    CONSTRAINT fk_ir_application FOREIGN KEY (application_id) REFERENCES applications(application_id),
    CONSTRAINT chk_ir_round  CHECK (round_number > 0),
    CONSTRAINT chk_ir_type   CHECK (round_type IN ('APTITUDE','TECHNICAL','MANAGERIAL','HR')),
    CONSTRAINT chk_ir_mode   CHECK (mode IN ('ONLINE','OFFLINE','TELEPHONIC')),
    CONSTRAINT chk_ir_status CHECK (status IN
        ('SCHEDULED','COMPLETED','RESCHEDULED','CANCELLED','NO_SHOW'))
) ENGINE=InnoDB;

CREATE INDEX idx_ir_date   ON interview_rounds(scheduled_date);
CREATE INDEX idx_ir_status ON interview_rounds(status);

CREATE TABLE interview_feedback (
    feedback_id             INT           NOT NULL AUTO_INCREMENT,
    interview_id            INT           NOT NULL,
    technical_rating        DECIMAL(3,1)  NULL,
    communication_rating    DECIMAL(3,1)  NULL,
    problem_solving_rating  DECIMAL(3,1)  NULL,
    overall_rating          DECIMAL(3,1)  NULL,
    recommendation          VARCHAR(30)   NOT NULL,
    remarks                 TEXT          NULL,
    submitted_by            INT           NOT NULL,
    submitted_at            DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_interview_feedback PRIMARY KEY (feedback_id),
    CONSTRAINT uq_feedback_interview UNIQUE (interview_id),
    CONSTRAINT fk_if_interview FOREIGN KEY (interview_id) REFERENCES interview_rounds(interview_id),
    CONSTRAINT fk_if_user      FOREIGN KEY (submitted_by) REFERENCES users(user_id),
    CONSTRAINT chk_if_tech  CHECK (technical_rating       IS NULL OR technical_rating       BETWEEN 0 AND 5),
    CONSTRAINT chk_if_comm  CHECK (communication_rating   IS NULL OR communication_rating   BETWEEN 0 AND 5),
    CONSTRAINT chk_if_ps    CHECK (problem_solving_rating IS NULL OR problem_solving_rating BETWEEN 0 AND 5),
    CONSTRAINT chk_if_ovr   CHECK (overall_rating         IS NULL OR overall_rating         BETWEEN 0 AND 5),
    CONSTRAINT chk_if_rec   CHECK (recommendation IN ('NEXT_ROUND','SELECTED','REJECTED','ON_HOLD'))
) ENGINE=InnoDB;

CREATE INDEX idx_if_recommendation ON interview_feedback(recommendation);


CREATE TABLE offers (
    offer_id               INT           NOT NULL AUTO_INCREMENT,
    offer_code             VARCHAR(40)   NOT NULL,
    application_id         INT           NOT NULL,
    designation            VARCHAR(120)  NOT NULL,
    offered_package_lpa    DECIMAL(6,2)  NOT NULL,
    offer_date             DATE          NOT NULL,
    expected_joining_date  DATE          NULL,
    offer_status           VARCHAR(20)   NOT NULL DEFAULT 'OFFERED',
    remarks                VARCHAR(500)  NULL,
    created_at             DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_offers PRIMARY KEY (offer_id),
    CONSTRAINT uq_offer_code UNIQUE (offer_code),
    CONSTRAINT uq_offer_application UNIQUE (application_id),
    CONSTRAINT fk_offer_application FOREIGN KEY (application_id) REFERENCES applications(application_id),
    CONSTRAINT chk_offer_pkg    CHECK (offered_package_lpa > 0),
    CONSTRAINT chk_offer_status CHECK (offer_status IN
        ('OFFERED','ACCEPTED','REJECTED','EXPIRED','WITHDRAWN'))
) ENGINE=InnoDB;

CREATE INDEX idx_offer_status ON offers(offer_status);


CREATE TABLE placements (
    placement_id           INT           NOT NULL AUTO_INCREMENT,
    candidate_id           INT           NOT NULL,
    company_id             INT           NOT NULL,
    job_id                 INT           NOT NULL,
    offer_id               INT           NOT NULL,
    designation            VARCHAR(120)  NOT NULL,
    final_package_lpa      DECIMAL(6,2)  NOT NULL,
    expected_joining_date  DATE          NULL,
    actual_joining_date    DATE          NULL,
    joining_status         VARCHAR(20)   NOT NULL DEFAULT 'PENDING',
    placement_status       VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at             DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_placements PRIMARY KEY (placement_id),
    CONSTRAINT uq_placement_offer UNIQUE (offer_id),
    CONSTRAINT fk_pl_candidate FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id),
    CONSTRAINT fk_pl_company   FOREIGN KEY (company_id)   REFERENCES companies(company_id),
    CONSTRAINT fk_pl_job       FOREIGN KEY (job_id)       REFERENCES jobs(job_id),
    CONSTRAINT fk_pl_offer     FOREIGN KEY (offer_id)     REFERENCES offers(offer_id),
    CONSTRAINT chk_pl_pkg     CHECK (final_package_lpa > 0),
    CONSTRAINT chk_pl_joining CHECK (joining_status IN
        ('PENDING','JOINED','NOT_JOINED','DEFERRED','NO_SHOW')),
    CONSTRAINT chk_pl_status  CHECK (placement_status IN ('ACTIVE','CANCELLED'))
) ENGINE=InnoDB;

CREATE INDEX idx_pl_candidate ON placements(candidate_id);
CREATE INDEX idx_pl_company   ON placements(company_id);
CREATE INDEX idx_pl_joining   ON placements(joining_status);


CREATE TABLE activity_logs (
    log_id       BIGINT        NOT NULL AUTO_INCREMENT,
    user_id      INT           NULL,
    module_name  VARCHAR(60)   NOT NULL,
    action_name  VARCHAR(80)   NOT NULL,
    record_id    VARCHAR(60)   NULL,
    description  VARCHAR(500)  NULL,
    logged_at    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_activity_logs PRIMARY KEY (log_id),
    CONSTRAINT fk_al_user FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=InnoDB;

CREATE INDEX idx_al_user   ON activity_logs(user_id);
CREATE INDEX idx_al_module ON activity_logs(module_name);
CREATE INDEX idx_al_logged ON activity_logs(logged_at);
