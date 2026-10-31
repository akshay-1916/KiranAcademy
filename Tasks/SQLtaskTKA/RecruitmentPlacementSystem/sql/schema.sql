CREATE DATABASE IF NOT EXISTS recruitment_db;
USE recruitment_db;

CREATE TABLE companies (
    company_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    industry VARCHAR(60),
    location VARCHAR(60),
    email VARCHAR(100) NOT NULL
) AUTO_INCREMENT = 1;

CREATE TABLE candidates (
    candidate_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    mobile CHAR(10) NOT NULL UNIQUE,
    technology VARCHAR(50) NOT NULL,
    percentage DECIMAL(5,2) NOT NULL CHECK (percentage BETWEEN 0 AND 100),
    passing_year INT NOT NULL,
    backlogs INT NOT NULL DEFAULT 0 CHECK (backlogs >= 0),
    active TINYINT(1) NOT NULL DEFAULT 1
) AUTO_INCREMENT = 1001;

CREATE TABLE jobs (
    job_id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    technology VARCHAR(50),
    openings INT NOT NULL,
    location VARCHAR(60),
    package_lpa DECIMAL(6,2) NOT NULL,
    min_percentage DECIMAL(5,2) NOT NULL,
    passing_year INT NOT NULL,
    max_backlogs INT NOT NULL DEFAULT 0,
    deadline DATE NOT NULL,
    status VARCHAR(10) NOT NULL DEFAULT 'OPEN',
    FOREIGN KEY (company_id) REFERENCES companies(company_id)
) AUTO_INCREMENT = 101;

CREATE TABLE applications (
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    candidate_id INT NOT NULL,
    job_id INT NOT NULL,
    status VARCHAR(15) NOT NULL DEFAULT 'APPLIED',
    applied_date DATE NOT NULL,
    UNIQUE KEY uq_candidate_job (candidate_id, job_id),
    FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id),
    FOREIGN KEY (job_id) REFERENCES jobs(job_id)
) AUTO_INCREMENT = 501;

CREATE TABLE interviews (
    interview_id INT AUTO_INCREMENT PRIMARY KEY,
    application_id INT NOT NULL,
    round_no INT NOT NULL,
    interview_date DATE NOT NULL,
    mode VARCHAR(10) NOT NULL,
    interviewer VARCHAR(60) NOT NULL,
    status VARCHAR(15) NOT NULL DEFAULT 'SCHEDULED',
    technical_rating INT DEFAULT 0,
    communication_rating INT DEFAULT 0,
    remarks VARCHAR(255),
    result VARCHAR(15),
    FOREIGN KEY (application_id) REFERENCES applications(application_id)
);

CREATE TABLE offers (
    offer_id INT AUTO_INCREMENT PRIMARY KEY,
    application_id INT NOT NULL UNIQUE,
    designation VARCHAR(60) NOT NULL,
    package_lpa DECIMAL(6,2) NOT NULL,
    offer_date DATE NOT NULL,
    joining_date DATE NOT NULL,
    status VARCHAR(15) NOT NULL DEFAULT 'OFFERED',
    FOREIGN KEY (application_id) REFERENCES applications(application_id)
);

CREATE TABLE placements (
    placement_id INT AUTO_INCREMENT PRIMARY KEY,
    application_id INT NOT NULL UNIQUE,
    candidate_id INT NOT NULL,
    company_id INT NOT NULL,
    designation VARCHAR(60) NOT NULL,
    final_package DECIMAL(6,2) NOT NULL,
    joining_date DATE NOT NULL,
    joining_status VARCHAR(15) NOT NULL,
    FOREIGN KEY (application_id) REFERENCES applications(application_id),
    FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id),
    FOREIGN KEY (company_id) REFERENCES companies(company_id)
);
