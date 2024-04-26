// completed by rashed jaafar.
package com.pluralsight;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    // Create the list.
    private List<Product> items;

    // Initialize the array.
    public Cart() {
        items = new ArrayList<>();
    }

    // Create the addToCart method.
    public void addToCart(Product product) {
        items.add(product);
    }

    // Create the removeFromCart method.
    public void removeFromCart(Product product) {
        items.remove(product);
    }

    // Create the getter.
    public List<Product> getItems() {
        return items;
    }

    // Create the method to calculate the cart total.
    public double getTotalPrice() {
        double total = 0;
        for (Product item : items) {
            total += item.getPrice();
        }
        return total;
    }

}


