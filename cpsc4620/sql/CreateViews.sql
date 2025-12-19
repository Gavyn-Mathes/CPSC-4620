-- JAMES KING GAVYN MATHES
-- CPSC 4620 Project Part 2 
-- SQL
-- April 5th 2024
Use Pizzeria;

CREATE VIEW ToppingPopularity AS
SELECT 
    t.ToppingName AS Topping,
    COUNT(st.ToppingID) + SUM(CASE WHEN st.SpecficToppingIsExtra THEN 1 ELSE 0 END) AS ToppingCount
FROM 
    topping t
LEFT JOIN
    specifictopping st ON t.ToppingID = st.ToppingID
GROUP BY 
    t.toppingName
ORDER BY 
    toppingCount DESC;
    

CREATE VIEW ProfitByPizza AS
SELECT 
	   p.BasePizzaSize AS Size,
       p.BaseCrustType AS Crust,
       SUM(p.PizzaTotalPrice - p.PizzaTotalCost) AS Profit,
       DATE_FORMAT(o.OrderTime, '%c/%Y') AS "Order Month"
FROM 
pizza p
JOIN 
ordertable o ON p.OrderID = o.OrderID
GROUP BY 
p.BasePizzaSize, p.BaseCrustType 
ORDER BY Profit ASC;

     

CREATE VIEW ProfitByOrderType AS
SELECT 
    o.OrderType AS CustomerType,
    DATE_FORMAT(o.OrderTime, '%c/%Y') AS "Order Month",
    SUM(o.OrderPriceCustomer) AS TotalOrderPrice,
    SUM(o.OrderCostBusiness) AS TotalOrderCost,
    SUM(o.OrderPriceCustomer - o.OrderCostBusiness) AS Profit
FROM 
    ordertable o
LEFT JOIN 
    pickup pu ON o.OrderID = pu.OrderID
LEFT JOIN 
    dinein di ON o.OrderID = di.OrderID
LEFT JOIN 
    delivery d ON o.OrderID = d.OrderID
WHERE
   o.OrderCompletion = 1
GROUP BY
   CustomerType, "Order Month"

UNION

SELECT 
    Null AS CustomerType,
    'Grand Total'  AS "Order Month",
    SUM(o.OrderPriceCustomer) AS TotalOrderPrice,
    SUM(o.OrderCostBusiness) AS TotalOrderCost,
    SUM(o.OrderPriceCustomer - o.OrderCostBusiness) AS Profit
FROM 
    ordertable o
LEFT JOIN 
    pickup pu ON o.OrderID = pu.OrderID
LEFT JOIN 
    dinein di ON o.OrderID = di.OrderID
LEFT JOIN 
    delivery d ON o.OrderID = d.OrderID
WHERE
   o.OrderCompletion = 1;


select * FROM ToppingPopularity;
select * FROM ProfitByPizza;
select * FROM ProfitByOrderType;