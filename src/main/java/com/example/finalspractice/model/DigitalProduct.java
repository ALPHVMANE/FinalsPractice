package com.example.finalspractice.model;

public class DigitalProduct extends Product implements Categorizable {
    private long id;
    private String name;
    private double price;
    private String fileSize;
    private int downloadCount;
    private Category category;

    public DigitalProduct(long id, String name, double price) {
        super(id,name,price);
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public double calculateValue(){
        return this.price * (1 + this.downloadCount * 0.01);
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
        return "Digi Product " + this.getName() + " " + this.getCategory() + " " + this.getPrice();
    }
}
