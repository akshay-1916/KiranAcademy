USE recruitment_db;

INSERT INTO companies(name, industry, location, email) VALUES
('Infosys', 'IT Services', 'Pune', 'hr@infosys.com'),
('TCS', 'IT Services', 'Mumbai', 'hr@tcs.com'),
('Persistent Systems', 'Product', 'Pune', 'careers@persistent.com');

INSERT INTO candidates(name, email, mobile, technology, percentage, passing_year, backlogs, active) VALUES
('Rohan Patil', 'rohan.patil@gmail.com', '9876500001', 'Java', 78.50, 2026, 0, 1),
('Sneha Kulkarni', 'sneha.k@gmail.com', '9876500002', 'Java', 65.00, 2026, 1, 1),
('Amit Deshmukh', 'amit.d@gmail.com', '9876500003', 'Python', 82.25, 2026, 0, 1),
('Priya Shinde', 'priya.s@gmail.com', '9876500004', 'Testing', 55.00, 2025, 2, 1),
('Vikas More', 'vikas.m@gmail.com', '9876500005', 'Java', 70.00, 2026, 0, 0);

INSERT INTO jobs(company_id, title, technology, openings, location, package_lpa, min_percentage, passing_year, max_backlogs, deadline, status) VALUES
(1, 'Java Developer', 'Java', 5, 'Pune', 4.50, 60.00, 2026, 0, '2027-12-31', 'OPEN'),
(2, 'Software Engineer', 'Java', 10, 'Mumbai', 3.60, 55.00, 2026, 1, '2027-12-31', 'OPEN'),
(3, 'Python Developer', 'Python', 3, 'Pune', 6.00, 70.00, 2026, 0, '2027-12-31', 'OPEN');
