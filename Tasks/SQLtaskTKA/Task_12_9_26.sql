create database banking_db;
use banking_db;

create table accounts (
account_id int primary key,
account_number varchar(20),
customer_name varchar(100),
account_type varchar(30),
branch varchar(50),
city varchar(50),
balance decimal(12,2),
credit_score int,
status varchar(20),
opened_date date
);
 
desc accounts;
select * from accounts;

insert into accounts values (1001, 'acc10001', 'aarav sharma', 'savings', 'fc road', 'pune', 85000, 780, 'active', '2025-01-15');
insert into accounts values (1002, 'acc10002', 'priya patil', 'current', 'andheri', 'mumbai', 145000, 810, 'active', '2024-11-20');
insert into accounts values (1003, 'acc10003', 'rohan joshi', 'savings', 'baner', 'pune', 42000, 735, 'active', '2026-02-10');
insert into accounts values (1004, 'acc10004', 'sneha kulkarni', 'salary', 'nashik road', 'nashik', 67000, 760, 'active', '2025-08-05');
insert into accounts values (1005, 'acc10005', 'vikram deshmukh', 'savings', 'camp', 'pune', 18500, 690, 'dormant', '2023-06-18');
insert into accounts values (1006, 'acc10006', 'neha more', 'current', 'thane', 'mumbai', 225000, 825, 'active', '2024-03-12');

select * from accounts;
select customer_name, account_number from accounts;
select customer_name, account_type, balance, status from accounts;
select * from accounts where account_type = 'savings';
select * from accounts where city = 'pune';
select * from accounts where balance > 50000;
select * from accounts where balance <= 50000;
select * from accounts where balance between 50000 and 200000;
select * from accounts where credit_score >= 750;
select * from accounts where status = 'active';
select * from accounts where status in ('dormant', 'closed');
select * from accounts where city = 'pune' and status = 'active';
select * from accounts where city = 'mumbai' or city = 'nashik';
select * from accounts where account_type = 'savings' or account_type = 'salary';
select * from accounts where balance not between 25000 and 100000;
select * from accounts where customer_name like 'a%';
select * from accounts where customer_name like '%a';
select * from accounts where branch like '%road%';
select * from accounts order by balance desc limit 3;
select * from accounts limit 5;
select * from accounts limit 3, 3;
select * from accounts where account_type = 'savings' and city = 'pune' and status = 'active' and balance > 50000;
select * from accounts where city in ('pune', 'mumbai', 'nashik') and credit_score >= 750;
select * from accounts where balance > 100000 and credit_score >= 800;
select * from accounts where balance between 50000 and 150000 or credit_score > 800;
select * from accounts where status = 'active' and account_type != 'current';
select * from accounts where customer_name like '%ar%';


update accounts set balance = 95000 where account_id = 1001;
update accounts set status = 'active' where account_id = 1005;
update accounts set credit_score = 755 where account_id = 1003;
update accounts set branch = 'college road' where account_id = 1004;
update accounts set account_type = 'premium current' where account_id = 1002;
update accounts set balance = balance + 25000 where account_id = 1006;
update accounts set balance = balance + 5000 where account_type = 'savings';
update accounts set city = 'pune city' where city = 'pune';
update accounts set credit_score = credit_score + 10 where status = 'active' and credit_score < 750;
update accounts set status = 'dormant' where balance < 20000;

delete from accounts where account_id = 1005;
delete from accounts where account_number = 'acc10004';
delete from accounts where status = 'closed';
delete from accounts where balance < 10000 or credit_score < 600;
delete from accounts where city = 'pune' and status = 'dormant';

alter table accounts add email varchar(100);
alter table accounts add mobile varchar(15);
alter table accounts modify branch varchar(100);
alter table accounts rename column customer_name to name;
alter table accounts drop column mobile;

alter table accounts add account_category varchar(30);
update accounts set account_category = 'premium' where account_id = 1001;
update accounts set account_category = 'regular' where account_id = 1002;
rename table accounts to bank_accounts;
describe bank_accounts;
truncate table bank_accounts;
drop table bank_accounts;

