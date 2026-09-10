create database company_db;
use company_db;

create table employees(
employee_id int primary key,
employee_name varchar(100),
department varchar(50),
salary decimal(10,2),
city varchar(50),
joining_date date,
status varchar(20)
);


insert into employees
(employee_id, employee_name, department, salary, city, joining_date, status) values
(101, 'Rahul Patil', 'Development', 45000, 'Pune', '2026-01-10', 'Active'),
(102, 'Priya Sharma', 'Testing', 38000, 'Mumbai', '2026-02-15', 'Active'),
(103, 'Amit Joshi', 'Development', 52000, 'Pune', '2025-12-05', 'Active'),
(104, 'Sneha Kulkarni', 'HR', 35000, 'Nashik', '2026-03-20', 'Active'),
(105, 'Rohan Deshmukh', 'Support', 30000, 'Mumbai', '2026-04-01', 'Inactive'),
(106, 'Anjali More', 'Testing', 42000, 'Pune', '2026-05-12', 'Active');

select * from employees;

desc employees;

select employee_name from employees;


select employee_name,salary from employees;
select employee_name,department,city from employees;
select * from employees where city="pune";
select * from employees where city="mumbai";
select * from employees where department="Development";
select * from employees where department="Testing";
select * from employees where status="Active";
select * from employees where status="Inactive";
select * from employees where employee_id=103;
select * from employees where employee_name='Priya Sharma';
select * from employees where salary>40000;
select * from employees where salary<40000;
select * from employees where salary=35000;
select * from employees where salary>=42000;
select * from employees where city='Pune' and status='Active';
select * from employees where department='Development' and salary>45000;
select * from employees where city='Pune' or city='Mumbai';


SET SQL_SAFE_UPDATES = 0;

update employees set salary=48000 where employee_name='Rahul Patil';
update employees set status="Active" where employee_name="Rohan Deshmukh";
update employees set city="Pune" where employee_id = 104;
update employees set department="Development" where employee_id=102;
update employees set salary=45000 where employee_id=103;
update employees set salary=salary+2000 where department='Testing';
update employees set city='Mumbai Branch' where city="Mumbai";

delete from  employees where employee_id=105;
delete from employees where employee_name="Rohan Deshmukh";
delete from employees where status="Inactive";
delete from employees where salary<30000;
delete from employees where employee_id=104;

alter table employees add column email varchar(100);
alter  table employees add column mobile varchar(15);
alter table employees modify column city varchar(100);
alter table employees rename column employee_name to name;
alter table employees add column experience int;
update employees set experience=2 where employee_id=101;


create table departments(
department_id int primary key,
department_name varchar(100),
location varchar(100)
);

insert into departments(department_id, department_name,location)
values
(1,'Development','Pune'),
(2,'Testing','Mumbai'),
(3,'HR','Nashik');

select * from departments;
update departments set location='Pune' where department_id=2;
rename table departments to company_departments;

desc company_departments;

truncate table company_departments;
drop table company_departments;