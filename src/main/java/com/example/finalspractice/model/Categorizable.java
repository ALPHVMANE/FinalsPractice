package com.example.finalspractice.model;

public interface Categorizable {

    /**
     * Find category
     * @return the course or null if not found
     */
    Category getCategory();

    /**
     * Add category
     * @param category new category
     */
    void setCategory(Category category);

}
