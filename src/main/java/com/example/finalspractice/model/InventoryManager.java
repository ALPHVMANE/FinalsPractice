package com.example.finalspractice.model;

import java.util.*;


public class InventoryManager {
    private List<Product> products;
    private List<Category> categories;

    public InventoryManager() {
        products = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> categories) { this.categories = categories; }

//    public void addDigitalProduct(int id, String name, double price) {
//        DigitalProduct digitalProduct = new DigitalProduct(id, name, price);
//        products.add(digitalProduct);
//    }
//    public void addPhysicalProduct(int id, String name, double price) {
//        PhysicalProduct physicalProduct = new PhysicalProduct(id, name, price);
//        products.add(physicalProduct);
//    }
    public void addProduct(Product product) {
        products.add(product);
    }
    public void addCategory(Category category) {
        categories.add(category);
    }
    public List<Product> getProductsByCategoryId(Long categoryId) {
        List<Product> result = new ArrayList<>();

        for (Product product : products) {
            if (product.getCategory() != null && product.getCategory().getId() == categoryId) {
                result.add(product);
            }
        }
        return result;
    }
    public Product getProductById(Long id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null; // or throw an exception if preferred
    }
}