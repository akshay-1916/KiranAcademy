SELECT * FROM employees;

update employees set salary = salary + (salary * 0.10) where role='tester'; 

SELECT name , salary FROM employees WHERE salary > 1166 ;

SELECT name , salary FROM employees WHERE salary < 1166 ;

SELECT name , salary FROM employees WHERE salary >= 1166 ;

SELECT name , salary FROM employees WHERE salary <= 1166 ;

SELECT name , role , city FROM employees WHERE role='developer' AND city='pune'; -- both condition shoud be matched

SELECT name , role , city FROM employees WHERE role='tester' OR city='pune'; -- any one condition shoud be matched

SELECT name , role , city FROM employees WHERE city='mumbai' OR city='pune' ;  

SELECT name , role , city FROM employees WHERE city IN ('pune' , 'mumbai'); -- cleaner way

SELECT name , role , city FROM employees WHERE role LIKE '%dev%';

SELECT name , role , city , salary FROM employees WHERE salary BETWEEN 1166 AND 2255;

SELECT * FROM employees;

SELECT name FROM employees ORDER BY name ASC; -- Aa->Zz -- using order by clause we can perform sorting

SELECT name FROM employees ORDER BY name DESC; -- Zz -> Aa 

SELECT salary FROM employees ORDER BY salary ASC; -- 1 -> 9 

SELECT salary FROM employees ORDER BY salary DESC; -- 9 -> 1

SELECT name , salary FROM employees ORDER BY salary DESC LIMIT 2; -- 1 -> 9 

SELECT name , salary FROM employees ORDER BY salary ASC LIMIT 3; -- 1 -> 9 

SELECT * FROM employees;

SELECT name FROM employees ORDER BY name ASC; -- Aa->Zz -- using order by clause we can perform sorting

SELECT name FROM employees ORDER BY name DESC; -- Zz -> Aa 

SELECT salary FROM employees ORDER BY salary ASC; -- 1 -> 9 

SELECT salary FROM employees ORDER BY salary DESC; -- 9 -> 1

SELECT name , salary FROM employees ORDER BY salary DESC LIMIT 2; -- 1 -> 9 

SELECT name , salary FROM employees ORDER BY salary ASC LIMIT 3; -- 1 -> 9 

SELECT * FROM employees;

SELECT name FROM employees ORDER BY name ASC; -- Aa->Zz -- using order by clause we can perform sorting

SELECT name FROM employees ORDER BY name DESC; -- Zz -> Aa 

SELECT salary FROM employees ORDER BY salary ASC; -- 1 -> 9 

SELECT salary FROM employees ORDER BY salary DESC; -- 9 -> 1

SELECT name , salary FROM employees ORDER BY salary DESC LIMIT 2; -- 1 -> 9 

SELECT name , salary FROM employees ORDER BY salary ASC LIMIT 3; -- 1 -> 9 

SELECT name , role , salary  FROM employees ORDER BY salary ASC , name DESC;

SELECT DISTINCT city FROM employees; -- DISTINCT removes duplicates while retrival only

SELECT DISTINCT role as dept FROM employees; -- alias

SELECT employees.name , employees.salary FROM employees ; 

SELECT e.name , e.salary FROM employees AS e ; 

SELECT name , salary FROM employees ORDER BY salary DESC LIMIT 3 OFFSET 2; -- skip 1st 2 entries and show 3 rows 



