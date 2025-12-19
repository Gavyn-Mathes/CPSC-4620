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