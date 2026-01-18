// File: src/main/java/inventory/InventoryManager.java
package inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * InventoryManager class manages all products in our inventory.
 *
 * This class is like a warehouse manager - it keeps track of all our products,
 * handles sales, and helps us find products when we need them.
 *
 * It uses both the Factory Pattern (to create products) and Strategy Pattern
 * (to calculate discounts during sales).
 */
public class InventoryManager {

    // Map to store all our products by ID
    private Map<String, Product> products;

    /**
     * Constructor creates a new empty inventory.
     */
    public InventoryManager() {
        this.products = new HashMap<>();
    }

    /**
     * Add a new product to inventory using the Factory Pattern.
     *
     * @param id       Unique product ID
     * @param name     Product name
     * @param type     Type of product ("BOOK" or "ELECTRONICS")
     * @param price    Product price
     * @param quantity Initial stock quantity
     */
    public void addProduct(String id, String name, String type, double price, int quantity) {
        try {
            // Use Factory Pattern to create the product
            Product product = ProductFactory.createProduct(id, name, type, price, quantity);
            
            // Add it to our inventory
            products.put(id, product);
            
            System.out.println("Product added successfully: " + product);
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    /**
     * Sell a product with discount calculation using Strategy Pattern.
     *
     * @param id           Product ID
     * @param quantity     How many to sell
     * @param discountType What type of discount to apply
     */
    public void sellProduct(String id, int quantity, String discountType) {
        // Find the product
        Product product = products.get(id);
        if (product == null) {
            System.out.println("Product not found: " + id);
            return;
        }

        // Check if we have enough stock
        if (!product.isInStock() || product.getQuantity() < quantity) {
            System.out.println("Insufficient stock for product: " + id);
            return;
        }

        // Calculate discount using Strategy pattern
        DiscountCalculator.DiscountResult discount = 
            DiscountCalculator.calculateDiscount(product, quantity, discountType);
        
        // Calculate total and apply discount
        double totalPrice = product.getPrice() * quantity;
        double finalPrice = totalPrice - discount.getDiscountAmount();
        
        // Update inventory
        product.sell(quantity);
        
        // Display sale information
        System.out.printf("Sale completed:%n");
        System.out.printf("Product: %s%n", product.getName());
        System.out.printf("Quantity: %d%n", quantity);
        System.out.printf("Original Price: $%.2f%n", totalPrice);
        System.out.printf("Discount: $%.2f (%s)%n", discount.getDiscountAmount(), discount.getDescription());
        System.out.printf("Final Price: $%.2f%n", finalPrice);
    }

    /**
     * Add more stock to an existing product.
     *
     * @param id       Product ID
     * @param quantity How many items to add
     */
    public void addStock(String id, int quantity) {
        Product product = products.get(id);
        if (product == null) {
            System.out.println("Product not found: " + id);
            return;
        }

        product.addStock(quantity);
        System.out.println("Added " + quantity + " items to " + product.getName() +
                ". New stock: " + product.getQuantity());
    }

    /**
     * Display all products in inventory.
     */
    public void viewInventory() {
        System.out.println("\n=== INVENTORY LIST ===");

        if (products.isEmpty()) {
            System.out.println("No products in inventory.");
        } else {
            for (Product product : products.values()) {
                System.out.println(product);
            }
        }

        System.out.println("======================\n");
    }

    /**
     * Get the total value of all inventory.
     *
     * @return Total value of all products
     */
    public double getInventoryValue() {
        double total = 0.0;

        for (Product product : products.values()) {
            total += product.getPrice() * product.getQuantity();
        }

        return total;
    }

    /**
     * Get products with stock below a specified threshold.
     *
     * @param threshold The stock level threshold
     * @return List of products with low stock
     */
    public List<Product> getLowStockProducts(int threshold) {
        List<Product> lowStock = new ArrayList<>();

        for (Product product : products.values()) {
            if (product.getQuantity() <= threshold) {
                lowStock.add(product);
            }
        }

        return lowStock;
    }

    /**
     * Find a product by name (legacy method).
     * This searches through all products to find one with the given name.
     *
     * @param name The name of the product to find
     * @return The product if found, null if not found
     */
    public Product findProduct(String name) {
        // Look through all products
        for (Product product : products.values()) {
            // Check if the name matches (ignoring upper/lower case)
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null; // Product not found
    }

    /**
     * Sell a product with discount calculation using Strategy Pattern (legacy method).
     *
     * @param productName  Name of the product to sell
     * @param quantity     How many to sell
     * @param discountType What type of discount to apply
     * @return true if sale was successful
     */
    public boolean sellProduct(String productName, int quantity, String discountType, boolean legacy) {
        // Find the product
        Product product = findProduct(productName);
        if (product == null) {
            System.out.println("Product not found: " + productName);
            return false;
        }

        // Check if we have enough stock
        if (!product.isInStock() || product.getQuantity() < quantity) {
            System.out.println("Not enough stock. Available: " + product.getQuantity());
            return false;
        }

        // Calculate prices using Strategy Pattern
        double originalPrice = product.getPrice() * quantity;
        double discount = DiscountCalculator.calculateDiscount(product, quantity, discountType);
        double finalPrice = originalPrice - discount;

        // Process the sale
        product.sell(quantity);

        // Show sale summary
        System.out.println("\n=== SALE COMPLETE ===");
        System.out.println("Product: " + product.getName());
        System.out.println("Quantity: " + quantity);
        System.out.println("Unit Price: $" + String.format("%.2f", product.getPrice()));
        System.out.println("Original Total: $" + String.format("%.2f", originalPrice));
        System.out.println(DiscountCalculator.getDiscountDescription(product, quantity, discountType));
        System.out.println("Final Price: $" + String.format("%.2f", finalPrice));
        System.out.println("Remaining Stock: " + product.getQuantity());
        System.out.println("====================\n");

        return true;
    }

    /**
     * Display all products in inventory (legacy method).
     */
    public void showInventory() {
        System.out.println("\n=== INVENTORY LIST ===");

        if (products.isEmpty()) {
            System.out.println("No products in inventory.");
        } else {
            for (int i = 0; i < products.size(); i++) {
                System.out.println((i + 1) + ". " + products.get(i));
            }
        }

        System.out.println("======================\n");
    }

    /**
     * Get all products of a specific type.
     *
     * @param type The product type ("Book" or "Electronics")
     * @return List of products of that type
     */
    public List<Product> getProductsByType(String type) {
        List<Product> result = new ArrayList<>();

        for (Product product : products.values()) {
            if (product.getType().equals(type)) {
                result.add(product);
            }
        }

        return result;
    }

    /**
     * Get products that are low in stock (5 or fewer items) - legacy method.
     *
     * @return List of products with low stock
     */
    public List<Product> getLowStockProducts() {
        return getLowStockProducts(5);
    }

    /**
     * Add more stock to an existing product (legacy method).
     *
     * @param productName Name of the product
     * @param quantity    How many items to add
     * @return true if stock was added successfully
     */
    public boolean addStock(String productName, int quantity) {
        Product product = findProduct(productName);
        if (product == null) {
            System.out.println("Product not found: " + productName);
            return false;
        }

        product.addStock(quantity);
        System.out.println("Added " + quantity + " items to " + productName +
                ". New stock: " + product.getQuantity());
        return true;
    }

    /**
     * Get the total number of products in inventory.
     *
     * @return Number of different products
     */
    public int getProductCount() {
        return products.size();
    }

    /**
     * Get the total value of all inventory (legacy method).
     *
     * @return Total value of all products
     */
    public double getTotalInventoryValue() {
        return getInventoryValue();
    }

    /**
     * Show inventory statistics.
     */
    public void showStatistics() {
        System.out.println("\n=== INVENTORY STATISTICS ===");
        System.out.println("Total Products: " + getProductCount());
        System.out.println("Total Inventory Value: $" + String.format("%.2f", getTotalInventoryValue()));

        List<Product> lowStock = getLowStockProducts();
        System.out.println("Low Stock Items: " + lowStock.size());

        if (!lowStock.isEmpty()) {
            System.out.println("Items needing restock:");
            for (Product product : lowStock) {
                System.out.println("  - " + product.getName() + " (Stock: " + product.getQuantity() + ")");
            }
        }

        System.out.println("============================\n");
    }
}