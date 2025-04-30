package com.example.finalspractice.model;

public abstract class Product implements Categorizable{
    private long id;
    private String name;
    private double price;
    private Category category;

    public Product(long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public Category getCategory() { return category; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(Category category) { this.category = category; }

    public abstract double calculateValue();
}
