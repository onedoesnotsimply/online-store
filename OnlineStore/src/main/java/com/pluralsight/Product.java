package com.pluralsight;
public class Product {
    private String sku;
    private String name;
    private double price;
    private String department;

    public Product(String sku, String name, double price, String department) {
        this.department = department;
        this.name = name;
        this.price = price;
        this.sku = sku;
    }

    @Override
    public String toString() {
        return "Product: " +
                department + "|"+
                name + "|" +
                price + "|" +
                sku + "|";
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

}
