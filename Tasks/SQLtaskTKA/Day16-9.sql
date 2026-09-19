use student;

create table employees (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(100),
    role VARCHAR(50),
    salary INT,
    createdAt DATETIME,
    city VARCHAR(50)
);

insert into employees values
(101, 'Raj', 'raj@tka.com', 'developer', 1234, '2026-09-15 19:19:38', 'Pune'),
(102, 'Neha', 'neha@tka.com', 'tester', 1132, '2026-09-15 19:19:38', 'Mumbai'),
(103, 'Jay', 'jay@tka.com', 'tester', 1113, '2026-09-15 19:19:38', 'Pune'),
(104, 'Tina', 'tina@tka.com', 'developer', 1144, '2026-09-15 19:19:38', 'Mumbai'),
(105, 'Amit', 'amit@tka.com', 'developer', 1155, '2026-09-15 19:19:38', 'Delhi'),
(106, 'Raksha', 'raksha@tka.com', 'developer', 1166, '2026-09-15 19:19:38', 'Delhi');


select * from employees;
SET SQL_SAFE_UPDATES = 0;

update employees set salary=salary+(salary*0.10) where  role='tester';

select name,salary from employees where salary>1155;

SELECT name, salary FROM employees WHERE salary >= 1166;
SELECT name, role, city FROM employees
WHERE role = 'developer' AND city = 'pune';

select name, role, city from employees
where role = 'tester' or city = 'pune';


select name from employees order by name desc;
select salary from employees order by salary desc limit 4;

select name from employees order by name asc;
select salary from employees order by salary asc;
select name, salary from employees order by salary desc limit 2;
select name , salary from employees order by salary asc limit 3;

select name , role salary from employees order by salary asc ;
select employees.name, employees.salary from employees;
select e.name, e.salary from employees as e;

select name ,salary from employees order by salary desc limit 3 offset 2; 
-- skip first 2 entres




