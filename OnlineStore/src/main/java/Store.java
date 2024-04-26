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
        homeMenu();
    }

    public static void homeMenu() {
        System.out.println("Welcome to The Online Store!");
        while(true){

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
                            System.out.println(cart.getItems());
                            break;
                        case 3:
                            return;
                    }


                }
    }

    public static void displayProducts() {
        for (Product item : inventory){
            System.out.println(item.toString());

        }
        System.out.println(" ");
        System.out.println("What would you like to do?");
        System.out.println("\t1) Search or filter items\n\t" +
                "2) Add an item to your cart\n\t" +
                "3) Return to home screen");

        int choice = scanner.nextInt();

        switch (choice){
            case 1:
                searchInventory();
                break;
            case 2:
                //addToCart();
                break;
            case 3:
                break;
        }

    }

    public static void searchInventory() {

    }


    public static void readFromFile(String filename){
        // Try to open the file
        try{
            BufferedReader bfr = new BufferedReader(new FileReader("products.csv"));
            String input;
            boolean skippedHeader = false;

            while ((input = bfr.readLine()) != null){
                if (!skippedHeader){
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
        Product product = new Product(sku, name, price,department);

        // Adds the product to csvInventory
        inventory.add(product);
    }
}
