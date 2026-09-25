create database quickbite

;use quickbite;

create table restaurants(
restaurant_id int primary key,
restaurant_name varchar(100),
cuisine varchar(50),
city varchar(50),
rating double,
avg_order_value double,
orders_count int,
delivery_fee double,
est_delivery_time int,
owner_name varchar(100),
brand varchar(100)
);


insert into restaurants values
(101,'spice route','north indian','pune',4.5,420,18500,39,32,'amit sharma','spice route'),
(102,'south tiffin house','south indian','chennai',4.1,260,14300,49,25,'priya nair','south tiffin'),
(103,'mumbai zaika','maharashtrian','mumbai',4.1,350,22000,49,38,'rohit patil','zaika foods'),
(104,'burger garage','fast food','pune',4.4,310,27500,29,30,'neha joshi','burger garage'),
(105,'pizza planet','italian','mumbai',4.6,520,31000,19,35,'vikas mehta','pizza planet'),
(106,'biryani junction','biryani','hyderabad',4.7,480,42000,29,40,'arjun reddy','biryani junction'),
(107,'chai & snacks','cafe','pune',3.9,180,19500,19,22,'sneha kulkarni','chai & snacks'),
(108,'royal thali','north indian','delhi',4.0,390,16800,39,42,'manish gupta','royal thali'),
(109,'tandoori tales','mughlai','delhi',4.5,610,12100,49,45,'karan singh','tandoori tales'),
(110,'coastal curry','seafood','goa',4.3,750,9800,69,48,'riya fernandes','coastal curry'),
(111,'green bowl','healthy','bengaluru',4.3,330,11600,29,20,'ananya rao','green bowl'),
(112,'dosa factory','south indian','bengaluru',4.2,240,27800,19,24,'suresh kumar','dosa factory'),
(113,'punjabi dhaba','punjabi','chandigarh',4.1,370,13200,29,40,'gurpreet singh','punjabi dhaba'),
(114,'the wok house','chinese','pune',4.4,450,18400,39,36,'rahul jain','wok house'),
(115,'sushi street','japanese','mumbai',4.8,920,7600,89,50,'meera shah','sushi street'),
(116,'cafe mocha','cafe','pune',4.0,290,15400,29,27,'ishita deshmukh','cafe mocha'),
(117,'street tadka','indian','nagpur',3.9,220,10200,19,35,'akash verma','street tadka'),
(118,'hyderabadi house','biryani','hyderabad',4.6,430,35500,29,37,'faizan ali','hyderabadi house'),
(119,'pasta palace','italian','bengaluru',4.5,560,10900,49,41,'nikhil rao','pasta palace'),
(120,'sweet cravings','desserts','pune',4.2,280,20500,19,26,'pooja patil','sweet cravings'),
(121,'kebab kingdom','mughlai','delhi',4.4,530,14100,59,44,'sameer khan','kebab kingdom'),
(122,'taco town','mexican','mumbai',4.2,460,8700,49,39,'kabir malhotra','taco town'),
(123,'farm fresh','healthy','pune',4.6,390,12500,29,30,'rohan kulkarni','farm fresh'),
(124,'midnight bites','fast food','pune',4.0,250,24800,39,34,'tanvi shah','midnight bites'),
(125,'kolkata kitchen','bengali','kolkata',4.3,340,11400,39,43,'soham sen','kolkata kitchen'),
(126,'kerala cafe','south indian','kochi',4.5,310,12700,29,38,'akhil menon','kerala cafe'),
(127,'royal rajputana','rajasthani','jaipur',4.7,470,9200,49,46,'vivek rathore','rajputana foods'),
(128,'namma meals','south indian','bengaluru',4.4,275,23900,19,27,'kavya shetty','namma meals'),
(129,'lassi lab','beverages','pune',4.1,160,18200,19,21,'dev malhotra','lassi lab'),
(130,'flame & grill','bbq','mumbai',4.8,880,8300,49,52,'aditya kapoor','flame & grill');


select*from restaurants where rating>4.5;
select*from restaurants where avg_order_value<300;
select*from restaurants where city='pune';
select*from restaurants where orders_count>20000;
select*from restaurants where est_delivery_time>40;
select*from restaurants where rating between 4.2 and 4.7;
select*from restaurants where cuisine in('south indian','italian','biryani');
select*from restaurants where owner_name like'%patil%';
select*from restaurants where brand=restaurant_name;
select*from restaurants where delivery_fee<30;
select*from restaurants order by orders_count desc limit 5;
select*from restaurants order by avg_order_value asc limit 5;
select*from restaurants order by rating desc;
select distinct cuisine from restaurants;
select restaurant_name as restaurant_name,rating as customer_rating from restaurants;
select restaurant_name,owner_name,brand from restaurants;
select*from restaurants order by city asc,rating desc;
select*from restaurants where orders_count>10000 order by rating desc limit 5;
select*from restaurants where city='pune' order by orders_count desc limit 3;
select*from restaurants order by delivery_fee desc limit 5;


select*from restaurants where restaurant_name like's%';
select*from restaurants where restaurant_name like'%house';
select*from restaurants where restaurant_name like'%cafe%';
select*from restaurants where cuisine like'%indian%';
select*from restaurants where char_length(restaurant_name)=5;
select*from restaurants where owner_name like'%raj%';
select*from restaurants where brand like'%foods%';
select*from restaurants where city like'p%';
select*from restaurants where avg_order_value>400 and rating>4.5;
select*from restaurants where orders_count>20000 or rating>4.7;
select*from restaurants where city!='pune';
select*from restaurants where est_delivery_time between 25 and 40;
select*from restaurants where avg_order_value between 300 and 600;
select*from restaurants where city in('pune','mumbai');
select*from restaurants where cuisine='fast food'and orders_count>20000;
select*from restaurants where rating>4.5 and delivery_fee<40;
select*from restaurants where city='bengaluru' and orders_count>10000;
select*from restaurants where owner_name!='rahul jain';

select*from restaurants where rating>4.5 and orders_count<10000;
select*from restaurants where avg_order_value<300 and orders_count>20000;
select*from restaurants where est_delivery_time<30 and rating>4.3;
select*from restaurants where city='mumbai' order by orders_count desc limit 3;
select*from restaurants where avg_order_value>(select avg(avg_order_value)from restaurants);
select*from restaurants where city='pune' order by orders_count desc;
select restaurant_name,city,rating,avg_order_value from restaurants where cuisine='south indian';
select*from restaurants where avg_order_value>500 and rating>=4.5;


select restaurant_name,cuisine,city,rating,avg_order_value,orders_count,delivery_fee,owner_name,brand from restaurants where rating>4.4 and orders_count>10000 and avg_order_value between 300 and 700 order by orders_count desc limit 5;