// File: src/main/java/inventory/InventoryManager.java
package inventory;

import java.util.ArrayList;
import java.util.List;

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

    // List to store all our products
    private List<Product> products;

    /**
     * Constructor creates a new empty inventory.
     */
    public InventoryManager() {
        products = new ArrayList<>();
    }

    /**
     * Add a new product to inventory using the Factory Pattern.
     *
     * @param type     Type of product ("Book" or "Electronics")
     * @param name     Product name
     * @param price    Product price
     * @param quantity Initial stock quantity
     * @return true if product was added successfully
     */
    public boolean addProduct(String type, String name, double price, int quantity) {
        try {
            // Use Factory Pattern to create the product
            Product product = ProductFactory.createProduct(type, name, price, quantity);

            // Add it to our inventory
            products.add(product);

            System.out.println("Added product: " + product.getName());
            return true;

        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
            return false;
        }
    }

    /**
     * Find a product by name.
     * This searches through all products to find one with the given name.
     *
     * @param name The name of the product to find
     * @return The product if found, null if not found
     */
    public Product findProduct(String name) {
        // Look through all products
        for (Product product : products) {
            // Check if the name matches (ignoring upper/lower case)
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null; // Product not found
    }

    /**
     * Sell a product with discount calculation using Strategy Pattern.
     *
     * @param productName  Name of the product to sell
     * @param quantity     How many to sell
     * @param discountType What type of discount to apply
     * @return true if sale was successful
     */
    public boolean sellProduct(String productName, int quantity, String discountType) {
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
     * Display all products in inventory.
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

        for (Product product : products) {
            if (product.getType().equals(type)) {
                result.add(product);
            }
        }

        return result;
    }

    /**
     * Get products that are low in stock (5 or fewer items).
     *
     * @return List of products with low stock
     */
    public List<Product> getLowStockProducts() {
        List<Product> lowStock = new ArrayList<>();

        for (Product product : products) {
            if (product.getQuantity() <= 5) {
                lowStock.add(product);
            }
        }

        return lowStock;
    }

    /**
     * Add more stock to an existing product.
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
     * Get the total value of all inventory.
     *
     * @return Total value of all products
     */
    public double getTotalInventoryValue() {
        double total = 0.0;

        for (Product product : products) {
            total += product.getPrice() * product.getQuantity();
        }

        return total;
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