import com.pluralsight.Cart;
import com.pluralsight.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        readFromFile("products.csv");
        System.out.println("Welcome to The Online Store!");
        homeMenu();
    }

    public static void homeMenu() {
        while (true) {

            System.out.println("What would you like to do?\n\t" +
                    "1) Display Products\n\t" +
                    "2) Display Cart\n\t" +
                    "3) Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayProducts();
                    break;
                case 2:
                    // display cart
                    if (!cart.getItems().isEmpty()) {
                        System.out.println(cart.getItems());
                        System.out.println("Would you like to Remove an Item? (Y/N)");
                        scanner.nextLine();
                        String remove = scanner.nextLine();
                        if (remove.equalsIgnoreCase("y")) {
                            System.out.println("Enter the name of the item you want to remove");
                            String itemName = scanner.nextLine();
                            for (Product item : inventory) {
                                if (item.getName().equalsIgnoreCase(itemName)){
                                    cart.removeFromCart(item);
                                    System.out.println("Item removed");
                                    break;
                                }

                            }
                        }
                    } else {
                        System.out.println("You have no items in your cart");
                        homeMenu();
                        //continue;
                    }
                    break;

                case 3:
                    scanner.close();
                    return;

            }


        }
    }

    public static void displayProducts() {
        for (Product item : inventory) {
            System.out.println(item.toString());

        }
        System.out.println(" ");
        System.out.println("What would you like to do?");
        System.out.println("\t1) Search or filter items\n\t" +
                "2) Return to home screen");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                searchInventory();
                break;
            case 2:
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


    public static void readFromFile(String filename) {
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
            throw new RuntimeException(e);
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