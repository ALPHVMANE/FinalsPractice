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

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        Category c1 = new Category(1, "Gift Card");
        Category c2 = new Category(2, "Music");
        Category c3 = new Category(3, "Movies");
        im.addCategory(c1);
        im.addCategory(c2);
        im.addCategory(c3);

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("products", im.getProducts());
        req.setAttribute("categories", im.getCategories());

        String action = req.getParameter("action");

        if (action != null && action.equals("view")) {
            req.getRequestDispatcher("WEB-INF/product-detail.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("WEB-INF/inventory-list.jsp").forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String productType = req.getParameter("productType");
        String name = req.getParameter("name");
//        String price = req.getParameter("price");
//        String categoryId = req.getParameter("categoryId");
        double price = Double.parseDouble(req.getParameter("price"));
        long categoryId = Integer.parseInt(req.getParameter("categoryId"));

        Category category = null;
        for (Category c : im.getCategories()) {
            if (c.getId() == categoryId) {
                category = c;
                break;
            }
        }

        Product p = null;
        if (productType.equals("physical")) {
            p = new PhysicalProduct(9999, name, price);
        } else if (productType.equals("digital")) {
            p = new DigitalProduct(9999, name, price);
        }
        p.setCategory(category);

        im.addProduct(p);

        resp.sendRedirect("inventory");
    }


}