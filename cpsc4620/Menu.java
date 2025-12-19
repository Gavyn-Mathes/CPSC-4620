package cpsc4620;


import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectStreamException;
import java.security.KeyStore;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/*
 * This file is where the front end magic happens.
 *
 * You will have to write the methods for each of the menu options.
 *
 * This file should not need to access your DB at all, it should make calls to the DBNinja that will do all the connections.
 *
 * You can add and remove methods as you see necessary. But you MUST have all of the menu methods (including exit!)
 *
 * Simply removing menu methods because you don't know how to implement it will result in a major error penalty (akin to your program crashing)
 *
 * Speaking of crashing. Your program shouldn't do it. Use exceptions, or if statements, or whatever it is you need to do to keep your program from breaking.
 *
 */


public class Menu {


	public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


	public static void main(String[] args) throws SQLException, IOException {


		System.out.println("Welcome to Pizzas-R-Us!");

		int menu_option = 0;


		// present a menu of options and take their selection


		PrintMenu();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String option = reader.readLine();
		menu_option = Integer.parseInt(option);


		while (menu_option != 9) {
			switch (menu_option) {
				case 1:// enter order
					EnterOrder();
					break;
				case 2:// view customers
					viewCustomers();
					break;
				case 3:// enter customer
					EnterCustomer();
					break;
				case 4:// view order
					// open/closed/date
					ViewOrders();
					break;
				case 5:// mark order as complete
					MarkOrderAsComplete();
					break;
				case 6:// view inventory levels
					ViewInventoryLevels();
					break;
				case 7:// add to inventory
					AddInventory();
					break;
				case 8:// view reports
					PrintReports();
					break;
                case 10:
                    System.out.print(DBNinja.findCustomerByPhone("000000000"));
                    break;
			}
			PrintMenu();
			option = reader.readLine();
			menu_option = Integer.parseInt(option);
        }

	}


	// allow for a new order to be placed
	public static void EnterOrder() throws SQLException, IOException {

		// Grab next orderId
		int orderId = 1;
        Order lastOrder = DBNinja.getLastOrder();
        if (lastOrder != null) orderId = lastOrder.getOrderID() + 1;
        //      * EnterOrder should do the following:
        //  Ask if the order is delivery, pickup, or dinein
        System.out.println("Is this order for:\n1.) Dine-in\n2.) Pick-up\n3.) Delivery\nEnter the number of your choice:");
        int orderType = Integer.parseInt(reader.readLine());
        String string;
        int customerId;
        Order order = null;
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = currentDate.format(formatter);
        switch (orderType) {
            //dinein
            case 1:
                System.out.println("What is the table number for this order?");
                int tableNumber = Integer.parseInt(reader.readLine());
                order = new DineinOrder(orderId, 0, date, 0, 0, 0, tableNumber);
                order.setOrderType(DBNinja.dine_in);
                break;
            //   if pickup...
            case 2:
                System.out.println("Is this order for an existing customer? Answer y/n: ");
                string = reader.readLine();
                while (!Objects.equals(string, "y") && !Objects.equals(string, "Y") && !Objects.equals(string, "n") && !Objects.equals(string, "N")) {
                    System.out.println("ERROR: I don't understand your input for: Is this order an existing customer?");
                    string = reader.readLine();
                }
                if (Objects.equals(string, "y") || Objects.equals(string, "Y")) {
                    if (DBNinja.getCustomerList() == null) { System.out.println("No customers. Returning..."); return; }
                    System.out.println("Here's a list of the current customers: ");
                    viewCustomers();
                    System.out.println("Which customer is this order for? Enter ID Number:");
                    customerId = Integer.parseInt(reader.readLine());
                } else {
                    EnterCustomer();
                    // Get most recent customer
                    ArrayList<Customer> customers = DBNinja.getCustomerList();
                    customerId = customers.get(customers.size()-1).getCustID();
                }
                order = new PickupOrder(orderId, customerId, date, 0, 0, 0, 0);
                order.setOrderType(DBNinja.pickup);
				break;
            case 3:
                // if delivery...
                Customer customer = null;
                System.out.println("Is this order for an existing customer? Answer y/n: ");
                string = reader.readLine();
                while (!Objects.equals(string, "y") && !Objects.equals(string, "Y") && !Objects.equals(string, "n") && !Objects.equals(string, "N")) {
                    System.out.println("ERROR: I don't understand your input for: Is this order an existing customer?");
                    string = reader.readLine();
                }
                if (Objects.equals(string, "y") || Objects.equals(string, "Y")) {
                    if (DBNinja.getCustomerList() == null) { System.out.println("No customers. Returning..."); return; }
                    System.out.println("Here's a list of the current customers: ");
                    viewCustomers();
                    System.out.println("Which customer is this order for? Enter ID Number:");
                    customerId = Integer.parseInt(reader.readLine());
                } else {
                    // Can't update address Once we create customer and push therefore
                    // Must duplicate code here
                    // James if you can find a way around that would be sweet
                    System.out.println("What is this customer's name (first <space> last)?");
                    String name = reader.readLine();
                    String[] splitName = name.trim().split("\\s+");

                    System.out.println("What is this customer's phone number (##########)?");
                    String phoneNumber = reader.readLine();
                    // Get most recent customer
                    ArrayList<Customer> customers = DBNinja.getCustomerList();
                    if (customers == null) customerId = 1;
                    else customerId = customers.get(customers.size() - 1).getCustID() + 1;
                    System.out.println("What is the House/Apt Number for this order? (e.g., 111)");
                    String houseNumber = reader.readLine();
                    System.out.println("What is the Street for this order? (e.g., SmileStreet)");
                    String street = reader.readLine();
                    System.out.println("What is the City for this order? (e.g., Greenville)");
                    String city = reader.readLine();
                    System.out.println("What is the State for this order? (e.g., SC)");
                    String state = reader.readLine();
                    System.out.println("What is the Zip Code for this order? (e.g., 20605)");
                    String zip = reader.readLine();
                    customer = new Customer(customerId, splitName[0], splitName[splitName.length-1], phoneNumber);
                    customer.setAddress(houseNumber+" "+street, city, state, zip);
                    DBNinja.addCustomer(customer);
                }
                if (customer != null) order = new DeliveryOrder(orderId, customerId, date, 0, 0, 0, customer.getAddress());
                else order = new DeliveryOrder(orderId, customerId, date, 0, 0, 0, " ");
                order.setOrderType(DBNinja.delivery);
				break;
            default:
                System.out.println("I don't understand that input. Returning to menu.");
                return;
        }

        DBNinja.addOrder(order);
		String exitValue;
        Pizza tempPizza;
        ArrayList<Pizza> pizzas = new ArrayList<>();
        do {
			System.out.println("Let's build a pizza!");
			tempPizza = buildPizza(orderId);
            pizzas.add(tempPizza);
            System.out.println("Enter -1 to stop adding pizzas...Enter anything else to continue adding pizzas to the order.");
            exitValue = reader.readLine();
        } while (!Objects.equals(exitValue, "-1"));

//      *
//      * Apply order discounts as needed (including to the DB)
//      *

        System.out.println("Do you want to add discounts to this order? Enter y/n?");
        string = reader.readLine();
        ArrayList<Discount> discounts = new ArrayList<>();
        ArrayList<Discount> discountList = DBNinja.getDiscountList();
        if (Objects.equals(string, "y") || Objects.equals(string, "Y")) {
            do {
                for (Discount discount : discounts) {
                    System.out.println(discount);
                }
                System.out.println("Which Order Discount do you want to add? Enter the DiscountID. Enter -1 to stop adding Discounts: ");
                exitValue = reader.readLine();
                int intOption;
                if (!Objects.equals(exitValue, "-1")) {

                    intOption = Integer.parseInt(exitValue);
                    if (intOption > 0 && intOption < 7) {
                        if (!discounts.contains(discountList.get(intOption - 1)))
                            discounts.add(discountList.get(Integer.parseInt(exitValue) - 1));
                        else System.out.println("Invalid Discount ID.");
                    } else System.out.println("Invalid Discount ID.");
                }
            } while (!Objects.equals(exitValue, "-1"));
        }

        double custCost = 0;
        double busCost = 0;
        for (Pizza pizza : pizzas) {
            custCost += pizza.getCustPrice();
            busCost += pizza.getBusPrice();
        }

        // Add Order discounts
        order.setCustPrice(custCost);
        order.setBusPrice(busCost);

        for (Pizza pizza :pizzas) {
            order.addPizza(pizza);
        }

        for (Discount discount: discounts) {
            order.addDiscount(discount);
            DBNinja.useOrderDiscount(order, discount);
        }

        DBNinja.updateOrderValues(order);

        System.out.println("Finished adding order...Returning to menu...");

    }




    // Done
	public static void viewCustomers() throws SQLException, IOException {
		ArrayList<Customer> customers = DBNinja.getCustomerList();

        if (customers == null) return;

		for (Customer customer : customers) {
			System.out.println(customer.toString());
		}
	}




	// Enter a new customer in the database
    // Done
	public static void EnterCustomer() throws SQLException, IOException {
		System.out.println("What is this customer's name (first <space> last)?");
		String name = reader.readLine();
        String[] splitName = name.trim().split("\\s+");

		System.out.println("What is this customer's phone number (##########)?");
		String phoneNumber = reader.readLine();

        ArrayList<Customer> customers = DBNinja.getCustomerList();
        int customerId = 1;
        if (customers != null) {
            customerId = customers.get(customers.size() - 1).getCustID();
        }
        Customer customer = new Customer(customerId, splitName[0], splitName[splitName.length-1], phoneNumber);

        DBNinja.addCustomer(customer);
	}


	// View any orders that are not marked as completed
	// THIS AND BUILD PIZZA NEED WORK, SOME CODE I WAS RUNNING WITH BUT CHANGE IF YOU NEED - JAMES ----- TO GAVYN
	public static void ViewOrders() throws SQLException, IOException {
     /*
      * This method allows the user to select between three different views of the Order history:
      * The program must display:
      * a.  all open orders
      * b.  all completed orders
      * c.  all the orders (open and completed) since a specific date (inclusive)
      *
      * After displaying the list of orders (in a condensed format) must allow the user to select a specific order for viewing its details.
      * The details include the full order type information, the pizza information (including pizza discounts), and the order discounts.
      *
      */
     System.out.println("Would you like to:\n(a) display all orders [open or closed]\n(b) display all open orders\n(c) display all completed [closed] orders\n(d) display orders since a specific date");

     String option = reader.readLine();
     ArrayList<Order> orders = new ArrayList<Order>();
     switch (option.toLowerCase()) {
        case "a":
           // Display all orders
           ArrayList<Order> allOpenOrders = DBNinja.getOrders(true);
		   ArrayList<Order> allCloseOrders = DBNinja.getOrders(false);
           orders.addAll(allOpenOrders);
           orders.addAll(allCloseOrders);
           orders.sort(Comparator.comparingInt(Order::getOrderID));
            if (orders.isEmpty()) {
               System.out.println("No orders to display, returning to menu.");
           } else {
              System.out.println("All Orders:");
              for (Order order : orders) {
                 System.out.println(order.toSimplePrint());
              }
           }
           break;
        case "b":
           // Open Orders
           orders = DBNinja.getOrders(true);
           Collections.reverse(orders);
           if (orders.isEmpty()) {
              System.out.println("No orders to display, returning to menu.");
           } else {
              System.out.println("Open Orders:");
              for (Order order : orders) {
                 System.out.println(order.toSimplePrint());
              }
           }
           break;
        case "c":
           // Closed/Completed Orders
           orders = DBNinja.getOrders(false);
           Collections.reverse(orders);
           if (orders.isEmpty()) {
              System.out.println("No orders to display, returning to menu.");
           } else {
              System.out.println("Completed Orders:");
              for (Order order : orders) {
                 System.out.println(order.toSimplePrint());
              }
           }
           break;
        case "d":
           System.out.println("What is the date you want to restrict by? (FORMAT= YYYY-MM-DD)");
           String date = reader.readLine();

           orders = DBNinja.getOrdersByDate(date);
           Collections.reverse(orders);
           if (orders.isEmpty()) {
              System.out.println("No orders to display, returning to menu.");
           } else {
              System.out.println("Orders since " + date + ":");
              for (Order order : orders) {
                 System.out.println(order.toSimplePrint());
              }
           }
           break;
        default:
            System.out.println("Incorrect entry, returning to menu.");
            break;
     }

     if (orders.isEmpty()) return;

     ArrayList<Integer> intOrders = new ArrayList<Integer>();
        for (Order order : orders) {
            intOrders.add(order.getOrderID());
        }
     do {
         System.out.println("Which order would you like to see in detail? Enter the number (-1 to exit): ");
         option = reader.readLine();
         if (!Objects.equals(option, "-1")) {
             if (intOrders.contains(Integer.parseInt(option))) {
                 System.out.println(orders.get(intOrders.indexOf(Integer.parseInt(option))).toString());
             } else {
                 System.out.println("Incorrect entry, returning to menu.");
                 return;
             }
         }
     } while (!Objects.equals(option, "-1"));
	}




	// When an order is completed, we need to make sure it is marked as complete
	public static void MarkOrderAsComplete() throws SQLException, IOException {
		/*
		 * All orders that are created through java (part 3, not the orders from part 2) should start as incomplete
		 *
		 * When this method is called, you should print all of the "open" orders marked
		 * and allow the user to choose which of the incomplete orders they wish to mark as complete
		 *
		 */

        ArrayList<Order> allOpenOrders = DBNinja.getOrders(true);
        if (allOpenOrders.isEmpty()) {
            System.out.println("There are no open orders currently... returning to menu...");
            return;
        }

        System.out.println("Which order would you like mark as complete? Enter the OrderID: ");
        String option;
        option = reader.readLine();
        int intOption = Integer.parseInt(option);
        ArrayList<Integer> intOrders = new ArrayList<Integer>();
        for (Order allOpenOrder : allOpenOrders) {
            intOrders.add(allOpenOrder.getOrderID());
        }

        if (intOrders.contains(intOption)) {
            Order order = allOpenOrders.get(intOrders.indexOf(intOption));
            order.setIsComplete(1);
            DBNinja.completeOrder(order);
            for (int i = 0; i < order.getPizzaList().size(); i++) {
                order.getPizzaList().get(i).setPizzaState("completed");
            }
        } else {
            System.out.println("Incorrect entry, not an option");
        }
	}


	public static void ViewInventoryLevels() throws SQLException, IOException {
		/*
		 * Print the inventory. Display the topping ID, name, and current inventory
		 */
        ArrayList<Topping> toppings = DBNinja.getToppingList();
        // Print levels
        for (Topping topping : toppings) {
            System.out.println(topping.toString());
        }
	}




	public static void AddInventory() throws SQLException, IOException {
		/*
		 * This should print the current inventory and then ask the user which topping (by ID) they want to add more to and how much to add
		 */
        ViewInventoryLevels();
        ArrayList<Topping> toppings = DBNinja.getToppingList();
        System.out.println("Which topping do you want to add inventory to? Enter the number: ");
        String option;
        option = reader.readLine();
        int intOption;
        intOption = Integer.parseInt(option);

        if (intOption < 0 || intOption > toppings.size()+1) { // Fix
            System.out.println("Incorrect entry, not an option");
            return;
        }

        Topping topping = toppings.get(intOption-1);

        System.out.println("How many units would you like to add? ");
        option = reader.readLine();
        intOption = Integer.parseInt(option);


        if (intOption < 0) { // Fix
            System.out.println("Incorrect entry, not an option");
            return;
        }

        // ADD TO TOPPING AND PUSH
        DBNinja.addToInventory(topping, intOption);

		// User Input Prompts...
//		System.out.println("Which topping do you want to add inventory to? Enter the number: ");
//		System.out.println("How many units would you like to add? ");
//		System.out.println("Incorrect entry, not an option");

	}


	// A method that builds a pizza. Used in our add new order method
	public static Pizza buildPizza(int orderID) throws SQLException, IOException {


     /*

      * This is a helper method for first menu option.
      *
      * It should ask which size pizza the user wants and the crustType.
      *
      * Once the pizza is created, it should be added to the DB.
      *
      * We also need to add toppings to the pizza. (Which means we not only need to add toppings here, but also our bridge table)
      *
      * We then need to add pizza discounts (again, to here and to the database)
      *
      * Once the discounts are added, we can return the pizza
      */
     Pizza ret = new Pizza(0, " ", " ", orderID, "notcompleted", null, 0, 0);
     ArrayList<Discount> discounts = DBNinja.getDiscountList();
     ArrayList<Discount> selectedDiscounts = new ArrayList<Discount>();
     ArrayList<Topping> selectedToppings = new ArrayList<Topping>();
     ArrayList<Boolean> isDoubled = new ArrayList<Boolean>();
     System.out.println("What size is the pizza?");
     System.out.println("1." + DBNinja.size_s);
     System.out.println("2." + DBNinja.size_m);
     System.out.println("3." + DBNinja.size_l);
     System.out.println("4." + DBNinja.size_xl);
     System.out.println("Enter the corresponding number: ");
     int sizeChoice = Integer.parseInt(reader.readLine());

     System.out.println("What crust for this pizza?");
     System.out.println("1." + DBNinja.crust_thin);
     System.out.println("2." + DBNinja.crust_orig);
     System.out.println("3." + DBNinja.crust_pan);
     System.out.println("4." + DBNinja.crust_gf);
     System.out.println("Enter the corresponding number: ");
     int crustChoice = Integer.parseInt(reader.readLine());

     String size = "";
     String crust = "";
     switch (sizeChoice) {
        case 1:
           size = DBNinja.size_s;
           break;
        case 2:
           size = DBNinja.size_m;
           break;
        case 3:
           size = DBNinja.size_l;
           break;
        case 4:
           size = DBNinja.size_xl;
           break;
        default:
           System.out.println("Invalid");
           return null;
     }
     ret.setSize(size);
     switch (crustChoice) {
        case 1:
           crust = DBNinja.crust_thin;
           break;
        case 2:
           crust = DBNinja.crust_orig;
           break;
        case 3:
           crust = DBNinja.crust_pan;
           break;
        case 4:
           crust = DBNinja.crust_gf;
           break;
        default:
           System.out.println("Invalid");
           return null;
     }
     ret.setCrustType(crust);

     ret.setCustPrice(DBNinja.getBaseCustPrice(size, crust));
     ret.setBusPrice(DBNinja.getBaseBusPrice(size, crust));

     System.out.println("Available Toppings:");
     ArrayList<Topping> toppings = DBNinja.getToppingList();
     for (Topping topping : toppings) {
        System.out.println(topping.getTopID() + ". " + topping.getTopName());
     }
     System.out.println("Which topping do you want to add? Enter the TopID. Enter -1 to stop adding toppings: ");
     int toppingChoice = Integer.parseInt(reader.readLine());

     while (toppingChoice != -1) {
        Topping selectedTopping = null;
        for (Topping topping : toppings) {
           if (topping.getTopID() == toppingChoice) {
              selectedTopping = topping;
              break;
           }
        }
        if (selectedTopping != null) {
           // Ask if the topping should be doubled
           System.out.println("Do you want to add this topping as extra? Enter y/n: ");
           String extraToppingChoice = reader.readLine();
           isDoubled.add(extraToppingChoice.equalsIgnoreCase("y"));

           if (!selectedToppings.contains(toppings.get(toppingChoice-1))) {
               selectedToppings.add(toppings.get(toppingChoice - 1));
               ret.addToppings(selectedTopping, isDoubled.get(isDoubled.size() - 1));
           } else {
               System.out.println("Invalid topping ID. Please try again.");
           }
        } else {
           System.out.println("Invalid topping ID. Please try again.");
        }
        System.out.println("Enter another topping ID or -1 to stop: ");
        toppingChoice = Integer.parseInt(reader.readLine());
     }

    // Ask the user if they want to add discounts to the pizza
     System.out.println("Do you want to add discounts to this Pizza? Enter y/n?");
     String discountChoice = reader.readLine();

     if (discountChoice.equalsIgnoreCase("y")) {
        // Prompt the user to choose pizza discounts
         for (Discount discount : discounts) {
             System.out.println(discount);
         }
        System.out.println("Which Pizza Discount do you want to add? Enter the DiscountID. Enter -1 to stop adding Discounts: ");
        int pizzaDiscountChoice = Integer.parseInt(reader.readLine());
        while (pizzaDiscountChoice != -1) {
            Discount selectedDiscount = null;
           if (pizzaDiscountChoice < 1 || pizzaDiscountChoice > 7) {
               System.out.println("Invalid discount ID. Please try again.");
           } else {
               selectedDiscount = discounts.get(pizzaDiscountChoice);
               if (selectedDiscounts.contains(selectedDiscount)) {
                   System.out.println("Invalid discount ID. Please try again.");
               } else {
                   selectedDiscounts.add(selectedDiscount);
               }
               System.out.println("Which Pizza Discount do you want to add? Enter the DiscountID. Enter -1 to stop adding Discounts: ");
               pizzaDiscountChoice = Integer.parseInt(reader.readLine());
           }
        }
     }

        DBNinja.addPizza(ret);
        ArrayList<Pizza> pizzas = DBNinja.getPizzaList();
        ret.setPizzaID(pizzas.get(pizzas.size()-1).getPizzaID());

        int i = 0;
        for (Topping topping : selectedToppings) {
            DBNinja.useTopping(ret, topping, isDoubled.get(i));
            i++;
        }

        for (Discount discount : selectedDiscounts) {
            DBNinja.usePizzaDiscount(ret, discount);
        }

        return ret;
	}


	public static void PrintReports() throws SQLException, NumberFormatException, IOException
	{
		/*
		 * This method asks the user which report they want to see and calls the DBNinja method to print the appropriate report.
		 *
		 */
        System.out.println("Which report do you wish to print? Enter\n(a) ToppingPopularity\n(b) ProfitByPizza\n(c) ProfitByOrderType:");
        String option = reader.readLine();

        switch(option) {
            case "a":
                DBNinja.printToppingPopReport();
                break;
            case "b":
                DBNinja.printProfitByPizzaReport();
                break;
            case "c":
                DBNinja.printProfitByOrderType();
                break;
            default:
                System.out.println("I don't understand that input... returning to menu...");
        }


        // User Input Prompts...
//		System.out.println("Which report do you wish to print? Enter\n(a) ToppingPopularity\n(b) ProfitByPizza\n(c) ProfitByOrderType:");
//		System.out.println("I don't understand that input... returning to menu...");
	}


	//Prompt - NO CODE SHOULD TAKE PLACE BELOW THIS LINE
	// DO NOT EDIT ANYTHING BELOW HERE, THIS IS NEEDED TESTING.
	// IF YOU EDIT SOMETHING BELOW, IT BREAKS THE AUTOGRADER WHICH MEANS YOUR GRADE WILL BE A 0 (zero)!!


	public static void PrintMenu() {
		System.out.println("\n\nPlease enter a menu option:");
		System.out.println("1. Enter a new order");
		System.out.println("2. View Customers ");
		System.out.println("3. Enter a new Customer ");
		System.out.println("4. View orders");
		System.out.println("5. Mark an order as completed");
		System.out.println("6. View Inventory Levels");
		System.out.println("7. Add Inventory");
		System.out.println("8. View Reports");
		System.out.println("9. Exit\n\n");
		System.out.println("Enter your option: ");
	}


	/*
	 * autograder controls....do not modiify!
	 */


	public final static String autograder_seed = "6f1b7ea9aac470402d48f7916ea6a010";



	private static void autograder_compilation_check() {


		try {
			Order o = null;
			Pizza p = null;
			Topping t = null;
			Discount d = null;
			Customer c = null;
			ArrayList<Order> alo = null;
			ArrayList<Discount> ald = null;
			ArrayList<Customer> alc = null;
			ArrayList<Topping> alt = null;
			double v = 0.0;
			String s = "";


			DBNinja.addOrder(o);
			DBNinja.addPizza(p);
			DBNinja.useTopping(p, t, false);
			DBNinja.usePizzaDiscount(p, d);
			DBNinja.useOrderDiscount(o, d);
			DBNinja.addCustomer(c);
			DBNinja.completeOrder(o);
			alo = DBNinja.getOrders(false);
			o = DBNinja.getLastOrder();
			alo = DBNinja.getOrdersByDate("01/01/1999");
			ald = DBNinja.getDiscountList();
			d = DBNinja.findDiscountByName("Discount");
			alc = DBNinja.getCustomerList();
			c = DBNinja.findCustomerByPhone("0000000000");
			alt = DBNinja.getToppingList();
			t = DBNinja.findToppingByName("Topping");
			DBNinja.addToInventory(t, 1000.0);
			v = DBNinja.getBaseCustPrice("size", "crust");
			v = DBNinja.getBaseBusPrice("size", "crust");
			DBNinja.printInventory();
			DBNinja.printToppingPopReport();
			DBNinja.printProfitByPizzaReport();
			DBNinja.printProfitByOrderType();
			s = DBNinja.getCustomerName(0);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
}
