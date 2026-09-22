create database streamflix;
use streamflix;

create table movies (
movie_id int primary key,
title varchar(100),
genre varchar(50),
language varchar(30),
rating double,
price double,
views int,
release_year int,
director varchar(100),
production_company varchar(100)
);

insert into movies values
(101, 'avengers: endgame', 'action', 'english', 8.4, 199, 950000, 2019, 'anthony russo & joe russo', 'marvel studios'),
(102, '3 idiots', 'comedy', 'hindi', 8.4, 149, 1200000, 2009, 'rajkumar hirani', 'vinod chopra films'),
(103, 'drishyam', 'thriller', 'hindi', 8.2, 129, 850000, 2015, 'nishikant kamat', 'panorama studios'),
(104, 'interstellar', 'sci-fi', 'english', 8.7, 249, 1100000, 2014, 'christopher nolan', 'warner bros. pictures'),
(105, 'kgf: chapter 1', 'action', 'kannada', 8.4, 179, 1400000, 2018, 'prashanth neel', 'hombale films'),
(106, 'kgf: chapter 2', 'action', 'kannada', 8.4, 199, 1800000, 2022, 'prashanth neel', 'hombale films'),
(107, 'rrr', 'action', 'telugu', 8.0, 199, 2100000, 2022, 's. s. rajamouli', 'dvv entertainment'),
(108, 'baahubali: the beginning', 'action', 'telugu', 8.0, 179, 1900000, 2015, 's. s. rajamouli', 'arka media works'),
(109, 'baahubali 2: the conclusion', 'action', 'telugu', 8.2, 199, 2500000, 2017, 's. s. rajamouli', 'arka media works'),
(110, 'pushpa: the rise', 'action', 'telugu', 7.6, 179, 1700000, 2021, 'sukumar', 'mythri movie makers'),
(111, 'pushpa 2: the rule', 'action', 'telugu', 6.1, 199, 2300000, 2024, 'sukumar', 'mythri movie makers'),
(112, 'jawan', 'action', 'hindi', 6.9, 199, 2200000, 2023, 'atlee', 'red chillies entertainment'),
(113, 'pathaan', 'action', 'hindi', 5.8, 179, 1600000, 2023, 'siddharth anand', 'yash raj films'),
(114, 'dangal', 'sports', 'hindi', 8.3, 159, 1300000, 2016, 'nitesh tiwari', 'aamir khan productions'),
(115, 'pk', 'comedy', 'hindi', 8.1, 149, 1250000, 2014, 'rajkumar hirani', 'vinod chopra films'),
(116, 'munna bhai m.b.b.s', 'comedy', 'hindi', 8.1, 99, 900000, 2003, 'rajkumar hirani', 'vinod chopra films'),
(117, 'chhichhore', 'comedy', 'hindi', 8.3, 129, 1050000, 2019, 'nitesh tiwari', 'fox star studios'),
(118, 'taare zameen par', 'drama', 'hindi', 8.3, 119, 780000, 2007, 'aamir khan', 'aamir khan productions'),
(119, 'zindagi na milegi dobara', 'drama', 'hindi', 8.2, 139, 980000, 2011, 'zoya akhtar', 'excel entertainment'),
(120, 'war', 'action', 'hindi', 6.5, 189, 1500000, 2019, 'siddharth anand', 'yash raj films'),
(121, 'dhoom', 'action', 'hindi', 6.6, 109, 720000, 2004, 'sanjay gadhvi', 'yash raj films'),
(122, 'dhoom 2', 'action', 'hindi', 6.5, 129, 860000, 2006, 'sanjay gadhvi', 'yash raj films'),
(123, 'dhoom 3', 'action', 'hindi', 5.4, 149, 920000, 2013, 'vijay krishna acharya', 'yash raj films'),
(124, 'bahubali returns', 'fantasy', 'hindi', 7.1, 139, 410000, 2020, 'demo director', 'streamflix originals'),
(125, 'robot', 'sci-fi', 'tamil', 7.1, 129, 680000, 2010, 's. shankar', 'sun pictures'),
(126, 'enthiran 2.0', 'sci-fi', 'tamil', 6.2, 169, 760000, 2018, 's. shankar', 'lyca productions'),
(127, 'vikram', 'action', 'tamil', 8.3, 189, 1450000, 2022, 'lokesh kanagaraj', 'raaj kamal films international'),
(128, 'master', 'action', 'tamil', 7.3, 159, 1120000, 2021, 'lokesh kanagaraj', 'xb film creators'),
(129, 'kantara', 'drama', 'kannada', 8.2, 149, 1350000, 2022, 'rishab shetty', 'hombale films'),
(130, 'charlie 777', 'adventure', 'kannada', 8.0, 139, 740000, 2022, 'kiranraj k', 'paramvah studios');

select * from movies;


select * from movies where rating > 8;
select * from movies where price < 150;
select * from movies where language = 'hindi';
select * from movies where views > 1000000;
select * from movies where release_year > 2015;
select * from movies where rating between 8 and 8.5;
select * from movies where genre in ('action', 'comedy', 'thriller');
select * from movies where director = 's. s. rajamouli';
select * from movies where production_company = 'hombale films';
select * from movies where production_company = 'arka media works';
select * from movies order by views desc limit 5;
select * from movies order by price asc limit 5;
select * from movies order by rating desc;
select distinct language from movies;
select title as movie, rating as imdb_rating from movies;
select title, director, production_company from movies;
select * from movies order by production_company asc, rating desc;
select * from movies where release_year > 2015 order by rating desc limit 5;
select * from movies where language = 'telugu' order by views desc limit 3;
select * from movies order by price desc limit 5;
select * from movies where title like 'a%';
select * from movies where title like '%a';
select * from movies where title like '%baahubali%';
select * from movies where title like '%dhoom%';
select * from movies where char_length(title) = 5;
select * from movies where director like '%raj%';
select * from movies where production_company like '%films%';
select * from movies where language like 't%';



select * from movies where price > 150 and rating > 8;
select * from movies where views > 1000000 or rating > 8.5;
select * from movies where genre != 'horror';
select * from movies where release_year between 2015 and 2020;
select * from movies where price between 100 and 200;
select * from movies where language in ('hindi', 'telugu');
select * from movies where genre = 'action' and views > 1000000;
select * from movies where director = 's. s. rajamouli' and rating > 8;
select * from movies where production_company = 'hombale films' and release_year > 2019;
select * from movies where director != 's. s. rajamouli';
select * from movies where rating > 8 and views < 500000;
select * from movies where price < 150 and views > 1000000;
select * from movies where language = 'english' and release_year > 2015 and rating > 8.5;
select * from movies where release_year > 2020 order by views desc limit 3;
select * from movies where price > (select avg(price) from movies);
select * from movies where director = 's. s. rajamouli' order by release_year;
select title, release_year, rating, views from movies where production_company = 'hombale films';
select * from movies where title like '%baahubali%' or title like '%kgf%';
select title, genre, rating, price, views, director, production_company from movies where rating > 8 and views > 500000 and price between 100 and 250 order by views desc limit 5;