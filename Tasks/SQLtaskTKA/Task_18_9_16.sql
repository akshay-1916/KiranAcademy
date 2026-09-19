use company_db;

create table employees1 (
employee_id int primary key,
employee_name varchar(100) not null,
department varchar(50),
salary decimal(10,2),
city varchar(50),
joining_date date,
email varchar(100) null,
status varchar(20)
);

desc employees1;

insert into employees1 (employee_id, employee_name,department,salary,city, joining_date, email,status)

values
(101, 'Akshay Patil', 'IT', 65000.00, 'Pune','2023-06-15', 'akshay.patil@gmail.com', 'Active'),
 (102, 'Priya Sharma', 'HR', 42000.00, 'Mumbai', '2022-03-10', 'priya.sharma@company.com', 'Active'),
(103, 'Rahul Deshmukh', 'Sales', 38000.00, 'Nashik', '2024-01-20', NULL, 'Active'),
(104, 'Sneha Kulkarni', 'Testing', 55000.00, 'Pune', '2023-09-05', 'sneha.kulkarni@company.com', 'Active'),
(105, 'Amit Joshi', 'Support', 32000.00, 'Mumbai', '2021-11-12', 'amit.joshi@company.com', 'Inactive'),
(106, 'Neha Pawar', 'Finance', 72000.00, 'Solapur', '2020-07-18', 'neha.pawar@company.com', 'Active'),
(107, 'Rohan Jadhav', 'IT', 85000.00, 'Pune', '2022-08-22', 'rohan.jadhav@company.com', 'Active'),
(108, 'Pooja More', 'HR', 45000.00, 'Kolhapur', '2023-04-11', NULL, 'Active'),
(109, 'Sagar Shinde', 'Sales', 50000.00, 'Mumbai', '2021-02-14', 'sagar.shinde@company.com', 'Active'),
(110, 'Kavita Gaikwad', 'Testing', 60000.00, 'Nashik', '2022-12-01', 'kavita.gaikwad@company.com', 'Inactive'),
(111, 'Nikhil Chavan', 'Support', 35000.00, 'Pune', '2024-02-19', NULL, 'Active'),
(112, 'Rutuja Bhosale', 'Finance', 78000.00, 'Mumbai', '2020-05-25', 'rutuja.bhosale@company.com', 'Active'),
(113, 'Omkar Salunkhe', 'IT', 95000.00, 'Solapur', '2019-10-07', 'omkar.salunkhe@company.com', 'Active'),
(114, 'Snehal Thorat', 'Sales', 47000.00, 'Pune', '2023-07-13', 'snehal.thorat@company.com', 'Inactive'),
(115, 'Kiran Mane', 'Testing', 58000.00, 'Mumbai', '2022-06-16', NULL, 'Active'),
(116, 'Vaishnavi Kadam', 'HR', 40000.00, 'Nashik', '2024-03-04', 'vaishnavi.kadam@company.com', 'Active'),
(117, 'Prasad Gawade', 'Support', 30000.00, 'Kolhapur', '2021-09-28', 'prasad.gawade@company.com', 'Active'),
(118, 'Anjali Khot', 'Finance', 68000.00, 'Pune', '2020-12-15', 'anjali.khot@company.com', 'Inactive'),
(119, 'Vishal Nikam', 'IT', 90000.00, 'Mumbai', '2019-04-09', NULL, 'Active'),
(120, 'Mrunal Sutar', 'Sales', 52000.00, 'Solapur', '2023-11-21', 'mrunal.sutar@company.com', 'Active'),
(121, 'Abhishek More', 'Testing', 62000.00, 'Pune', '2021-08-17', 'abhishek.more@company.com', 'Active'),
(122, 'Dipali Wagh', 'HR', 48000.00, 'Mumbai', '2022-10-03', NULL, 'Inactive'),
(123, 'Tejas Kulkarni', 'Support', 28000.00, 'Nashik', '2024-05-14', 'tejas.kulkarni@company.com', 'Active'),
(124, 'Sonal Patil', 'Finance', 82000.00, 'Pune', '2020-02-27', 'sonal.patil@company.com', 'Active'),
(125, 'Ganesh Shinde', 'IT', 75000.00, 'Kolhapur', '2021-06-08', NULL, 'Inactive');

select * from employees1 where city='Pune';
select * from employees1 where city='Mumbai';
select * from employees1 where department='IT';
select * from employees1 where department='HR';
select * from employees1 where department='Sales';
select * from employees1 where status='Active';
select * from employees1 where status='Inactive';
select * from employees1 where employee_id=103;
select * from employees1 where employee_name='Priya Sharma';
select * from employees1 where salary=35000;
select * from employees1 where city != 'Pune';
select * from employees1 where department <> 'Testing';

select * from employees1 where salary>40000;
select * from employees1 where salary<35000;
select * from employees1 where salary>=50000;
select * from employees1 where salary<=30000;
select * from employees1 where joining_date>'2025-01-01';
select * from employees1 where joining_date>='2024-12-31';
select * from employees1 where employee_id>110;
select * from employees1 where joining_date>='2026-01-01';


select * from employees1 where city='Pune' and status='Active';
select * from employees1 where department="IT" and salary>50000;
select * from employees1 where city = 'mumbai' and status = 'inactive';
select * from employees1 where department = 'sales' and city = 'pune' and salary >= 42000;
select * from employees1 where department = 'hr' and joining_date > '2025-06-01';
select * from employees1 where status = 'active' and salary >= 40000 and salary <= 70000;
select * from employees1 where city = 'mumbai' and department = 'testing' and salary > 38000;
select * from employees1 where status = 'active' and joining_date >= '2026-01-01' and salary > 45000;


select * from employees1 where city = 'pune' or city = 'mumbai';
select * from employees1 where department = 'it' or department = 'hr';
select * from employees1 where salary < 32000 or salary > 60000;
select * from employees1 where city = 'nashik' or salary > 55000;
select * from employees1 where not department="HR";
select * from employees1 where not status="Inactive";
select * from employees1 where (city='Pune' or city="Mumbai") and status='Active';
select * from employees1 where not city='Pune' and salary>40000;

-- Range & Membership Operators (BETWEEN, IN)
select * from employees1 where salary between 35000 and 55000;
select * from employees1 where salary not between 40000 and 65000;
select * from employees1 where joining_date between '2025-01-01' and '2025-12-31';
select * from employees1 where employee_id between 105 and 115;
select * from employees1 where joining_date between '2025-01-01' and '2025-12-31';
select * from employees1 where department in ('IT','HR','Sales');
select * from employees1 where city in ('Pune','Mumbai','Nagpur');
select * from employees1 where department not in ('Testing','Support');
select * from employees1 where city not in ('mumbai', 'nashik');
select * from employees1 where employee_id in (101, 105, 110, 115, 120);
select * from employees1 where department in ('IT','Sales') and salary between 45000 and 75000;


-- Pattern Matching with LIKE & Wildcards (%, _)  
 select * from employees1 where employee_name like 'A%';
 select * from employees1 where employee_name like 'R%';
 select * from employees1 where employee_name like '%a';
 select * from employees1 where employee_name like 'P%a';
 select * from employees1 where employee_name like '_____';
 select * from employees1 where employee_name like '_a%';
 select * from employees1 where employee_name not like 'r%';
 select * from employees1 where employee_name like 'R%';
 
 -- NULL Value Checks (IS NULL & IS NOT NULL) 
select * from employees1 where email is null;
select * from employees1 where email is not null;
select * from employees1 where email is null and city ='Pune';
select * from employees1 where status='Active' and email is not null and salary>40000;

-- Combined Industrial Challenge Queries
select * from employees1 where (status='Active') and city in ('Pune','Mumbai') and (department='IT' or department='Sales') and (salary between 40000 and 70000) and (joining_date>'2025-01-01');
select * from employees1 where (employee_name like 'S%' or employee_name like 'R%') and (email is not null) and (status='Active') and city in ('Pune', 'Nashik');