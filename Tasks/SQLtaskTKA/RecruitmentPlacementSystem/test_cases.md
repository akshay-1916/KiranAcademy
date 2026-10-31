# Test Cases

| ID | Module | Input | Expected Result |
|----|--------|-------|-----------------|
| TC01 | Database | Run application with correct DB credentials | Menu is displayed, no connection error |
| TC02 | Database | Wrong password in DBConnection | "Database error" message shown, app does not crash |
| TC03 | Candidate | Add candidate with valid data | Candidate added, id returned, log entry "Candidate Created: CAN<id>" |
| TC04 | Candidate | Add candidate with existing email | "Duplicate email not allowed" |
| TC05 | Candidate | Add candidate with existing mobile | "Duplicate mobile not allowed" |
| TC06 | Candidate | Percentage 105 | "Percentage must be between 0 and 100" |
| TC07 | Candidate | Backlogs -1 | "Backlog count cannot be negative" |
| TC08 | Candidate | Mobile 12345 | "Mobile must be exactly 10 digits" |
| TC09 | Candidate | Update candidate id 9999 | CandidateNotFoundException message |
| TC10 | Candidate | Deactivate candidate 1001 | Candidate shown as INACTIVE |
| TC11 | Company | Add company with valid data | Company added with id |
| TC12 | Job | Create job with company id that does not exist | "Company not found" |
| TC13 | Job | Create job with openings 0 | "Openings must be greater than 0" |
| TC14 | Job | Create job with past deadline | "Deadline cannot be in the past" |
| TC15 | Job | Close job 101 then view open jobs | Job 101 is not listed |
| TC16 | Eligibility | Active candidate meeting all criteria | ELIGIBLE |
| TC17 | Eligibility | Inactive candidate | NOT ELIGIBLE: Candidate is not active |
| TC18 | Eligibility | Candidate percentage below minimum | NOT ELIGIBLE with percentage reason |
| TC19 | Eligibility | Passing year does not match | NOT ELIGIBLE with passing year reason |
| TC20 | Eligibility | Backlogs more than allowed | NOT ELIGIBLE with backlog reason |
| TC21 | Eligibility | Closed job | NOT ELIGIBLE: Job is closed |
| TC22 | Application | Apply with eligible candidate | Application created with status APPLIED |
| TC23 | Application | Apply to same job again | DuplicateApplicationException message |
| TC24 | Application | Apply with ineligible candidate | CandidateNotEligibleException with reason |
| TC25 | Application | Apply with unknown job id | JobNotFoundException message |
| TC26 | Workflow | Shortlist APPLIED application | Status becomes SHORTLISTED |
| TC27 | Workflow | Create offer for APPLIED application | "Invalid" / state error, status unchanged |
| TC28 | Workflow | Confirm joining for APPLIED application | State error, direct jump blocked |
| TC29 | Workflow | Reject a JOINED application | Invalid status change error |
| TC30 | Interview | Schedule interview for SHORTLISTED application | Interview created, application becomes INTERVIEW |
| TC31 | Interview | Schedule interview for APPLIED application | State error |
| TC32 | Interview | Feedback with rating 11 | "Ratings must be between 1 and 10" |
| TC33 | Interview | Feedback result SELECTED | Application becomes SELECTED |
| TC34 | Interview | Feedback result REJECTED | Application becomes REJECTED |
| TC35 | Offer | Create offer for SELECTED application | Offer created, application becomes OFFERED |
| TC36 | Offer | Accept offer | Offer status ACCEPTED |
| TC37 | Offer | Decline offer | Offer DECLINED, application REJECTED |
| TC38 | Placement | Confirm joining after accepted offer | Placement row created, application JOINED, job openings reduced by 1 |
| TC39 | Placement | Confirm joining without accepting offer | "Offer must be accepted before joining" |
| TC40 | Reports | Run all six reports | Each report prints data or "No records found" |
| TC41 | Logging | Create candidate, job, shortlist application | activity_log.txt contains the three entries |
| TC42 | Menu | Enter text instead of number | "Please enter a valid whole number" and prompt again |
| TC43 | Menu | Enter wrong date format | "Date must be in yyyy-MM-dd format" |
| TC44 | Menu | Choose 9 | Application exits |
