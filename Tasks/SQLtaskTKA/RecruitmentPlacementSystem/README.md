# Recruitment & Placement Management System

Console application built with Java, JDBC and MySQL.

## Project Structure

    src/
      app/         RecruitmentApp (console menu)
      service/     business logic and eligibility / workflow rules
      dao/         JDBC database operations using PreparedStatement
      model/       Candidate, Company, Job, Application, Interview, Offer, Placement
      exception/   CandidateNotFound, JobNotFound, DuplicateApplication, CandidateNotEligible
      util/        DBConnection, ActivityLogger
    sql/           schema.sql, sample_data.sql
    docs/          er_diagram.mermaid
    prototype/     index.html, style.css
    test_cases.md

## Requirements

- JDK 8 or higher
- MySQL 8
- MySQL Connector/J jar (mysql-connector-j-8.x.jar)

## Setup

1. Run the scripts in MySQL:

        mysql -u root -p < sql/schema.sql
        mysql -u root -p < sql/sample_data.sql

2. Open `src/util/DBConnection.java` and set your MySQL user and password.

3. Compile (Windows use `;` instead of `:` in the classpath):

        mkdir out
        javac -cp mysql-connector-j-8.x.jar -d out $(find src -name "*.java")

4. Run from the project folder so activity_log.txt is created there:

        java -cp out:mysql-connector-j-8.x.jar app.RecruitmentApp

In Eclipse / IntelliJ add the connector jar to the build path and run `RecruitmentApp`.

## Recruitment Workflow

APPLIED -> SHORTLISTED -> INTERVIEW -> SELECTED -> OFFERED -> JOINED

REJECTED is possible from any stage before JOINED. Direct jumps such as APPLIED -> JOINED are blocked.

How the flow runs in the menu:

1. Applications > Apply
2. Applications > Shortlist
3. Interviews > Schedule (moves to INTERVIEW)
4. Interviews > Save Feedback with result SELECTED (moves to SELECTED)
5. Offers > Create Offer (moves to OFFERED), then Accept Offer
6. Placements > Confirm Joining (moves to JOINED, placement saved, job openings reduced)

## Eligibility Rules

Candidate active, job open, deadline not passed, not already applied, percentage at least the job minimum, passing year equal to job passing year, backlogs not more than job maximum.

## Activity Log

Actions are appended to `activity_log.txt`, for example:

    Candidate Created: CAN1001
    Job Created: JOB101
    Candidate Shortlisted: APP501

## Sample Data

sample_data.sql adds 3 companies, 5 candidates and 3 jobs. Candidate ids start at 1001, job ids at 101 and application ids at 501.

## Screenshots

Run the application and save screenshots of each module in a `screenshots` folder.
