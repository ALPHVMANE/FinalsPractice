package com.example.finalspractice.model;

public class PhysicalProduct extends Product implements Categorizable{
    private long id;
    private String name;
    private double price;
    private double weight;
    private int quantity;
    private Category category;

    public PhysicalProduct(long id, String name, double price) {
        super(id, name, price);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public double calculateValue(){
        return price * quantity;
    }
    @Override
    public Category getCategory() {
        return this.category;
    }
    @Override
    public void setCategory(Category category) {
        this.category = category;
    }
    @Override
    public String toString() {
        return "Phys Product " + this.getName() + " " + this.getCategory() + " " + this.getPrice();
    }
}
