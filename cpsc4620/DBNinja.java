//package cpsc4620;
//
//import java.io.IOException;
//import java.sql.*;
//import java.util.*;
//import java.util.Date;
//
///*
// * This file is where most of your code changes will occur You will write the code to retrieve
// * information from the database, or save information to the database
// *
// * The class has several hard coded static variables used for the connection, you will need to
// * change those to your connection information
// *
// * This class also has static string variables for pickup, delivery and dine-in. If your database
// * stores the strings differently (i.e "pick-up" vs "pickup") changing these static variables will
// * ensure that the comparison is checking for the right string in other places in the program. You
// * will also need to use these strings if you store this as boolean fields or an integer.
// *
// *
// */
//
///**
// * A utility class to help add and retrieve information from the database
// */
//
//public final class DBNinja {
//    private static Connection conn;
//
//    // Change these variables to however you record dine-in, pick-up and delivery, and sizes and crusts
//    public final static String pickup = "pickup";
//    public final static String delivery = "delivery";
//    public final static String dine_in = "dinein";
//
//    public final static String size_s = "Small";
//    public final static String size_m = "Medium";
//    public final static String size_l = "Large";
//    public final static String size_xl = "XLarge";
//
//    public final static String crust_thin = "Thin";
//    public final static String crust_orig = "Original";
//    public final static String crust_pan = "Pan";
//    public final static String crust_gf = "Gluten-Free";
//
//
//
//
//    private static boolean connect_to_db() throws SQLException, IOException {
//
//        try {
//            conn = DBConnector.make_connection();
//            return true;
//        } catch (SQLException e) {
//            return false;
//        } catch (IOException e) {
//            return false;
//        }
//
//    }
//    public static void addOrder(Order o) throws SQLException, IOException {
//        try {
//            connect_to_db();
//            if (Objects.equals(o.getOrderType(), DBNinja.dine_in)) {
//                DineinOrder dineinOrder = (DineinOrder) o;
//                String sqlQuery = "INSERT INTO ordertable (OrderType, OrderPriceCustomer, OrderCostBusiness, OrderCompletion, OrderTime) VALUES (?, ?, ?, ?, ?)";
//                PreparedStatement statement = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
//                statement.setString(1, dineinOrder.getOrderType());
//                statement.setDouble(2, dineinOrder.getCustPrice());
//                statement.setDouble(3, dineinOrder.getBusPrice());
//                statement.setInt(4, dineinOrder.getIsComplete());
//                statement.setString(5, dineinOrder.getDate());
//                statement.executeUpdate();
//                ResultSet generatedKeys = statement.getGeneratedKeys();
//                int generatedKey;
//                if (generatedKeys.next()) {
//                    generatedKey = generatedKeys.getInt(1);
//                } else {
//                    throw new SQLException();
//                }
//                statement.close();
//                String sqlQuery2 = "INSERT INTO dinein (OrderID, DineINTableNumber) VALUES (?, ?)";
//                statement = conn.prepareStatement(sqlQuery2);
//                statement.setInt(1, generatedKey);
//                statement.setInt(2, dineinOrder.getTableNum());
//                statement.executeUpdate();
//                statement.close();
//                conn.close();
//            } else if (Objects.equals(o.getOrderType(), DBNinja.pickup)) {
//                PickupOrder pickupOrder = (PickupOrder) o;
//                String sqlQuery = "INSERT INTO ordertable (OrderType, OrderPriceCustomer, OrderCostBusiness, OrderCompletion, OrderTime) VALUES (?, ?, ?, ?, ?)";
//                PreparedStatement statement = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
//                statement.setString(1, pickupOrder.getOrderType());
//                statement.setDouble(2, pickupOrder.getCustPrice());
//                statement.setDouble(3, pickupOrder.getBusPrice());
//                statement.setInt(4, pickupOrder.getIsComplete());
//                statement.setString(5, pickupOrder.getDate());
//                statement.executeUpdate();
//                ResultSet generatedKeys = statement.getGeneratedKeys();
//                int generatedKey;
//                if (generatedKeys.next()) {
//                    generatedKey = generatedKeys.getInt(1);
//                } else {
//                    throw new SQLException();
//                }
//                statement.close();
//                String sqlQuery2 = "INSERT INTO pickup (OrderID, CustomerID) VALUES (?, ?)";
//                statement = conn.prepareStatement(sqlQuery2);
//                statement.setInt(1, generatedKey);
//                statement.setInt(2, pickupOrder.getCustID());
//                statement.executeUpdate();
//                statement.close();
//                conn.close();
//            } else if (Objects.equals(o.getOrderType(), DBNinja.delivery)) {
//                DeliveryOrder deliveryOrder = (DeliveryOrder) o;
//                String sqlQuery = "INSERT INTO ordertable (OrderType, OrderPriceCustomer, OrderCostBusiness, OrderCompletion, OrderTime) VALUES (?, ?, ?, ?, ?)";
//                PreparedStatement statement = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
//                statement.setString(1, deliveryOrder.getOrderType());
//                statement.setDouble(2, deliveryOrder.getCustPrice());
//                statement.setDouble(3, deliveryOrder.getBusPrice());
//                statement.setInt(4, deliveryOrder.getIsComplete());
//                statement.setString(5, deliveryOrder.getDate());
//                statement.executeUpdate();
//                ResultSet generatedKeys = statement.getGeneratedKeys();
//                int generatedKey;
//                if (generatedKeys.next()) {
//                    generatedKey = generatedKeys.getInt(1);
//                } else {
//                    throw new SQLException();
//                }
//                statement.close();
//                String sqlQuery2 = "INSERT INTO delivery (OrderID, CustomerID) VALUES (?, ?)";
//                statement = conn.prepareStatement(sqlQuery2);
//                statement.setInt(1, generatedKey);
//                statement.setInt(2, deliveryOrder.getCustID());
//                statement.executeUpdate();
//                statement.close();
//                conn.close();
//            }
//        } finally {
//            if (conn != null) {
//                conn.close();
//            }
//        }
//    }
//
//
//    public static void addPizza(Pizza p) throws SQLException, IOException {
//        connect_to_db();
//        String sqlQuery = "INSERT INTO pizza (BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID) VALUES (?, ?, ?, ?, ?, ?)";
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        statement.setString(1, p.getCrustType());
//        statement.setString(2, p.getSize());
//        statement.setDouble(3, p.getCustPrice());
//        statement.setDouble(4, p. getBusPrice());
//        statement.setString(5, p.getPizzaState());
//        statement.setInt(6, p.getOrderID());
//        statement.executeUpdate();
//        statement.close();
//        conn.close();
//
//        //DO NOT FORGET TO CLOSE YOUR CONNECTION
//    }
//
//
//    public static void useTopping(Pizza p, Topping t, boolean isDoubled) throws SQLException, IOException //this method will update toppings inventory in SQL and add entities to the Pizzatops table. Pass in the p pizza that is using t topping
//    {
//        connect_to_db();
//        /*
//         * This method should do 2 two things.
//         * - update the topping inventory every time we use t topping (accounting for extra toppings as well)
//         * - connect the topping to the pizza
//         *   What that means will be specific to your yimplementatinon.
//         *
//         * Ideally, you should't let toppings go negative....but this should be dealt with BEFORE calling this method.
//         *
//         */
//
//        String updateQuery = "UPDATE topping SET ToppingCurrentInventory = ToppingCurrentInventory - ? WHERE ToppingID = ?";
//        PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
//        double amountUsed;
//        if (isDoubled) {
//            amountUsed = t.getXLAMT() * 2;
//        } else {
//
//            amountUsed = t.getLgAMT();
//        }
//        updateStatement.setDouble(1, amountUsed);
//        updateStatement.setInt(2, t.getTopID());
//        updateStatement.executeUpdate();
//
//
//        String insertQuery = "INSERT INTO specifictopping (PizzaID, ToppingID, SpecficToppingIsExtra) VALUES (?, ?, ?)";
//        PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
//        insertStatement.setInt(1, p.getPizzaID());
//        insertStatement.setInt(2, t.getTopID());
//        insertStatement.setBoolean(3, isDoubled);
//        insertStatement.executeUpdate();
//
//
//        //DO NOT FORGET TO CLOSE YOUR CONNECTION
//        conn.close();
//    }
//
//
//    public static void usePizzaDiscount(Pizza p, Discount d) throws SQLException, IOException
//    {
//        connect_to_db();
//        String sqlQuery = "INSERT INTO pizzadiscount (DiscountID, PizzaID) VALUES (?, ?)";
//
//
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        statement.setInt(1, d.getDiscountID());
//        statement.setInt(2, p.getPizzaID());
//
//
//        statement.executeUpdate();
//
//        conn.close();
//
//
//    }
//
//    public static void useOrderDiscount(Order o, Discount d) throws SQLException, IOException
//    {
//        connect_to_db();
//        connect_to_db();
//        String sqlQuery = "INSERT INTO orderdiscount (OrderID, DiscountID) VALUES (?, ?)";
//
//
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        statement.setInt(1, o.getOrderID());
//        statement.setInt(2, d.getDiscountID());
//
//
//        statement.executeUpdate();
//
//        conn.close();
//    }
//
//    public static void addCustomer(Customer c) throws SQLException, IOException {
//        connect_to_db();
//        if (c.getAddress() == null) {
//            String sqlQuery = "INSERT INTO customer (CustomerLName, CustomerFName, CustomerPhone) VALUES (?, ?, ?)";
//
//            PreparedStatement statement = conn.prepareStatement(sqlQuery);
//            statement.setString(1, c.getLName());
//            statement.setString(2, c.getFName());
//            statement.setString(3, c.getPhone());
//
//
//            statement.executeUpdate();
//            statement.close();
//        } else {
//            String sqlQuery = "INSERT INTO customer (CustomerLName, CustomerFName, CustomerPhone, CustomerDeliveryStreet, CustomerDeliveryCity, CustomerDeliveryState, CustomerDeliveryZIP) VALUES (?, ?, ?, ?, ?, ?, ?)";
//
//            PreparedStatement statement = conn.prepareStatement(sqlQuery);
//            statement.setString(1, c.getLName());
//            statement.setString(2, c.getFName());
//            statement.setString(3, c.getPhone());
//
//            String[] splitName = c.getAddress().trim().split("/n");
//
//            statement.setString(4, splitName[0]);
//            statement.setString(5, splitName[1]);
//            statement.setString(6, splitName[2]);
//            statement.setString(7, splitName[3]);
//
//            statement.executeUpdate();
//            statement.close();
//        }
//        // Close the connection and statement
//        conn.close();
//    }
//
//
//    public static void completeOrder(Order o) throws SQLException, IOException {
//        connect_to_db();
//        String sqlQuery = "UPDATE ordertable SET OrderCompletion = 1 WHERE OrderID = ?";
//
//        // Prepare the statement
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        statement.setInt(1, o.getOrderID());
//
//        // Execute the update query
//        int rowsAffected = statement.executeUpdate();
//
//        if (rowsAffected > 0) {
//            System.out.println("Order marked as complete successfully.");
//        } else {
//            System.out.println("Failed to mark order as complete.");
//        }
//
//        // Close the statement and connection
//        statement.close();
//        conn.close();
//    }
//
//
//    public static ArrayList<Order> getOrders(boolean openOnly) throws SQLException, IOException {
//        connect_to_db();
//        String sqlQuery = "SELECT * FROM ordertable";
//        if (openOnly) sqlQuery += " WHERE ordertable.OrderCompletion = FALSE";
//        else sqlQuery += " WHERE ordertable.OrderCompletion = TRUE";
//        sqlQuery += " ORDER BY ordertable.OrderID DESC";
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        ResultSet resultSet = statement.executeQuery();
//        ArrayList<Order> orders = new ArrayList<>();
//
//        String sqlQuery2;
//        PreparedStatement statement2 = null;
//        ResultSet resultSet2 = null;
//
//        try {
//            while (resultSet.next()) {
//                int orderID = resultSet.getInt("OrderID");
//                String orderType = resultSet.getString("OrderType");
//                double orderPrice = resultSet.getDouble("OrderPriceCustomer");
//                double orderCost = resultSet.getDouble("OrderCostBusiness");
//                boolean orderValue = resultSet.getBoolean("OrderCompletion");
//                int orderCompletion = orderValue ? 1 : 0;
//                String orderTime = resultSet.getString("OrderTime");
//
//                if (Objects.equals(orderType, DBNinja.pickup)) {
//                    sqlQuery2 = "SELECT * FROM pickup WHERE pickup.OrderID = ?";
//                    statement2 = conn.prepareStatement(sqlQuery2);
//                    statement2.setInt(1, orderID);
//                    resultSet2 = statement2.executeQuery();
//                    if (resultSet2.next()) {
//                        int custID = resultSet2.getInt("CustomerID");
//                        int isPickedUp = resultSet2.getInt("isPickedUp");
//                        orders.add(new PickupOrder(orderID, custID, orderTime, orderPrice, orderCost, isPickedUp, orderCompletion));
//                    }
//                } else if (Objects.equals(orderType, DBNinja.dine_in)) {
//                    sqlQuery2 = "SELECT * FROM dinein WHERE dinein.OrderID = ?";
//                    statement2 = conn.prepareStatement(sqlQuery2);
//                    statement2.setInt(1, orderID);
//                    resultSet2 = statement2.executeQuery();
//                    if (resultSet2.next()) {
//                        int tableNumber = resultSet2.getInt("DineINTableNumber");
//                        orders.add(new DineinOrder(orderID, -1, orderTime, orderPrice, orderCost, orderCompletion, tableNumber));
//                    }
//                } else if (Objects.equals(orderType, DBNinja.delivery)) {
//                    sqlQuery2 = "SELECT * FROM delivery WHERE delivery.OrderID = ?";
//                    statement2 = conn.prepareStatement(sqlQuery2);
//                    statement2.setInt(1, orderID);
//                    resultSet2 = statement2.executeQuery();
//                    if (resultSet2.next()) {
//                        int custID = resultSet2.getInt("CustomerID");
//                        String address = getCustomerAddress(custID);
//                        orders.add(new DeliveryOrder(orderID, custID, orderTime, orderPrice, orderCost, orderCompletion, address));
//                    }
//                }
//            }
//        } finally {
//
//            if (resultSet2 != null) resultSet2.close();
//            if (statement2 != null) statement2.close();
//            statement.close();
//            conn.close();
//        }
//
//        return orders;
//    }
//
//    public static Order getLastOrder() throws SQLException, IOException {
//        connect_to_db();
//        String sqlQuery = "SELECT o.*, d.CustomerID " +
//                "FROM ordertable o " +
//                "LEFT JOIN delivery d ON o.OrderID = d.OrderID " +
//                "ORDER BY o.OrderID DESC LIMIT 1";
//
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        ResultSet resultSet = statement.executeQuery();
//
//        if (resultSet.next()) {
//            int orderID = resultSet.getInt("OrderID");
//            Integer custID = resultSet.getInt("CustomerID");
//            String orderType = resultSet.getString("OrderType");
//            String date = resultSet.getString("OrderTime");
//            double custPrice = resultSet.getDouble("OrderPriceCustomer");
//            double busPrice = resultSet.getDouble("OrderCostBusiness");
//            int isComplete = resultSet.getInt("OrderCompletion");
//
//            Order lastOrder = new Order(orderID, custID, orderType, date, custPrice, busPrice, isComplete);
//            return lastOrder;
//        }
//
//        conn.close();
//        return null;
//    }
//
//    public static ArrayList<Order> getOrdersByDate(String date) throws SQLException, IOException {
//        connect_to_db();
//        ArrayList<Order> orders = new ArrayList<>();
//        String sqlQuery = "SELECT o.*, d.CustomerID " +
//                "FROM ordertable o " +
//                "LEFT JOIN delivery d ON o.OrderID = d.OrderID " +
//                "LEFT JOIN dinein din ON o.OrderID = din.OrderID " +
//                "LEFT JOIN pickup p ON o.OrderID = p.OrderID " +
//                "WHERE DATE(o.OrderTime) = ?";
//
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        statement.setString(1, date);
//
//        ResultSet resultSet = statement.executeQuery();
//
//        while (resultSet.next()) {
//            int orderID = resultSet.getInt("OrderID");
//            int custID = resultSet.getInt("CustomerID");
//            String orderType = resultSet.getString("OrderType");
//            double custPrice = resultSet.getDouble("OrderPriceCustomer");
//            double busPrice = resultSet.getDouble("OrderCostBusiness");
//            int iscomplete = resultSet.getInt("OrderCompletion");
//            String orderTime = resultSet.getString("OrderTime");
//
//            Order order = new Order(orderID, custID, orderType, date, custPrice, busPrice, iscomplete);
//            orders.add(order);
//        }
//
//        resultSet.close();
//        statement.close();
//        conn.close();
//
//        return orders;
//    }
//
//    public static ArrayList<Discount> getDiscountList() throws SQLException, IOException {
//        connect_to_db();
//        ArrayList<Discount> discounts = new ArrayList<>();
//        String sqlQuery = "SELECT * FROM discount";
//
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        ResultSet resultSet = statement.executeQuery();
//
//        while (resultSet.next()) {
//            int discountID = resultSet.getInt("DiscountID");
//            String name = resultSet.getString("DiscountName");
//            double value = resultSet.getDouble("DiscountValue");
//            boolean isPercent = resultSet.getBoolean("DiscountIsPercent");
//
//            Discount discount = new Discount(discountID, name, value, isPercent);
//            discounts.add(discount);
//        }
//
//        statement.close();
//        conn.close();
//
//        return discounts;
//    }
//
//    public static Discount findDiscountByName(String name) throws SQLException, IOException {
//        connect_to_db();
//        String sqlQuery = "SELECT * FROM discount WHERE DiscountName = ?";
//
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        statement.setString(1, name);
//
//        ResultSet resultSet = statement.executeQuery();
//        if (resultSet.next()) {
//            int discountID = resultSet.getInt("DiscountID");
//            boolean isPercent = resultSet.getBoolean("DiscountIsPercent");
//            double value = resultSet.getDouble("DiscountValue");
//
//            Discount discount = new Discount(discountID, name,value, isPercent);
//
//            statement.close();
//            conn.close();
//
//            return discount;
//        }
//
//        statement.close();
//        conn.close();
//
//        return null;
//    }
//
//
//    public static ArrayList<Customer> getCustomerList() throws SQLException, IOException {
//        connect_to_db();
//        String sqlQuery = "SELECT * FROM customer ORDER BY CustomerID";
//        ArrayList<Customer> customerList = new ArrayList<>();
//
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        ResultSet resultSet = statement.executeQuery();
//
//        while (resultSet.next()) {
//            int custID = resultSet.getInt("CustomerID");
//            String fName = resultSet.getString("CustomerFName");
//            String lName = resultSet.getString("CustomerLName");
//            String phone = resultSet.getString("CustomerPhone");
//            String street = resultSet.getString("CustomerDeliveryStreet");
//            String city = resultSet.getString("CustomerDeliveryCity");
//            String state = resultSet.getString("CustomerDeliveryState");
//            String zip = resultSet.getString("CustomerDeliveryZIP");
//
//            Customer customer = new Customer(custID, fName, lName, phone);
//            customer.setAddress(street, city, state, zip);
//
//            customerList.add(customer);
//        }
//        conn.close();
//
//        if (customerList.isEmpty()) {
//            return null;
//        }
//
//        return customerList;
//    }
//    public static Customer findCustomerByPhone(String phoneNumber) throws SQLException, IOException {
//        connect_to_db();
//        String sqlQuery = "SELECT * FROM customer WHERE CustomerPhone = ?";
//
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        statement.setString(1, phoneNumber);
//
//        ResultSet resultSet = statement.executeQuery();
//
//        if (resultSet.next()) {
//            int custID = resultSet.getInt("CustomerID");
//            String FName = resultSet.getString("CustomerFName");
//            String LName = resultSet.getString("CustomerLName");
//            String phone = resultSet.getString("CustomerPhone");
//            String street = resultSet.getString("CustomerDeliveryStreet");
//            String city = resultSet.getString("CustomerDeliveryCity");
//            String state = resultSet.getString("CustomerDeliveryState");
//            String zip = resultSet.getString("CustomerDeliveryZIP");
//
//            Customer customer = new Customer(custID, FName, LName, phone);
//            customer.setAddress(street, city, state, zip);
//
//            conn.close();
//            return customer;
//        }
//
//        conn.close();
//        return null;
//    }
//
//    public static ArrayList<Topping> getToppingList() throws SQLException, IOException {
//        connect_to_db();
//        String sqlQuery = "SELECT * FROM topping ORDER BY ToppingID";
//
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        ResultSet resultSet = statement.executeQuery();
//        ArrayList<Topping> toppingList = new ArrayList<>();
//
//        while (resultSet.next()) {
//            int topID = resultSet.getInt("ToppingID");
//            String topName = resultSet.getString("ToppingName");
//            double perAMT = resultSet.getDouble("ToppingAmountPerSmall");
//            double medAMT = resultSet.getDouble("ToppingAmountPerMedium");
//            double lgAMT = resultSet.getDouble("ToppingAmountPerLarge");
//            double xlAMT = resultSet.getDouble("ToppingAmountPerXLarge");
//            double custPrice = resultSet.getDouble("ToppingPrice");
//            double busPrice = resultSet.getDouble("ToppingCost");
//            int minINVT = resultSet.getInt("ToppingMinInventory");
//            int curINVT = resultSet.getInt("ToppingCurrentInventory");
//
//            Topping topping = new Topping(topID, topName, perAMT, medAMT, lgAMT, xlAMT, custPrice, busPrice, minINVT, curINVT);
//            toppingList.add(topping);
//        }
//
//        conn.close();
//        return toppingList;
//    }
//
//    public static Topping findToppingByName(String name) throws SQLException, IOException {
//        String sqlQuery = "SELECT * FROM topping WHERE ToppingName = ?";
//
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        statement.setString(1, name);
//
//        ResultSet resultSet = statement.executeQuery();
//
//        if (resultSet.next()) {
//            int topID = resultSet.getInt("ToppingID");
//            String topName = resultSet.getString("ToppingName");
//            double perAMT = resultSet.getDouble("ToppingAmountPerSmall");
//            double medAMT = resultSet.getDouble("ToppingAmountPerMedium");
//            double lgAMT = resultSet.getDouble("ToppingAmountPerLarge");
//            double xlAMT = resultSet.getDouble("ToppingAmountPerXLarge");
//            double custPrice = resultSet.getDouble("ToppingPrice");
//            double busPrice = resultSet.getDouble("ToppingCost");
//            int minINVT = resultSet.getInt("ToppingMinInventory");
//            int curINVT = resultSet.getInt("ToppingCurrentInventory");
//
//            Topping topping = new Topping(topID, topName, perAMT, medAMT, lgAMT, xlAMT, custPrice, busPrice, minINVT, curINVT);
//            conn.close();
//            return topping;
//        }
//
//        conn.close();
//        return null;
//    }
//
//    public static void addToInventory(Topping t, double quantity) throws SQLException, IOException {
//        connect_to_db();
//
//        String sqlQuery = "UPDATE topping SET ToppingCurrentInventory = ToppingCurrentInventory + ? WHERE ToppingID = ?";
//
//        // Create the PreparedStatement
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//
//        statement.setDouble(1, quantity);
//        statement.setInt(2, t.getTopID());
//
//        statement.executeUpdate();
//
//        // DO NOT FORGET TO CLOSE YOUR CONNECTION
//        statement.close();
//        conn.close();
//
//    }
//
//    public static double getBaseCustPrice(String size, String crust) throws SQLException, IOException {
//        connect_to_db();
//        String sqlQuery = "SELECT BasePizzaPrice FROM base WHERE BasePizzaSize = ? AND BaseCrustType = ?";
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//
//        statement.setString(1, size);
//        statement.setString(2, crust);
//
//        ResultSet resultSet = statement.executeQuery();
//
//        double basePrice = 0.0;
//        if (resultSet.next()) {
//            basePrice = resultSet.getDouble("BasePizzaPrice");
//        }
//
//        // DO NOT FORGET TO CLOSE YOUR CONNECTION
//        resultSet.close();
//        statement.close();
//        conn.close();
//
//        // DO NOT FORGET TO CLOSE YOUR CONNECTION
//        return basePrice;
//    }
//    public static double getBaseBusPrice(String size, String crust) throws SQLException, IOException {
//        connect_to_db();
//
//        String sqlQuery = "SELECT BasePizzaCost FROM base WHERE BasePizzaSize = ? AND BaseCrustType = ?";
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//
//        statement.setString(1, size);
//        statement.setString(2, crust);
//
//        ResultSet resultSet = statement.executeQuery();
//
//        double baseCustPrice = 0.0;
//        if (resultSet.next()) {
//            baseCustPrice = resultSet.getDouble("BasePizzaCost");
//        }
//
//        resultSet.close();
//        statement.close();
//        conn.close();
//        //DO NOT FORGET TO CLOSE YOUR CONNECTION
//        return baseCustPrice;
//    }
//
//    public static void printInventory() throws SQLException, IOException {
//        //prepared statement and clls the sql query using prepared statement (prevent injections)
//        connect_to_db();
//        String sqlQuery = "SELECT ToppingName, ToppingCurrentInventory FROM topping ORDER BY ToppingName;";
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        ResultSet resultSet = statement.executeQuery();
//
//        System.out.println("Topping Name\tCurrent Inventory");
//
//        while (resultSet.next()) {
//            String toppingName = resultSet.getString("ToppingName");
//            int currentInventory = resultSet.getInt("ToppingCurrentInventory");
//
//            // Print each row
//            System.out.println(toppingName + "\t" + currentInventory);
//        }
//
//        //DO NOT FORGET TO CLOSE YOUR CONNECTION
//        resultSet.close();
//        conn.close();
//
//    }
//
//    public static void printToppingPopReport() throws SQLException, IOException
//    {
//        //prepared statement and clls the sql query using prepared statement (prevent injections)
//        connect_to_db();
//        String sqlQuery = "SELECT * FROM ToppingPopularity ORDER BY ToppingCount DESC;";
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        ResultSet resultSet = statement.executeQuery();
//
//        // print column names
//        System.out.println("Topping\tToppingCount");
//
//        // keep printing until values expire
//        while (resultSet.next()) {
//            String topping = resultSet.getString("Topping");
//            int toppingCount = resultSet.getInt("ToppingCount");
//            System.out.println(topping + "\t" + toppingCount);
//        }
//
//        //DO NOT FORGET TO CLOSE YOUR CONNECTION
//        resultSet.close();
//        statement.close();
//        conn.close();
//    }
//
//    public static void printProfitByPizzaReport() throws SQLException, IOException
//    {
//        //prepared statement and clls the sql query using prepared statement (prevent injections)
//        connect_to_db();
//        String sqlQuery = "SELECT * FROM ProfitByPizza;";
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        ResultSet resultSet = statement.executeQuery();
//
//        // print column names
//        System.out.println("Size\tCrust\tProfit\tOrder Month");
//
//        // keep printing until values expire
//        while (resultSet.next()) {
//            String size = resultSet.getString("Size");
//            String crust = resultSet.getString("Crust");
//            double profit = resultSet.getDouble("Profit");
//            String orderMonth = resultSet.getString("Order Month");
//            System.out.println(size + "\t" + crust + "\t" + profit + "\t" + orderMonth);
//        }
//
//        //DO NOT FORGET TO CLOSE YOUR CONNECTION
//        resultSet.close();
//        statement.close();
//        conn.close();
//    }
//
//
//    public static void printProfitByOrderType() throws SQLException, IOException
//    {
//        //prepared statement and clls the sql query using prepared statement (prevent injections)
//        connect_to_db();
//        String sqlQuery = "SELECT * FROM ProfitByOrderType;";
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        ResultSet resultSet = statement.executeQuery();
//
//        System.out.println("CustomerType\tOrder Month\tTotal Order Price\tTotal Order Cost\tProfit");
//
//        while (resultSet.next()) {
//            String customerType = resultSet.getString("CustomerType");
//            String orderMonth = resultSet.getString("Order Month");
//            double totalOrderPrice = resultSet.getDouble("TotalOrderPrice");
//            double totalOrderCost = resultSet.getDouble("TotalOrderCost");
//            double profit = resultSet.getDouble("Profit");
//
//            System.out.println(customerType + "\t" + orderMonth + "\t" + totalOrderPrice + "\t" + totalOrderCost + "\t" + profit);
//
//        }
//
//        //DO NOT FORGET TO CLOSE YOUR CONNECTION
//        resultSet.close();
//        statement.close();
//        conn.close();
//    }
//
//
//
//
//    public static String getCustomerName(int CustID) throws SQLException, IOException
//    {
//        /*
//         * This is a helper method to fetch and format the name of a customer
//         * based on a customer ID. This is an example of how to interact with
//         * your database from Java.  It's used in the model solution for this project...so the code works!
//         *
//         * OF COURSE....this code would only work in your application if the table & field names match!
//         *
//         */
//
//        connect_to_db();
//
//        String cname2 = "";
//        PreparedStatement os;
//        ResultSet rset2;
//        String query2;
//        query2 = "Select CustomerFName, CustomerLName From customer WHERE CustomerID=?;";
//        os = conn.prepareStatement(query2);
//        os.setInt(1, CustID);
//        rset2 = os.executeQuery();
//        while(rset2.next())
//        {
//            cname2 = rset2.getString("CustomerFName") + " " + rset2.getString("CustomerLName"); // note the use of field names in the getSting methods
//        }
//
//        conn.close();
//        return cname2;
//    }
//
//    // Helper Functions
//    public static ArrayList<Pizza> getPizzaList() throws SQLException, IOException {
//        connect_to_db();
//
//        String sqlQuery = "SELECT * FROM pizza";
//        PreparedStatement statement = conn.prepareStatement(sqlQuery);
//        ResultSet resultSet = statement.executeQuery();
//
//        ArrayList<Pizza> pizzaList = new ArrayList<>();
//
//        while (resultSet.next()) {
//            int pizzaID = resultSet.getInt("PizzaID");
//            String crustType = resultSet.getString("BaseCrustType");
//            String pizzaSize = resultSet.getString("BasePizzaSize");
//            double totalCustPrice = resultSet.getDouble("PizzaTotalPrice");
//            double totalBusPrice = resultSet.getDouble("PizzaTotalCost");
//            String pizzaStatus = resultSet.getString("PizzaStatus");
//            int orderID = resultSet.getInt("OrderID");
//
//            Pizza pizza = new Pizza(pizzaID, pizzaSize, crustType, orderID, pizzaStatus, " ", totalCustPrice, totalBusPrice);
//            pizzaList.add(pizza);
//        }
//
//        // Close ResultSet, PreparedStatement, and connection
//        resultSet.close();
//        statement.close();
//        conn.close();
//
//        return pizzaList;
//    }
//
//    public static String getCustomerAddress(int CustID) throws SQLException, IOException {
//        connect_to_db();
//
//        String cAddress2 = "";
//        PreparedStatement os;
//        ResultSet rset2;
//        String query2;
//        query2 = "Select CustomerDeliveryStreet, CustomerDeliveryCity, CustomerDeliveryState, CustomerDeliveryZIP From customer WHERE CustomerID=?;";
//        os = conn.prepareStatement(query2);
//        os.setInt(1, CustID);
//        rset2 = os.executeQuery();
//        while(rset2.next())
//        {
//            cAddress2 = rset2.getString("CustomerDeliveryStreet") + " " + rset2.getString("CustomerDeliveryCity") + " " + rset2.getString("CustomerDeliveryState") + " " + rset2.getString("CustomerDeliveryZip"); // note the use of field names in the getSting methods
//        }
//
//        conn.close();
//        return cAddress2;
//    }
//
//    public static void updateOrderValues(Order order) throws SQLException, IOException {
//        connect_to_db();
//
//
//        String updateQuery = "UPDATE ordertable SET OrderPriceCustomer = ?, OrderCostBusiness = ? WHERE OrderID = ?";
//        PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
//
//        updateStatement.setDouble(1, order.getCustPrice());
//        updateStatement.setDouble(2, order.getBusPrice());
//        updateStatement.setInt(3, order.getOrderID());
//
//        updateStatement.executeUpdate();
//
//
//        conn.close();
//    }
//
//
//    /*
//     * The next 3 private methods help get the individual components of a SQL datetime object.
//     * You're welcome to keep them or remove them.
//     */
//    private static int getYear(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
//    {
//        return Integer.parseInt(date.substring(0,4));
//    }
//    private static int getMonth(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
//    {
//        return Integer.parseInt(date.substring(5, 7));
//    }
//    private static int getDay(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
//    {
//        return Integer.parseInt(date.substring(8, 10));
//    }
//
//    public static boolean checkDate(int year, int month, int day, String dateOfOrder)
//    {
//        if(getYear(dateOfOrder) > year)
//            return true;
//        else if(getYear(dateOfOrder) < year)
//            return false;
//        else
//        {
//            if(getMonth(dateOfOrder) > month)
//                return true;
//            else if(getMonth(dateOfOrder) < month)
//                return false;
//            else
//            {
//                if(getDay(dateOfOrder) >= day)
//                    return true;
//                else
//                    return false;
//            }
//        }
//    }
//
//
//}
//
package cpsc4620;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

/*
 * This file is where most of your code changes will occur You will write the code to retrieve
 * information from the database, or save information to the database
 *
 * The class has several hard coded static variables used for the connection, you will need to
 * change those to your connection information
 *
 * This class also has static string variables for pickup, delivery and dine-in. If your database
 * stores the strings differently (i.e "pick-up" vs "pickup") changing these static variables will
 * ensure that the comparison is checking for the right string in other places in the program. You
 * will also need to use these strings if you store this as boolean fields or an integer.
 *
 *
 */

/**
 * A utility class to help add and retrieve information from the database
 */

public final class DBNinja {
    private static Connection conn;

    // Change these variables to however you record dine-in, pick-up and delivery, and sizes and crusts
    public final static String pickup = "pickup";
    public final static String delivery = "delivery";
    public final static String dine_in = "dinein";

    public final static String size_s = "Small";
    public final static String size_m = "Medium";
    public final static String size_l = "Large";
    public final static String size_xl = "XLarge";

    public final static String crust_thin = "Thin";
    public final static String crust_orig = "Original";
    public final static String crust_pan = "Pan";
    public final static String crust_gf = "Gluten-Free";




    private static boolean connect_to_db() throws SQLException, IOException {

        try {
            conn = DBConnector.make_connection();
            return true;
        } catch (SQLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

    }


    public static void addOrder(Order o) throws SQLException, IOException {
        connect_to_db();
        if (Objects.equals(o.getOrderType(), DBNinja.dine_in)) {
            DineinOrder dineinOrder = (DineinOrder) o;
            String sqlQuery = "INSERT INTO ordertable (OrderType, OrderPriceCustomer, OrderCostBusiness, OrderCompletion, OrderTime) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, dineinOrder.getOrderType());
            statement.setDouble(2, dineinOrder.getCustPrice());
            statement.setDouble(3, dineinOrder.getBusPrice());
            statement.setInt(4, dineinOrder.getIsComplete());
            statement.setString(5, dineinOrder.getDate());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            int generatedKey;
            if (generatedKeys.next()) {
                generatedKey = generatedKeys.getInt(1);
            } else {
                throw new SQLException();
            }
            String sqlQuery2 = "INSERT INTO dinein (OrderID, DineINTableNumber) VALUES (?, ?)";
            PreparedStatement statement2 = conn.prepareStatement(sqlQuery2);
            statement2.setInt(1, generatedKey);
            statement2.setInt(2, dineinOrder.getTableNum());
            statement2.executeUpdate();
        } else if (Objects.equals(o.getOrderType(), DBNinja.pickup)) {
            PickupOrder pickupOrder = (PickupOrder) o;
            String sqlQuery = "INSERT INTO ordertable (OrderType, OrderPriceCustomer, OrderCostBusiness, OrderCompletion, OrderTime) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, pickupOrder.getOrderType());
            statement.setDouble(2, pickupOrder.getCustPrice());
            statement.setDouble(3, pickupOrder.getBusPrice());
            statement.setInt(4, pickupOrder.getIsComplete());
            statement.setString(5, pickupOrder.getDate());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            int generatedKey;
            if (generatedKeys.next()) {
                generatedKey = generatedKeys.getInt(1);
            } else {
                throw new SQLException();
            }
            String sqlQuery2 = "INSERT INTO pickup (OrderID, CustomerID) VALUES (?, ?)";
            PreparedStatement statement2 = conn.prepareStatement(sqlQuery2);
            statement2.setInt(1, generatedKey);
            statement2.setInt(2, pickupOrder.getCustID());
            statement2.executeUpdate();
        } else if (Objects.equals(o.getOrderType(), DBNinja.delivery)) {
            DeliveryOrder deliveryOrder = (DeliveryOrder) o;
            String sqlQuery = "INSERT INTO ordertable (OrderType, OrderPriceCustomer, OrderCostBusiness, OrderCompletion, OrderTime) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, deliveryOrder.getOrderType());
            statement.setDouble(2, deliveryOrder.getCustPrice());
            statement.setDouble(3, deliveryOrder.getBusPrice());
            statement.setInt(4, deliveryOrder.getIsComplete());
            statement.setString(5, deliveryOrder.getDate());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            int generatedKey;
            if (generatedKeys.next()) {
                generatedKey = generatedKeys.getInt(1);
            } else {
                throw new SQLException();
            }
            String sqlQuery2 = "INSERT INTO delivery (OrderID, CustomerID) VALUES (?, ?)";
            PreparedStatement statement2 = conn.prepareStatement(sqlQuery2);
            statement2.setInt(1, generatedKey);
            statement2.setInt(2, deliveryOrder.getCustID());
            statement2.executeUpdate();
        }
        conn.close();


        //DO NOT FORGET TO CLOSE YOUR CONNECTION
    }

    public static void addPizza(Pizza p) throws SQLException, IOException
    {
        connect_to_db();
        String sqlQuery = "INSERT INTO pizza (BaseCrustType, BasePizzaSize, PizzaTotalPrice, PizzaTotalCost, PizzaStatus, OrderID) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setString(1, p.getCrustType());
        statement.setString(2, p.getSize());
        statement.setDouble(3, p.getCustPrice());
        statement.setDouble(4, p. getBusPrice());
        statement.setString(5, p.getPizzaState());
        statement.setInt(6, p.getOrderID());
        statement.executeUpdate();
        statement.close();
        conn.close();

        //DO NOT FORGET TO CLOSE YOUR CONNECTION
    }


    public static void useTopping(Pizza p, Topping t, boolean isDoubled) throws SQLException, IOException //this method will update toppings inventory in SQL and add entities to the Pizzatops table. Pass in the p pizza that is using t topping
    {
        connect_to_db();
        /*
         * This method should do 2 two things.
         * - update the topping inventory every time we use t topping (accounting for extra toppings as well)
         * - connect the topping to the pizza
         *   What that means will be specific to your yimplementatinon.
         *
         * Ideally, you should't let toppings go negative....but this should be dealt with BEFORE calling this method.
         *
         */

        String updateQuery = "UPDATE topping SET ToppingCurrentInventory = ToppingCurrentInventory - ? WHERE ToppingID = ?";
        PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
        double amountUsed;
        if (isDoubled) {
            amountUsed = t.getXLAMT() * 2;
        } else {

            amountUsed = t.getLgAMT();
        }
        updateStatement.setDouble(1, amountUsed);
        updateStatement.setInt(2, t.getTopID());
        updateStatement.executeUpdate();


        String insertQuery = "INSERT INTO specifictopping (PizzaID, ToppingID, SpecficToppingIsExtra) VALUES (?, ?, ?)";
        PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
        insertStatement.setInt(1, p.getPizzaID());
        insertStatement.setInt(2, t.getTopID());
        insertStatement.setBoolean(3, isDoubled);
        insertStatement.executeUpdate();


        //DO NOT FORGET TO CLOSE YOUR CONNECTION
        conn.close();
    }


    public static void usePizzaDiscount(Pizza p, Discount d) throws SQLException, IOException
    {
        connect_to_db();
        String sqlQuery = "INSERT INTO pizzadiscount (DiscountID, PizzaID) VALUES (?, ?)";


        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1, d.getDiscountID());
        statement.setInt(2, p.getPizzaID());


        statement.executeUpdate();

        conn.close();


    }

    public static void useOrderDiscount(Order o, Discount d) throws SQLException, IOException
    {
        connect_to_db();
        connect_to_db();
        String sqlQuery = "INSERT INTO orderdiscount (OrderID, DiscountID) VALUES (?, ?)";


        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1, o.getOrderID());
        statement.setInt(2, d.getDiscountID());


        statement.executeUpdate();

        conn.close();
    }

    public static void addCustomer(Customer c) throws SQLException, IOException {
        connect_to_db();
        String sqlQuery = "INSERT INTO customer (CustomerLName, CustomerFName, CustomerPhone) VALUES (?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setString(1, c.getLName());
        statement.setString(2, c.getFName());
        statement.setString(3, c.getPhone());


        statement.executeUpdate();

        // Close the connection and statement
        statement.close();
        conn.close();
    }


    public static void completeOrder(Order o) throws SQLException, IOException {
        connect_to_db();
        String sqlQuery = "UPDATE ordertable SET OrderCompletion = 1 WHERE OrderID = ?";

        // Prepare the statement
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1, o.getOrderID());

        // Execute the update query
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Order marked as complete successfully.");
        } else {
            System.out.println("Failed to mark order as complete.");
        }

        // Close the statement and connection
        statement.close();
        conn.close();
    }


    public static ArrayList<Order> getOrders(boolean openOnly) throws SQLException, IOException {
        connect_to_db();
        /*
         * Return an arraylist of all of the orders.
         *  openOnly == true => only return a list of open (i.e., orders that have not been marked as completed)
         *           == false => return a list of all the orders in the database
         * Remember that in Java, we account for supertypes and subtypes
         * which means that when we create an arrayList of orders, that really
         * means we have an arrayList of dineinOrders, deliveryOrders, and pickupOrders.
         *
         * Don't forget to order the data coming from the database appropriately.
         *
         */
        String sqlQuery = "SELECT o.*, d.CustomerID, din.DineINTableNumber, c.CustomerDeliveryStreet, c.CustomerDeliveryCity, c.CustomerDeliveryState, c.CustomerDeliveryZIP " +
                "FROM ordertable o " +
                "LEFT JOIN delivery d ON o.OrderID = d.OrderID " +
                "LEFT JOIN dinein din ON o.OrderID = din.OrderID " +
                "LEFT JOIN customer c ON d.CustomerID = c.CustomerID";

        if (openOnly) {
            sqlQuery += " WHERE o.OrderCompletion = FALSE";
        } else {
            sqlQuery += " WHERE o.OrderCompletion = TRUE";
        }

        sqlQuery += " ORDER BY o.OrderID DESC";

        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Order> orders = new ArrayList<>();

        while (resultSet.next()) {
            int orderID = resultSet.getInt("OrderID");
            Integer custID = resultSet.getInt("CustomerID");
            String orderType = resultSet.getString("OrderType");
            String date = resultSet.getString("OrderTime");
            double custPrice = resultSet.getDouble("OrderPriceCustomer");
            double busPrice = resultSet.getDouble("OrderCostBusiness");
            int isComplete = resultSet.getInt("OrderCompletion");
            int tableNum = resultSet.getInt("DineINTableNumber");

            // Address handling
            String street = resultSet.getString("CustomerDeliveryStreet");
            String city = resultSet.getString("CustomerDeliveryCity");
            String state = resultSet.getString("CustomerDeliveryState");
            String zip = resultSet.getString("CustomerDeliveryZIP");
            String address = street + "\n" + city + "\n" + state + "\n" + zip;

            Order order;
            switch (orderType) {
                case "delivery":
                    order = new DeliveryOrder(orderID, custID, date, custPrice, busPrice, isComplete, address);
                    break;
                case "pickup":
                    int isPickedUp = 1;
                    order = new PickupOrder(orderID, custID, date, custPrice, busPrice, isPickedUp, isComplete);
                    order.toString();
                    break;
                case "dinein":
                    order = new DineinOrder(orderID, custID, date, custPrice, busPrice, isComplete, tableNum);
                    break;
                default:
                    order = null;
                    break;
            }

            if (order != null) {
                orders.add(order);
            }
        }

        conn.close();
        return orders;
    }
    public static Order getLastOrder() throws SQLException, IOException {
        connect_to_db();
        String sqlQuery = "SELECT o.*, d.CustomerID " +
                "FROM ordertable o " +
                "LEFT JOIN delivery d ON o.OrderID = d.OrderID " +
                "ORDER BY o.OrderID DESC LIMIT 1";

        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int orderID = resultSet.getInt("OrderID");
            Integer custID = resultSet.getInt("CustomerID");
            String orderType = resultSet.getString("OrderType");
            String date = resultSet.getString("OrderTime");
            double custPrice = resultSet.getDouble("OrderPriceCustomer");
            double busPrice = resultSet.getDouble("OrderCostBusiness");
            int isComplete = resultSet.getInt("OrderCompletion");

            Order lastOrder = new Order(orderID, custID, orderType, date, custPrice, busPrice, isComplete);
            return lastOrder;
        }

        conn.close();
        return null;
    }

    public static ArrayList<Order> getOrdersByDate(String date) throws SQLException, IOException {
        connect_to_db();
        ArrayList<Order> orders = new ArrayList<>();
        String sqlQuery = "SELECT o.*, d.CustomerID " +
                "FROM ordertable o " +
                "LEFT JOIN delivery d ON o.OrderID = d.OrderID " +
                "LEFT JOIN dinein din ON o.OrderID = din.OrderID " +
                "LEFT JOIN pickup p ON o.OrderID = p.OrderID " +
                "WHERE DATE(o.OrderTime) = ?";

        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setString(1, date);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int orderID = resultSet.getInt("OrderID");
            int custID = resultSet.getInt("CustomerID");
            String orderType = resultSet.getString("OrderType");
            double custPrice = resultSet.getDouble("OrderPriceCustomer");
            double busPrice = resultSet.getDouble("OrderCostBusiness");
            int iscomplete = resultSet.getInt("OrderCompletion");
            String orderTime = resultSet.getString("OrderTime");

            Order order = new Order(orderID, custID, orderType, date, custPrice, busPrice, iscomplete);
            orders.add(order);
        }

        resultSet.close();
        statement.close();
        conn.close();

        return orders;
    }

    public static ArrayList<Discount> getDiscountList() throws SQLException, IOException {
        connect_to_db();
        ArrayList<Discount> discounts = new ArrayList<>();
        String sqlQuery = "SELECT * FROM discount";

        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int discountID = resultSet.getInt("DiscountID");
            String name = resultSet.getString("DiscountName");
            double value = resultSet.getDouble("DiscountValue");
            boolean isPercent = resultSet.getBoolean("DiscountIsPercent");

            Discount discount = new Discount(discountID, name, value, isPercent);
            discounts.add(discount);
        }

        statement.close();
        conn.close();

        return discounts;
    }

    public static Discount findDiscountByName(String name) throws SQLException, IOException {
        connect_to_db();
        String sqlQuery = "SELECT * FROM discount WHERE DiscountName = ?";

        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setString(1, name);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int discountID = resultSet.getInt("DiscountID");
            boolean isPercent = resultSet.getBoolean("DiscountIsPercent");
            double value = resultSet.getDouble("DiscountValue");

            Discount discount = new Discount(discountID, name,value, isPercent);

            statement.close();
            conn.close();

            return discount;
        }

        statement.close();
        conn.close();

        return null;
    }


    public static ArrayList<Customer> getCustomerList() throws SQLException, IOException {
        connect_to_db();
        String sqlQuery = "SELECT * FROM customer ORDER BY CustomerID";
        ArrayList<Customer> customerList = new ArrayList<>();

        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int custID = resultSet.getInt("CustomerID");
            String fName = resultSet.getString("CustomerFName");
            String lName = resultSet.getString("CustomerLName");
            String phone = resultSet.getString("CustomerPhone");
            String street = resultSet.getString("CustomerDeliveryStreet");
            String city = resultSet.getString("CustomerDeliveryCity");
            String state = resultSet.getString("CustomerDeliveryState");
            String zip = resultSet.getString("CustomerDeliveryZIP");

            Customer customer = new Customer(custID, fName, lName, phone);
            customer.setAddress(street, city, state, zip);

            customerList.add(customer);
        }
        conn.close();

        if (customerList.isEmpty()) {
            return null;
        }

        return customerList;
    }
    public static Customer findCustomerByPhone(String phoneNumber) throws SQLException, IOException {
        connect_to_db();
        connect_to_db();
        String sqlQuery = "SELECT * FROM customer WHERE CustomerPhone = ?";

        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setString(1, phoneNumber);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int custID = resultSet.getInt("CustomerID");
            String FName = resultSet.getString("CustomerFName");
            String LName = resultSet.getString("CustomerLName");
            String phone = resultSet.getString("CustomerPhone");
            String street = resultSet.getString("CustomerDeliveryStreet");
            String city = resultSet.getString("CustomerDeliveryCity");
            String state = resultSet.getString("CustomerDeliveryState");
            String zip = resultSet.getString("CustomerDeliveryZIP");

            Customer customer = new Customer(custID, FName, LName, phone);
            customer.setAddress(street, city, state, zip);

            conn.close();
            return customer;
        }

        conn.close();
        return null;
    }

    public static ArrayList<Topping> getToppingList() throws SQLException, IOException {
        connect_to_db();
        String sqlQuery = "SELECT * FROM topping ORDER BY ToppingID";

        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Topping> toppingList = new ArrayList<>();

        while (resultSet.next()) {
            int topID = resultSet.getInt("ToppingID");
            String topName = resultSet.getString("ToppingName");
            double perAMT = resultSet.getDouble("ToppingAmountPerSmall");
            double medAMT = resultSet.getDouble("ToppingAmountPerMedium");
            double lgAMT = resultSet.getDouble("ToppingAmountPerLarge");
            double xlAMT = resultSet.getDouble("ToppingAmountPerXLarge");
            double custPrice = resultSet.getDouble("ToppingPrice");
            double busPrice = resultSet.getDouble("ToppingCost");
            int minINVT = resultSet.getInt("ToppingMinInventory");
            int curINVT = resultSet.getInt("ToppingCurrentInventory");

            Topping topping = new Topping(topID, topName, perAMT, medAMT, lgAMT, xlAMT, custPrice, busPrice, minINVT, curINVT);
            toppingList.add(topping);
        }

        conn.close();
        return toppingList;
    }

    public static Topping findToppingByName(String name) throws SQLException, IOException {
        connect_to_db();
        String sqlQuery = "SELECT * FROM topping WHERE ToppingName = ?";

        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setString(1, name);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int topID = resultSet.getInt("ToppingID");
            String topName = resultSet.getString("ToppingName");
            double perAMT = resultSet.getDouble("ToppingAmountPerSmall");
            double medAMT = resultSet.getDouble("ToppingAmountPerMedium");
            double lgAMT = resultSet.getDouble("ToppingAmountPerLarge");
            double xlAMT = resultSet.getDouble("ToppingAmountPerXLarge");
            double custPrice = resultSet.getDouble("ToppingPrice");
            double busPrice = resultSet.getDouble("ToppingCost");
            int minINVT = resultSet.getInt("ToppingMinInventory");
            int curINVT = resultSet.getInt("ToppingCurrentInventory");

            Topping topping = new Topping(topID, topName, perAMT, medAMT, lgAMT, xlAMT, custPrice, busPrice, minINVT, curINVT);
            conn.close();
            return topping;
        }

        conn.close();
        return null;
    }




    public static void addToInventory(Topping t, double quantity) throws SQLException, IOException {
        connect_to_db();

        String sqlQuery = "UPDATE topping SET ToppingCurrentInventory = ToppingCurrentInventory + ? WHERE ToppingID = ?";

        // Create the PreparedStatement
        PreparedStatement statement = conn.prepareStatement(sqlQuery);

        statement.setDouble(1, quantity);
        statement.setInt(2, t.getTopID());

        statement.executeUpdate();

        // DO NOT FORGET TO CLOSE YOUR CONNECTION
        statement.close();
        conn.close();

    }

    public static double getBaseCustPrice(String size, String crust) throws SQLException, IOException {
        connect_to_db();
        String sqlQuery = "SELECT BasePizzaPrice FROM base WHERE BasePizzaSize = ? AND BaseCrustType = ?";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);

        statement.setString(1, size);
        statement.setString(2, crust);

        ResultSet resultSet = statement.executeQuery();

        double basePrice = 0.0;
        if (resultSet.next()) {
            basePrice = resultSet.getDouble("BasePizzaPrice");
        }

        // DO NOT FORGET TO CLOSE YOUR CONNECTION
        resultSet.close();
        statement.close();
        conn.close();

        // DO NOT FORGET TO CLOSE YOUR CONNECTION
        return basePrice;
    }
    public static double getBaseBusPrice(String size, String crust) throws SQLException, IOException {
        connect_to_db();

        String sqlQuery = "SELECT BasePizzaCost FROM base WHERE BasePizzaSize = ? AND BaseCrustType = ?";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);

        statement.setString(1, size);
        statement.setString(2, crust);

        ResultSet resultSet = statement.executeQuery();

        double baseCustPrice = 0.0;
        if (resultSet.next()) {
            baseCustPrice = resultSet.getDouble("BasePizzaCost");
        }

        resultSet.close();
        statement.close();
        conn.close();
        //DO NOT FORGET TO CLOSE YOUR CONNECTION
        return baseCustPrice;
    }

    public static void printInventory() throws SQLException, IOException {
        //prepared statement and clls the sql query using prepared statement (prevent injections)
        connect_to_db();
        String sqlQuery = "SELECT ToppingName, ToppingCurrentInventory FROM topping ORDER BY ToppingName;";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ResultSet resultSet = statement.executeQuery();

        System.out.println("Topping Name\tCurrent Inventory");

        while (resultSet.next()) {
            String toppingName = resultSet.getString("ToppingName");
            int currentInventory = resultSet.getInt("ToppingCurrentInventory");

            // Print each row
            System.out.println(toppingName + "\t" + currentInventory);
        }

        //DO NOT FORGET TO CLOSE YOUR CONNECTION
        resultSet.close();
        statement.close();
        conn.close();

    }

    public static void printToppingPopReport() throws SQLException, IOException
    {
        //prepared statement and clls the sql query using prepared statement (prevent injections)
        connect_to_db();
        String sqlQuery = "SELECT * FROM ToppingPopularity ORDER BY ToppingCount DESC;";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ResultSet resultSet = statement.executeQuery();

        // print column names
        System.out.println("Topping\tToppingCount");

        // keep printing until values expire
        while (resultSet.next()) {
            String topping = resultSet.getString("Topping");
            int toppingCount = resultSet.getInt("ToppingCount");
            System.out.println(topping + "\t" + toppingCount);
        }

        //DO NOT FORGET TO CLOSE YOUR CONNECTION
        resultSet.close();
        statement.close();
        conn.close();
    }

    public static void printProfitByPizzaReport() throws SQLException, IOException
    {
        //prepared statement and clls the sql query using prepared statement (prevent injections)
        connect_to_db();
        String sqlQuery = "SELECT * FROM ProfitByPizza;";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ResultSet resultSet = statement.executeQuery();

        // print column names
        System.out.println("Size\tCrust\tProfit\tOrder Month");

        // keep printing until values expire
        while (resultSet.next()) {
            String size = resultSet.getString("Size");
            String crust = resultSet.getString("Crust");
            double profit = resultSet.getDouble("Profit");
            String orderMonth = resultSet.getString("Order Month");
            System.out.println(size + "\t" + crust + "\t" + profit + "\t" + orderMonth);
        }

        //DO NOT FORGET TO CLOSE YOUR CONNECTION
        resultSet.close();
        statement.close();
        conn.close();
    }


    public static void printProfitByOrderType() throws SQLException, IOException
    {
        //prepared statement and clls the sql query using prepared statement (prevent injections)
        connect_to_db();
        String sqlQuery = "SELECT * FROM ProfitByOrderType;";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ResultSet resultSet = statement.executeQuery();

        System.out.println("CustomerType\tOrder Month\tTotal Order Price\tTotal Order Cost\tProfit");

        while (resultSet.next()) {
            String customerType = resultSet.getString("CustomerType");
            String orderMonth = resultSet.getString("Order Month");
            double totalOrderPrice = resultSet.getDouble("TotalOrderPrice");
            double totalOrderCost = resultSet.getDouble("TotalOrderCost");
            double profit = resultSet.getDouble("Profit");

            System.out.println(customerType + "\t" + orderMonth + "\t" + totalOrderPrice + "\t" + totalOrderCost + "\t" + profit);

        }

        //DO NOT FORGET TO CLOSE YOUR CONNECTION
        resultSet.close();
        statement.close();
        conn.close();
    }




    public static String getCustomerName(int CustID) throws SQLException, IOException
    {
        /*
         * This is a helper method to fetch and format the name of a customer
         * based on a customer ID. This is an example of how to interact with
         * your database from Java.  It's used in the model solution for this project...so the code works!
         *
         * OF COURSE....this code would only work in your application if the table & field names match!
         *
         */

        connect_to_db();

        String cname2 = "";
        PreparedStatement os;
        ResultSet rset2;
        String query2;
        query2 = "Select CustomerFName, CustomerLName From customer WHERE CustomerID=?;";
        os = conn.prepareStatement(query2);
        os.setInt(1, CustID);
        rset2 = os.executeQuery();
        while(rset2.next())
        {
            cname2 = rset2.getString("CustomerFName") + " " + rset2.getString("CustomerLName"); // note the use of field names in the getSting methods
        }

        conn.close();
        return cname2;
    }

    /*
     * The next 3 private methods help get the individual components of a SQL datetime object.
     * You're welcome to keep them or remove them.
     */
    private static int getYear(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
    {
        return Integer.parseInt(date.substring(0,4));
    }
    private static int getMonth(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
    {
        return Integer.parseInt(date.substring(5, 7));
    }
    private static int getDay(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
    {
        return Integer.parseInt(date.substring(8, 10));
    }

    public static boolean checkDate(int year, int month, int day, String dateOfOrder)
    {
        if(getYear(dateOfOrder) > year)
            return true;
        else if(getYear(dateOfOrder) < year)
            return false;
        else
        {
            if(getMonth(dateOfOrder) > month)
                return true;
            else if(getMonth(dateOfOrder) < month)
                return false;
            else
            {
                if(getDay(dateOfOrder) >= day)
                    return true;
                else
                    return false;
            }
        }
    }
    public static void updateOrderValues(Order order) throws SQLException, IOException {
        connect_to_db();


        String updateQuery = "UPDATE ordertable SET OrderPriceCustomer = ?, OrderCostBusiness = ? WHERE OrderID = ?";
        PreparedStatement updateStatement = conn.prepareStatement(updateQuery);

        updateStatement.setDouble(1, order.getCustPrice());
        updateStatement.setDouble(2, order.getBusPrice());
        updateStatement.setInt(3, order.getOrderID());

        updateStatement.executeUpdate();


        conn.close();
    }
        public static ArrayList<Pizza> getPizzaList() throws SQLException, IOException {
        connect_to_db();

        String sqlQuery = "SELECT * FROM pizza";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ResultSet resultSet = statement.executeQuery();

        ArrayList<Pizza> pizzaList = new ArrayList<>();

        while (resultSet.next()) {
            int pizzaID = resultSet.getInt("PizzaID");
            String crustType = resultSet.getString("BaseCrustType");
            String pizzaSize = resultSet.getString("BasePizzaSize");
            double totalCustPrice = resultSet.getDouble("PizzaTotalPrice");
            double totalBusPrice = resultSet.getDouble("PizzaTotalCost");
            String pizzaStatus = resultSet.getString("PizzaStatus");
            int orderID = resultSet.getInt("OrderID");

            Pizza pizza = new Pizza(pizzaID, pizzaSize, crustType, orderID, pizzaStatus, " ", totalCustPrice, totalBusPrice);
            pizzaList.add(pizza);
        }

        // Close ResultSet, PreparedStatement, and connection
        resultSet.close();
        statement.close();
        conn.close();

        return pizzaList;
    }


}