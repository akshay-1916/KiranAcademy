create database ecommerce_db;

use ecommerce_db;

create table products(
product_id int primary key,
product_name varchar(100),
category varchar(50),
brand varchar(50),
price decimal(10,2),
quantity int,
city varchar(50),
status varchar(20)
);

desc products;
select * from products;
INSERT INTO products
VALUES
(201, 'Galaxy M55', 'Mobile', 'Samsung', 32000, 15, 'Pune', 'Available'),
(202, 'iPhone 15', 'Mobile', 'Apple', 65000, 8, 'Mumbai', 'Available'),
(203, 'Moto Edge 50', 'Mobile', 'Motorola', 28000, 20, 'Pune', 'Available'),
(204, 'Inspiron 15', 'Laptop', 'Dell', 58000, 6, 'Nashik', 'Available'),
(205, 'IdeaPad Slim 3', 'Laptop', 'Lenovo', 45000, 12, 'Mumbai', 'Available'),
(206, 'Galaxy Watch 6', 'Watch', 'Samsung', 22000, 4, 'Pune', 'Out of Stock'),
(207, 'Apple Watch SE', 'Watch', 'Apple', 30000, 10, 'Mumbai', 'Available'),
(208, 'Redmi Pad', 'Tablet', 'Xiaomi', 24000, 18, 'Pune', 'Available'),
(209, 'OnePlus Pad', 'Tablet', 'OnePlus', 35000, 5, 'Nashik', 'Available');

INSERT INTO products
VALUES (210, 'Bluetooth Speaker', 'Accessories', 'JBL', 7000, 25, 'Mumbai', 'Available');

select product_name from products;
select product_name, price from products;
select product_name, category, brand, price from products;
select * from products where city = 'pune';
select * from products where city = 'mumbai';
select * from products where category = 'mobile';
select * from products where category = 'laptop';
select * from products where price > 30000;
select * from products where price < 30000;
select * from products where price = 35000;
select * from products where price >= 45000;
select * from products where price <= 30000;
select * from products where quantity > 10;
select * from products where quantity < 10;
select * from products where city = 'pune' and category = 'mobile';
select * from products where city = 'mumbai' and status = 'available';
select * from products where price > 30000 and quantity > 5;
select * from products where price >= 30000 and price <= 60000;
select * from products where city = 'pune' or city = 'mumbai';

select * from products where category = 'mobile' or category = 'laptop';
select * from products where quantity < 10 or price > 50000;
select * from products where category = 'mobile' and price > 30000;
select * from products where brand = 'samsung' or brand = 'apple';
select * from products where city = 'pune' and status = 'available' and quantity > 10;
select * from products where price between 25000 and 50000;
select * from products where quantity between 5 and 15;
select * from products where category in ('mobile', 'laptop', 'tablet');
select * from products where city in ('pune', 'mumbai');
select * from products where brand != 'samsung';
select * from products where status != 'out of stock';
select * from products where price != 30000;
select * from products where product_name like 'galaxy%';
select * from products where product_name like '%pad%';
select * from products where category = 'mobile' and (price > 30000 or quantity > 15);


update products set price = 34000 where product_id = 201;
update products set quantity = 12 where product_id = 202;
update products set status = 'available' where product_id = 206; 
update products set price = price + 2000 where category = 'mobile';
update products set quantity = quantity + 5 where city = 'pune';
update products set status = 'out of stock' where quantity < 5;

delete from products where product_id = 210;
delete from products where price < 8000;
delete from products where status = 'out of stock' and quantity < 5;
delete from products where category = 'tablet' and price > 30000;