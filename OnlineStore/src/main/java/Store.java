import com.pluralsight.Cart;
import com.pluralsight.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {
    // Create scanner for input
    static Scanner scanner = new Scanner(System.in);
    // Create inventory ArrayList
    static ArrayList<Product> inventory = new ArrayList<>();

    //static ArrayList<Product> cart = new ArrayList<>();
    static Cart cart = new Cart();

    public static void main(String[] args) {
        // Populate the inventory
        readFromFile();
        System.out.println("Welcome to The Online Store!");
        homeMenu();
    }

    public static void homeMenu() {
        while (true) {

            System.out.println("""
                    What would you like to do?
                    \t\
                    1) Display Products
                    \t\
                    2) Display Cart
                    \t\
                    3) Exit""");

            int choice = scanner.nextInt();


            switch (choice) {
                case 1:
                    displayProducts();
                    break;
                case 2:
                    displayCart();
                    break;
                case 3:
                    scanner.close();
                    return;

            }

        }
    }

    public static void displayCart() {
        // display cart
        if (!cart.getItems().isEmpty()) {
            System.out.println(cart.getItems());
            System.out.println(cart.getTotalPrice());

            System.out.println("""
                    What would you like to do?
                    \t\
                    1) Remove Item
                    \t\
                    2) Check Out
                    \t\
                    3) Return to Main Menu""");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    removeFromCart();
                    break;
                case 2:
                    checkOut();
                    break;
                case 3:
                    homeMenu();

            }
        } else {
            System.out.println("You have no items in your cart");
            homeMenu();
            //continue;
        }
    }

    public static void removeFromCart() {
        System.out.println("Enter the name of the item you want to remove");
        scanner.nextLine();
        String itemName = scanner.nextLine();
        for (Product item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                cart.removeFromCart(item);
                System.out.println("Item removed");
                break;
            }
        }
    }

    public static void checkOut() {
        System.out.println("Sales total: $" + cart.getTotalPrice());

        System.out.print("Please enter cash amount: $");
        double payment = scanner.nextDouble();

        if (payment >= cart.getTotalPrice()) {
            double change = (payment - cart.getTotalPrice());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d, yyyy  hh:mm");
            LocalDateTime purchaseDate = LocalDateTime.now();


            System.out.printf(purchaseDate.format(formatter) + "\n"
                    + cart.getItems()
                    + "\n" + cart.getTotalPrice()
                    + "\n" + payment
                    + "\n%.2f\n", change);
            for (Product item : inventory) {

                cart.removeFromCart(item);
            }
            homeMenu();
        }
    }

    public static void displayProducts() {
        for (Product item : inventory) {
            System.out.println(item.toString());
        }
        System.out.println("""
                \nWhat would you like to do?
                \t\
                1) Search for items
                \t\
                2) Filter items
                \t\
                3) Return to home screen""");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                searchInventory();
                break;
            case 2:
                filterInventory();
                break;
            case 3:
                break;
        }

    }

    public static void searchInventory() {
        scanner.nextLine();
        System.out.print("Please enter the name of the item: ");
        String item = scanner.nextLine();

        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(item)) {
                System.out.println(product.toString());

                System.out.println("Add to cart? (Y/N)");
                String add = scanner.nextLine();
                if (add.equalsIgnoreCase("y")) {
                    cart.addToCart(product);
                    homeMenu();
                }
            }
        }
        System.out.println("Item not found");
    }

    public static void filterInventory() {
        System.out.println("""
                What would you like to filter by?
                \t\
                1) Price range
                \t\
                2) Department""");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            filterByPriceRange();
        } else if (choice == 2) {
            filterByDepartment();
        } else {
            System.out.println("Invalid choice");
            homeMenu();
        }
    }

    public static void filterByPriceRange() {
        float minPrice;
        float maxPrice;
        System.out.println("Please enter the minimum price of the product: ");
        minPrice = scanner.nextFloat();
        System.out.println("Please enter the maximum price of the product: ");
        maxPrice = scanner.nextFloat();
        for (Product product : inventory) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                System.out.println(product);
            }
        }
        searchInventory();
    }

    public static void filterByDepartment() {

        System.out.println("Please enter the department you would like to filter by: ");
        String department = scanner.nextLine();
        for (Product item : inventory) {
            if (item.getDepartment().equalsIgnoreCase(department)) {
                System.out.println(item.toString());

            }
        }
        System.out.println("Press Enter to pick item.");
        searchInventory();
    }

    public static void readFromFile() {
        // Try to open the file
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("products.csv"));
            String input;
            boolean skippedHeader = false;

            while ((input = bfr.readLine()) != null) {
                if (!skippedHeader) {
                    skippedHeader = true;
                    continue;
                }
                // Populate the CSV inventory
                getCSVInventory(input);
            }
            bfr.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    // Populate the CSV inventory
    public static void getCSVInventory(String input) {
        // split the input
        String[] tokens = input.split("\\|");

        // Parse the tokens
        String sku = tokens[0];
        String name = tokens[1];
        double price = Double.parseDouble(tokens[2]);
        String department = tokens[3];

        // Creates the product
        Product product = new Product(sku, name, price, department);

        // Adds the product to csvInventory
        inventory.add(product);
    }
}
