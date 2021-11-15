# Gift Certificates System

`Spring Boot` `HATEOAS` `Java 8` `MySQL` `JUnit 5` `Mockito`


### Module dependencies
`model > repository > service > controller`

### Find user with the highest cost of all orders
```sql
/**
    Option 1
    Selecting only one user (does not take into account the situation when several users with the same order amount are found) 
 */
SELECT user_id, SUM(price)
FROM `order`
GROUP BY user_id
ORDER BY SUM(price) DESC
LIMIT 1;

/**
    Option 2.1
    Selecting a list of users who can have the total sum of all orders equal. 
 */
SELECT user_id, sum_price
FROM (
         SELECT user_id, sum_price, RANK() OVER (order by sum_price DESC) AS r
         FROM (SELECT user_id, SUM(price) sum_price FROM `order` GROUP BY user_id) t
     ) AS t2
WHERE r = 1;

/**
  Option 2.2
  Selecting a list of users who can have the total sum of all orders equal. 
 */
SELECT user_id, SUM(price) sum_price
FROM `order`
GROUP BY user_id
HAVING sum_price = (
    SELECT SUM(price)
    FROM `order`
    GROUP BY user_id
    ORDER BY SUM(price) DESC
    LIMIT 1
);

/**
  Option 2.3
  Selecting a list of users who can have the total sum of all orders equal. 
 */
SELECT user_id, SUM(price) sum_price
FROM `order`
GROUP BY user_id
HAVING sum_price = (SELECT max(sum)
                    FROM (SELECT SUM(price) as sum
                          FROM `order`
                          GROUP BY user_id) as s);
```

### Link
[Task](https://github.com/mjc-school/MJC-School/blob/master/stage%20%233/java/module%20%232.%20REST%20API%20Basics/rest_api_basics_task.md)

[Random data generation](https://www.mockaroo.com/)