-- JAMES KING GAVYN MATHES
-- CPSC 4620 Project Part 2 
-- SQL
-- April 5th 2024
Use Pizzeria;

INSERT INTO topping (ToppingName, ToppingPrice, ToppingCost, ToppingCurrentInventory, ToppingMinInventory, ToppingAmountPerSmall, ToppingAmountPerMedium, ToppingAmountPerLarge, ToppingAmountPerXLarge)
VALUES
('Pepperoni', 1.25, 0.2, 100, 50, 2, 2.75, 3.5, 4.5),
('Sausage', 1.25, 0.15, 100, 50, 2.5, 3, 3.5, 4.25),
('Ham', 1.5, 0.15, 78, 25, 2, 2.5, 3.25, 4),
('Chicken', 1.75, 0.25, 56, 25, 1.5, 2, 2.25, 3),
('Green Pepper', 0.5, 0.02, 79, 25, 1, 1.5, 2, 2.5),
('Onion', 0.5, 0.02, 85, 25, 1, 1.5, 2, 2.75),
('Roma Tomato', 0.75, 0.03, 86, 10, 2, 3, 3.5, 4.5),
('Mushrooms', 0.75, 0.1, 52, 50, 1.5, 2, 2.5, 3),
('Black Olives', 0.6, 0.1, 39, 25, 0.75, 1, 1.5, 2),
('Pineapple', 1, 0.25, 15, 0, 1, 1.25, 1.75, 2),
('Jalapenos', 0.5, 0.05, 64, 0, 0.5, 0.75, 1.25, 1.75),
('Banana Peppers', 0.5, 0.05, 36, 0, 0.6, 1, 1.3, 1.75),
('Regular Cheese', 0.5, 0.12, 250, 50, 2, 3.5, 5, 7),
('Four Cheese Blend', 1, 0.15, 150, 25, 2, 3.5, 5, 7),
('Feta Cheese', 1.5, 0.18, 75, 0, 1.75, 3, 4, 5.5),
('Goat Cheese', 1.5, 0.2, 54, 0, 1.6, 2.75, 4, 5.5),
('Bacon', 1.5, 0.25, 89, 0, 1, 1.5, 2, 3);


INSERT INTO discount (DiscountName, DiscountIsPercent, DiscountValue)
VALUES
('Employee', 1, 0.15),
('Lunch Special Medium', 0, 1),
('Lunch Special Large', 0, 2),
('Specialty Pizza', 0, 1.5),
('Happy Hour', 1, 0.10),
('Gameday Special', 1, 0.20);


INSERT INTO base (BaseCrustType, BasePizzaSize, BasePizzaPrice, BasePizzaCost)
VALUES
('Thin', 'Small', 3, 0.5),
('Original', 'Small', 3, 0.75),
('Pan', 'Small', 3.5, 1),
('Gluten-Free', 'Small', 4, 2),
('Thin', 'Medium', 5, 1),
('Original', 'Medium', 5, 1.5),
('Pan', 'Medium', 6, 2.25),
('Gluten-Free', 'Medium', 6.25, 3),
('Thin', 'Large', 8, 1.25),
('Original', 'Large', 8, 2),
('Pan', 'Large', 9, 3),
('Gluten-Free', 'Large', 9.5, 4),
('Thin', 'XLarge', 10, 2),
('Original', 'XLarge', 10, 3),
('Pan', 'XLarge', 11.5, 4.5),
('Gluten-Free', 'XLarge', 12.5, 6);

-- ORDER 1------------------------------------------------------

INSERT INTO ordertable(OrderType, OrderPriceCustomer, OrderCostBusiness,OrderCompletion, OrderTime)
VALUES 
('dineIn', 20.75-(SELECT DiscountValue FROM discount WHERE DiscountName = ('Lunch Special Large')), 3.68, 1, '2024-03-05 12:03:00');

SELECT MAX(ORDERID) INTO @OrderID FROM ordertable;

INSERT INTO dinein (OrderID, DineINTableNumber)
VALUES 
(@OrderID, 21);

INSERT INTO pizza (BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID)
SELECT 'Thin', 'Large', (20.75 - (SELECT DiscountValue FROM discount WHERE DiscountName = 'Lunch Special Large')), 3.68, 'Completed', @OrderID;


SELECT MAX(PizzaID) INTO @PizzaID FROM pizza;

INSERT INTO specifictopping (PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, t.ToppingID, 1 
FROM topping t
WHERE t.ToppingName IN ('Regular Cheese');

INSERT INTO specifictopping (PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, t.ToppingID, 0
FROM topping t
WHERE t.ToppingName IN ('Pepperoni', 'Sausage');

INSERT INTO pizzadiscount (PizzaID, DiscountID)
SELECT @PizzaID, DiscountID
FROM discount
WHERE DiscountName = 'Lunch Special Large';


-- ORDER 2 -------------------------------------------------------

INSERT INTO ordertable (OrderType, OrderPriceCustomer, OrderCostBusiness, OrderCompletion, OrderTime)
VALUES 
(
    'dineIn',
    12.85 + 6.93 - (SELECT SUM(DiscountValue) FROM discount WHERE DiscountName IN ('Specialty Pizza','Lunch Special Medium')),
    (SELECT SUM(3.23 + 1.40)),
    1,
    '2024-04-03 12:05:00'
);

SELECT MAX(ORDERID) INTO @OrderID FROM ordertable;

INSERT INTO dinein (OrderID, DineINTableNumber)
VALUES 
(@OrderID, 4);

INSERT INTO pizza (BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID)
SELECT 'Pan', 'Medium', (12.85 - (SELECT SUM(DiscountValue) FROM discount WHERE DiscountName IN ('Specialty Pizza','Lunch Special Medium'))), 3.23, 'Completed', @OrderID;

SELECT MAX(PizzaID) INTO @PizzaID FROM pizza;

INSERT INTO specifictopping (PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, t.ToppingID, 0
FROM topping t
WHERE t.ToppingName IN ('Feta Cheese', 'Black Olives', 'Roma Tomato', 'Mushrooms', 'Banana Peppers');


INSERT INTO pizzadiscount (PizzaID, DiscountID)
SELECT @PizzaID, DiscountID
FROM discount d
WHERE d.DiscountName IN ('Specialty Pizza','Lunch Special Medium');

INSERT INTO pizza (BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID)
SELECT 'Original', 'Small', (6.93), 1.40, 'Completed', @OrderID;

SELECT MAX(PizzaID) INTO @PizzaID FROM pizza;

INSERT INTO specifictopping (PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, t.ToppingID, 0
FROM topping t
WHERE t.ToppingName IN ('Regular Cheese', 'Chicken', 'Banana Peppers');




-- ORDER 3 -------------------------------------------------------
INSERT INTO customer(CustomerFName, CustomerLName, CustomerPhone)
VALUES ( 'Andrew', 'Wilkes-Krier', '864-254-5861');


INSERT INTO ordertable(OrderType, OrderPriceCustomer, OrderCostBusiness, OrderCompletion, OrderTime)
VALUES ('pickUp', 14.88 * 6, 3.30 * 6, 1, '2024-03-03 21:30:00');



SELECT MAX(OrderID) INTO @OrderID FROM ordertable;
SELECT MAX(CustomerID) INTO @CustomerID FROM customer;

 INSERT INTO pickup (OrderID, CustomerID)
VALUES 
(@OrderID, @CustomerID);

INSERT INTO pizza(BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID)
VALUES 
('Original', 'Large', 14.88, 3.30, 'Completed', @OrderID);
SELECT MAX(PizzaID) INTO @PizzaID FROM pizza;
INSERT INTO specifictopping(PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, ToppingID, 0
FROM topping AS t
WHERE t.ToppingName IN ('Regular Cheese', 'Pepperoni');
      
INSERT INTO pizza(BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID)
VALUES 
('Original', 'Large', 14.88, 3.30, 'Completed', @OrderID);
SELECT MAX(PizzaID) INTO @PizzaID FROM pizza;
INSERT INTO specifictopping(PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, ToppingID, 0
FROM topping AS t
WHERE t.ToppingName IN ('Regular Cheese', 'Pepperoni');
      
INSERT INTO pizza(BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID)
VALUES 
('Original', 'Large', 14.88, 3.30, 'Completed', @OrderID);
SELECT MAX(PizzaID) INTO @PizzaID FROM pizza;
INSERT INTO specifictopping(PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, ToppingID, 0
FROM topping AS t
WHERE t.ToppingName IN ('Regular Cheese', 'Pepperoni');

INSERT INTO pizza(BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID)
VALUES 
('Original', 'Large', 14.88, 3.30, 'Completed', @OrderID);
SELECT MAX(PizzaID) INTO @PizzaID FROM pizza;
INSERT INTO specifictopping(PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, ToppingID, 0
FROM topping AS t
WHERE t.ToppingName IN ('Regular Cheese', 'Pepperoni');
INSERT INTO pizza(BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID)
VALUES 
('Original', 'Large', 14.88, 3.30, 'Completed', @OrderID);
SELECT MAX(PizzaID) INTO @PizzaID FROM pizza;
INSERT INTO specifictopping(PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, ToppingID, 0
FROM topping AS t
WHERE t.ToppingName IN ('Regular Cheese', 'Pepperoni');

INSERT INTO pizza(BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID)
VALUES 
('Original', 'Large', 14.88, 3.30, 'Completed', @OrderID);
SELECT MAX(PizzaID) INTO @PizzaID FROM pizza;
INSERT INTO specifictopping(PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, ToppingID, 0
FROM topping AS t
WHERE t.ToppingName IN ('Regular Cheese', 'Pepperoni');
      





-- Order 4
INSERT INTO customer(CustomerFName, CustomerLName, CustomerPhone, CustomerDeliveryStreet, CustomerDeliveryCity, CustomerDeliveryState, CustomerDeliveryZIP)
VALUES ('Andrew','Wilkes-Krier', '864-254-5861', '115 Party Blvd', 'Anderson', 'SC', '29621');


INSERT INTO ordertable(OrderType, OrderPriceCustomer, OrderCostBusiness, OrderCompletion, OrderTime)
SELECT 
    'delivery', 
    CAST((27.94 + 31.50 + 26.75) - (SELECT DiscountValue FROM discount WHERE DiscountName = 'Specialty pizza') - ( (27.94 + 31.50 + 26.75) * (SELECT DiscountValue FROM discount WHERE DiscountName = 'Gameday Special')) AS DECIMAL(5, 2)), 
    9.19 + 6.25 + 8.18,  1, '2024-04-20 19:11:00';
    
SELECT MAX(OrderID) INTO @OrderID FROM ordertable;
SELECT MAX(CustomerID) INTO @CustomerID FROM customer;

INSERT INTO delivery (OrderID, CustomerID)
VALUES 
(@OrderID, @CustomerID);

INSERT INTO orderdiscount (OrderID, DiscountID)
SELECT @OrderID, DiscountID
FROM discount AS d
WHERE d.DiscountName = 'Gameday Special';

INSERT INTO pizza(BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID)
VALUES ('Original', 'XLarge',  CAST((27.94) - ((27.94) * (SELECT DiscountValue FROM discount WHERE DiscountName = 'Gameday Special')) AS DECIMAL(5, 2)), 
     9.19, 'Completed', @OrderID);


SELECT MAX(PizzaID) INTO @PizzaID FROM pizza;

INSERT INTO specifictopping(PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, ToppingID, 0
FROM topping AS t
WHERE t.ToppingName IN ('Four Cheese Blend', 'Pepperoni', 'Sausage');

INSERT INTO pizza(BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID)
VALUES ('Original', 'XLarge', CAST((31.50) - (SELECT DiscountValue FROM discount WHERE DiscountName = 'Specialty Pizza') 
- ((31.50) * (SELECT DiscountValue FROM discount WHERE DiscountName = 'Gameday Special')) AS DECIMAL(5, 2)), 
     6.25, 'Completed', @OrderID);

SELECT MAX(PizzaID) INTO @PizzaID FROM pizza;

INSERT INTO specifictopping(PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, ToppingID, 0
FROM topping AS t
WHERE t.ToppingName IN ('Four Cheese Blend');

INSERT INTO specifictopping(PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, ToppingID, 1
FROM topping AS t
WHERE t.ToppingName IN ('Ham', 'Pineapple');

INSERT INTO pizzadiscount (PizzaID, DiscountID)
SELECT @PizzaID, DiscountID
FROM discount d
WHERE d.DiscountName = 'Specialty Pizza';

INSERT INTO pizza(BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID)
VALUES ('Original', 'XLarge', CAST((26.75) - ((26.75) * (SELECT DiscountValue FROM discount WHERE DiscountName = 'Gameday Special')) AS DECIMAL(5, 2)), 
 8.18, 'Completed', @OrderID);

SELECT MAX(PizzaID) INTO @PizzaID FROM pizza;

INSERT INTO specifictopping(PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, ToppingID, 0
FROM topping AS t
WHERE t.ToppingName IN ('Four Cheese Blend', 'Chicken', 'Bacon');


-- --------Order 5 ------------
INSERT INTO customer(CustomerFName, CustomerLName, CustomerPhone)
VALUES ('Matt','Enger', '864-474-9953');

INSERT INTO ordertable(OrderType, OrderPriceCustomer, OrderCostBusiness, OrderCompletion, OrderTime)
VALUES ('pickUp', 27.45 - (SELECT DiscountValue FROM discount WHERE DiscountName = 'Specialty Pizza'), 7.88, 1, '2024-03-02 17:30:00');

SELECT MAX(OrderID) INTO @OrderID FROM ordertable;
SELECT MAX(CustomerID) INTO @CustomerID FROM customer;

INSERT INTO pickup (OrderID, CustomerID)
VALUES 
(@OrderID, @CustomerID);

INSERT INTO pizza(BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID)
VALUES ('Gluten-Free', 'XLarge', 27.45- (SELECT DiscountValue FROM discount WHERE DiscountName = 'Specialty Pizza'), 7.88, 'Completed', @OrderID);

SELECT MAX(PizzaID) INTO @PizzaID FROM pizza;

INSERT INTO specifictopping(PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, ToppingID, 0
FROM topping AS t
WHERE t.ToppingName IN ('Green Pepper', 'Onion', 'Roma Tomato', 'Mushrooms', 'Black Olives', 'Goat Cheese');

INSERT INTO pizzadiscount(PizzaID, DiscountID)
SELECT @PizzaID, DiscountID
FROM discount AS d
WHERE d.DiscountName = 'Specialty Pizza';

 -- -- Order 6 -----------
INSERT INTO customer(CustomerFName, CustomerLName, CustomerPhone, CustomerDeliveryStreet, CustomerDeliveryCity, CustomerDeliveryState, CustomerDeliveryZIP)
VALUES ('Frank', 'Turner', '864-232-8944', '6745 Wessex Street', 'Anderson', 'SC', '29621');

INSERT INTO ordertable(OrderType, OrderPriceCustomer, OrderCostBusiness, OrderCompletion, OrderTime)
VALUES ('delivery', 20.81, 3.19, 1, '2024-03-02 18:17:00');

SELECT MAX(OrderID) INTO @OrderID FROM ordertable;
SELECT MAX(CustomerID) INTO @CustomerID FROM customer;

INSERT INTO delivery (OrderID, CustomerID)
VALUES 
(@OrderID, @CustomerID);

INSERT INTO pizza(BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID)
VALUES ('Thin', 'Large', 20.81 , 3.19, 'Completed', @OrderID);

SELECT MAX(PizzaID) INTO @PizzaID FROM pizza;

INSERT INTO specifictopping(PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, ToppingID, 0
FROM topping AS t
WHERE t.ToppingName IN ('Chicken', 'Green Pepper', 'Onion', 'Mushrooms');

INSERT INTO specifictopping(PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, ToppingID, 1
FROM topping AS t
WHERE t.ToppingName IN ('Four Cheese Blend');

-- -- -- Order 7
INSERT INTO customer(CustomerFName, CustomerLName, CustomerPhone, CustomerDeliveryStreet, CustomerDeliveryCity, CustomerDeliveryState, CustomerDeliveryZIP)
VALUES ('Milo','Auckermann', '864-878-5679', '8879 Suburban Home', 'Anderson', 'SC', '29621');

INSERT INTO ordertable(OrderType, OrderPriceCustomer, OrderCostBusiness, OrderCompletion, OrderTime) 
SELECT 
    'delivery', 
    CAST((13.00 + 19.25) * (1 - (SELECT DiscountValue FROM discount WHERE DiscountName = 'Employee')) AS DECIMAL(10, 2)), 
    2.00 + 3.25, 
    1, '2024-04-13 20:32:00';
    
SELECT MAX(OrderID) INTO @OrderID FROM ordertable;
SELECT MAX(CustomerID) INTO @CustomerID FROM customer;

INSERT INTO delivery (OrderID, CustomerID)
VALUES 
(@OrderID, @CustomerID);

INSERT INTO pizza(BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID)
VALUES ('Thin', 'Large', CAST(13.00 * (1 - (SELECT DiscountValue FROM discount WHERE DiscountName = 'Employee'))AS DECIMAL(10, 2)), 2.00, 'Completed', @OrderID);

SELECT MAX(PizzaID) INTO @PizzaID FROM pizza;

INSERT INTO specifictopping(PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, ToppingID, 1
FROM topping AS t
WHERE t.ToppingName IN ('Four Cheese Blend');


INSERT INTO pizza(BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID)
VALUES ('Thin', 'Large', CAST(19.25 * (1 - (SELECT DiscountValue FROM discount WHERE DiscountName = 'Employee'))AS DECIMAL(10, 2)), 3.25, 'Completed', @OrderID);

SELECT MAX(PizzaID) INTO @PizzaID FROM pizza;

INSERT INTO specifictopping(PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, ToppingID, 0
FROM topping AS t
WHERE t.ToppingName IN ('Regular Cheese');

INSERT INTO specifictopping(PizzaID, ToppingID, SpecficToppingIsExtra)
SELECT @PizzaID, ToppingID, 1
FROM topping AS t
WHERE t.ToppingName IN ('Pepperoni');

