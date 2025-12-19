-- JAMES KING GAVYN MATHES
-- CPSC 4620 Project Part 2 
-- SQL
-- April 5th 2024
-- TABLE CUSTOMER--------------------------------

Create schema Pizzeria;
Use Pizzeria;

CREATE TABLE customer(
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerFName VARCHAR(100),
    CustomerLName VARCHAR(100),
    CustomerPhone VARCHAR(15),
    CustomerType VARCHAR(50),
    CustomerDeliveryStreet VARCHAR(255),
    CustomerDeliveryCity VARCHAR(100),
    CustomerDeliveryState VARCHAR(50),
    CustomerDeliveryZIP VARCHAR(10)
);




-- TABLE ORDER--------------------------------
CREATE TABLE ordertable(
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    OrderType VARCHAR(20),
    OrderPriceCustomer DECIMAL(7, 2),
    OrderCostBusiness DECIMAL(7, 2),
    OrderCompletion BOOLEAN,
    OrderTime DATETIME
);


-- TABLE pickup--------------------------------
CREATE TABLE pickup(
    OrderID INT PRIMARY KEY,
    CustomerID INT,
    FOREIGN KEY (CustomerID) REFERENCES customer(CustomerID),
    FOREIGN KEY (OrderID) REFERENCES ordertable(OrderID)
);


-- TABLE dinein--------------------------------
CREATE TABLE dinein(
    OrderID INT PRIMARY KEY,
    DineINTableNumber INT,
    FOREIGN KEY (OrderID) REFERENCES ordertable(OrderID)
);



-- TABLE delivery-----------------------------------------------------------------------------------
CREATE TABLE delivery(
    OrderID INT PRIMARY KEY,
    CustomerID INT,
    FOREIGN KEY (CustomerID) REFERENCES customer(CustomerID),
    FOREIGN KEY (OrderID) REFERENCES ordertable(OrderID)
);


-- TABLE base--------------------------------
CREATE TABLE base (
    BaseCrustType VARCHAR(20),
    BasePizzaSize VARCHAR(20),
    PRIMARY KEY (BaseCrustType,BasePizzaSize),
    BasePizzaPrice DECIMAL(5, 2),
    BasePizzaCost DECIMAL(5, 2)
);


-- TABLE pizza--------------------------------
CREATE TABLE pizza (
    PizzaID INT AUTO_INCREMENT PRIMARY KEY,
    BaseCrustType VARCHAR(20),
    BasePizzaSize VARCHAR(20),
    PizzaTotalPrice DECIMAL(7, 2),
    PizzaTotalCost DECIMAL(7, 2),
    PizzaStatus VARCHAR(20),
    OrderID INT,
    FOREIGN KEY (BaseCrustType, BasePizzaSize) REFERENCES base(BaseCrustType, BasePizzaSize),
    FOREIGN KEY (OrderID) REFERENCES ordertable(OrderID)
);


-- TABLE topping --------------------------------

CREATE TABLE topping (
    ToppingID INT AUTO_INCREMENT PRIMARY KEY,
    ToppingName VARCHAR(50) NOT NULL,
    ToppingPrice DECIMAL(5, 2) NOT NULL,
    ToppingCost DECIMAL(5, 2) NOT NULL,
    ToppingAmountPerSmall DECIMAL(5, 2) NOT NULL,
    ToppingAmountPerMedium DECIMAL(5, 2) NOT NULL,
    ToppingAmountPerLarge DECIMAL(5, 2) NOT NULL,
    ToppingAmountPerXLarge DECIMAL(5, 2) NOT NULL,
    ToppingMinInventory INT NOT NULL,
    ToppingCurrentInventory INT NOT NULL
);



-- TABLE specifictopping --------------------------------
CREATE TABLE specifictopping (
    PizzaID INT,
    ToppingID INT,
    SpecficToppingIsExtra BOOLEAN,
    PRIMARY KEY (PizzaID, ToppingID),
    FOREIGN KEY (PizzaID) REFERENCES pizza(PizzaID),
    FOREIGN KEY (ToppingID) REFERENCES topping(ToppingID)
);

-- TABLE discount--------------------------------
CREATE TABLE discount (
    DiscountID INT AUTO_INCREMENT PRIMARY KEY,
    DiscountName VARCHAR(50),
    DiscountIsPercent BOOLEAN,
    DiscountValue DECIMAL(5, 2)
);


-- TABLE orderdiscount--------------------------------
CREATE TABLE orderdiscount (
    OrderID INT,
    DiscountID INT,
    PRIMARY KEY (OrderID, DiscountID),
    FOREIGN KEY (OrderID) REFERENCES ordertable(OrderID),
    FOREIGN KEY (DiscountID) REFERENCES discount(DiscountID)
);


-- TABLE pizzadiscount--------------------------------

CREATE TABLE pizzadiscount(
    DiscountID INT,
    PizzaID INT,
    PRIMARY KEY (PizzaID, DiscountID),
    FOREIGN KEY (PizzaID) REFERENCES pizza(PizzaID),
    FOREIGN KEY (DiscountID) REFERENCES discount(DiscountID)
);

