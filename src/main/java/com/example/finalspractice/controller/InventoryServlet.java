package com.example.finalspractice.controller;

import java.io.IOException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.finalspractice.model.*;
import java.util.*;

@WebServlet("/inventory")
public class InventoryServlet extends HttpServlet {

    private final InventoryManager im = new InventoryManager();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        Category c1 = new Category(1, "Gift Card");
        Category c2 = new Category(2, "Music");
        Category c3 = new Category(3, "Movies");
        im.addCategory(c1);
        im.addCategory(c2);
        im.addCategory(c3);

        // Sample products (add products as per your needs)
        Product p1 = new PhysicalProduct(1, "Gift Card 1", 25.00);
        p1.setCategory(c1);
        ((PhysicalProduct) p1).setWeight(0.2);
        ((PhysicalProduct) p1).setQuantity(50);
        im.addProduct(p1);
        Product p2 = new DigitalProduct(2, "Digital Music 1", 15.00);
        p2.setCategory(c2);
        ((DigitalProduct) p2).setFileSize("1000");
        ((DigitalProduct) p2).setDownloadCount(200);
        im.addProduct(p2);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) {
            action = "list";  // default action
        }

        // Route to appropriate method based on action
        switch (action) {
            case "view":
                viewItem(req, resp);
                break;
            case "category":
                listByCategory(req, resp);
                break;
            case "list":
            default:
                listInventory(req, resp);
                break;
        }
    }

    private void listInventory(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Get all products and categories
        req.setAttribute("products", im.getProducts());
        req.setAttribute("categories", im.getCategories());

        // Forward to inventory-list.jsp to display
        req.getRequestDispatcher("/WEB-INF/inventory-list.jsp").forward(req, resp);
    }

    private void viewItem(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long productId = Long.parseLong(idParam);
                Product product = im.getProductById(productId);

                if (product != null) {
                    // Set product as request attribute and forward to detail page
                    req.setAttribute("product", product);
                    req.getRequestDispatcher("/WEB-INF/product-detail.jsp").forward(req, resp);
                    return;
                }
            } catch (NumberFormatException e) {
                // Invalid ID, proceed to list inventory
            }
        }

        // If item wasn't found or ID was invalid, redirect to inventory list
        resp.sendRedirect(req.getContextPath() + "/inventory");
    }

    private void listByCategory(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String categoryIdParam = req.getParameter("id");

        if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
            try {
                Long categoryId = Long.parseLong(categoryIdParam);
                req.setAttribute("products", im.getProductsByCategoryId(categoryId));
                req.setAttribute("categories", im.getCategories());
                req.setAttribute("selectedCategoryId", categoryId);

                // Forward to inventory-list.jsp to display filtered products
                req.getRequestDispatcher("/WEB-INF/inventory-list.jsp").forward(req, resp);
                return;
            } catch (NumberFormatException e) {
                // Invalid category ID, continue showing all inventory
            }
        }

        // If category was invalid or not found, redirect to show all products
        resp.sendRedirect(req.getContextPath() + "/inventory");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String productType = req.getParameter("productType");
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        long categoryId = Long.parseLong(req.getParameter("categoryId"));
        double weight = Double.parseDouble(req.getParameter("weight"));
        int qty = Integer.parseInt(req.getParameter("quantity"));
        String fileSize = req.getParameter("fileSize");
        int dCount = Integer.parseInt(req.getParameter("downloadCount"));


        Category category = null;
        for (Category c : im.getCategories()) {
            if (c.getId() == categoryId) {
                category = c;
                break;
            }
        }

        Product p = null;
        if ("physical".equals(productType)) {
            p = new PhysicalProduct(9999, name, price);
            ((PhysicalProduct) p).setWeight(weight);
            ((PhysicalProduct) p).setQuantity(qty);

        } else if ("digital".equals(productType)) {
            p = new DigitalProduct(9999, name, price);
            ((DigitalProduct) p).setFileSize(fileSize);
            ((DigitalProduct) p).setDownloadCount(dCount);
        }

        if (p != null) {
            p.setCategory(category);
            im.addProduct(p);
        }

        // Redirect back to the inventory list
        resp.sendRedirect(req.getContextPath() + "/inventory");
    }
}