<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inventory Management System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        h1, h2 {
            color: #333;
        }
        .container {
            background-color: white;
            border-radius: 5px;
            padding: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .category-filter {
            margin-bottom: 20px;
        }
        .category-filter a {
            margin-right: 10px;
            text-decoration: none;
            padding: 5px 10px;
            border-radius: 3px;
            background-color: #eee;
            color: #333;
        }
        .category-filter a.selected {
            background-color: #4CAF50;
            color: white;
        }
        .view-link {
            color: #2196F3;
            text-decoration: none;
        }
        .view-link:hover {
            text-decoration: underline;
        }
        .product-type {
            font-size: 0.8em;
            color: #777;
            font-style: italic;
        }
        .add-form {
            margin-top: 30px;
            border-top: 1px solid #ddd;
            padding-top: 20px;
        }
        .form-row {
            margin-bottom: 15px;
        }
        label {
            display: inline-block;
            width: 150px;
            font-weight: bold;
        }
        input[type="text"], input[type="number"], select {
            width: 250px;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
        .message {
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .product-type-fields {
            margin-left: 150px;
            padding: 10px;
            background-color: #f9f9f9;
            border-radius: 4px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Inventory Management System</h1>

    <!-- Display messages if any -->
    <c:if test="${not empty successMessage}">
        <div class="message success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="message error">${errorMessage}</div>
    </c:if>

    <!-- Category filter -->
    <div class="category-filter">
        <strong>Filter by Category:</strong>
        <a href="${pageContext.request.contextPath}/inventory"
           class="${empty selectedCategoryId ? 'selected' : ''}">All Products</a>

        <c:forEach var="category" items="${categories}">
            <a href="${pageContext.request.contextPath}/inventory?action=category&id=${category.id}"
               class="${selectedCategoryId == category.id ? 'selected' : ''}">${category.name}</a>
        </c:forEach>
    </div>

    <!-- Product table -->
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Category</th>
            <th>Price</th>
            <th>Type</th>
            <th>Value</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty products}">
                <tr>
                    <td colspan="7">No products found</td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <td>${product.category.name}</td>
                        <td>$${product.price}</td>
                        <td>
                                    <span class="product-type">
                                            ${product['class'].simpleName}
                                    </span>
                        </td>
                        <td>$${product.calculateValue()}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/inventory?action=view&id=${product.id}"
                               class="view-link">View Details</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>

    <!-- Add new product form (for doPost implementation) -->
    <div class="add-form">
        <h2>Add New Product</h2>
        <form action="${pageContext.request.contextPath}/inventory" method="post">
            <div class="form-row">
                <label for="productType">Product Type:</label>
                <select id="productType" name="productType" onchange="toggleFields()">
                    <option value="physical">Physical Product</option>
                    <option value="digital">Digital Product</option>
                </select>
            </div>

            <div class="form-row">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>
            </div>

            <div class="form-row">
                <label for="price">Price:</label>
                <input type="number" id="price" name="price" step="0.01" min="0" required>
            </div>

            <div class="form-row">
                <label for="category">Category:</label>
                <select id="category" name="categoryId" required>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.id}">${category.name}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Physical product specific fields -->
            <div id="physicalFields" class="product-type-fields">
                <div class="form-row">
                    <label for="weight">Weight (kg):</label>
                    <input type="number" id="weight" name="weight" step="0.01" min="0">
                </div>

                <div class="form-row">
                    <label for="quantity">Quantity:</label>
                    <input type="number" id="quantity" name="quantity" min="0">
                </div>
            </div>

            <!-- Digital product specific fields -->
            <div id="digitalFields" class="product-type-fields" style="display: none;">
                <div class="form-row">
                    <label for="fileSize">File Size:</label>
                    <input type="text" id="fileSize" name="fileSize">
                </div>

                <div class="form-row">
                    <label for="downloadCount">Download Count:</label>
                    <input type="number" id="downloadCount" name="downloadCount" min="0">
                </div>
            </div>

            <div class="form-row">
                <button type="submit">Add Product</button>
            </div>
        </form>
    </div>
</div>

<script>
    function toggleFields() {
        var productType = document.getElementById('productType').value;
        var physicalFields = document.getElementById('physicalFields');
        var digitalFields = document.getElementById('digitalFields');

        if (productType === 'physical') {
            physicalFields.style.display = 'block';
            digitalFields.style.display = 'none';
        } else {
            physicalFields.style.display = 'none';
            digitalFields.style.display = 'block';
        }
    }
</script>
</body>
</html>