package com.example.finalspractice.model;

public class Main {
    public static void main(String[] args) {
        // Test out the model

        InventoryManager im = new InventoryManager();
        System.out.println("Inventory manager");
        PhysicalProduct p1 = new PhysicalProduct(1, "Rubber Ducky", 2.00);

        DigitalProduct p2 = new DigitalProduct(2, "World of Warcraft", 49.00);

        im.addProduct(p1);
        im.addProduct(p2);

        // Side effects so that the developer can observe it worked

        // Loop through the products and print them out
        for (Product p : im.getProducts()) {
            System.out.println(p.toString());
        }
    }
}
