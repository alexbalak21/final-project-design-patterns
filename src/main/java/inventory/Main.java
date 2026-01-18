// File: src/main/java/inventory/Main.java
package inventory;

import java.util.Scanner;

/**
 * Main class for the Simple Inventory Management System.
 *
 * This class provides a simple command-line interface where users can:
 * - Add products to inventory
 * - View all products
 * - Sell products with different discounts
 * - View inventory statistics
 *
 * This demonstrates how the Factory and Strategy patterns work together
 * in a real application.
 */
public class Main {

    // Scanner for reading user input
    private static Scanner scanner = new Scanner(System.in);

    // Our inventory manager
    private static InventoryManager inventory = new InventoryManager();

    /**
     * Main method - this is where the program starts.
     */
    public static void main(String[] args) {
        System.out.println("=== WELCOME TO SIMPLE INVENTORY SYSTEM ===");
        System.out.println("This system demonstrates Factory and Strategy patterns");
        System.out.println();

        // Add some sample products to start with
        addSampleProducts();

        // Show the main menu
        boolean running = true;
        while (running) {
            showMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewInventory();
                    break;
                case 3:
                    sellProduct();
                    break;
                case 4:
                    addStock();
                    break;
                case 5:
                    viewStatistics();
                    break;
                case 6:
                    System.out.println("Thank you for using the Inventory System!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    /**
     * Show the main menu options.
     */
    private static void showMenu() {
        System.out.println("=== MAIN MENU ===");
        System.out.println("1. Add Product");
        System.out.println("2. View Inventory");
        System.out.println("3. Sell Product");
        System.out.println("4. Add Stock");
        System.out.println("5. View Statistics");
        System.out.println("6. Exit");
        System.out.println("==================");
    }

    /**
     * Add a new product using the Factory Pattern.
     */
    private static void addProduct() {
        System.out.println("\n=== ADD NEW PRODUCT ===");

        // Show available product types
        String[] types = ProductFactory.getValidTypes();
        System.out.println("Available product types:");
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }

        // Get product type
        int typeChoice = getIntInput("Choose product type (1-" + types.length + "): ");
        if (typeChoice < 1 || typeChoice > types.length) {
            System.out.println("Invalid choice.");
            return;
        }
        String productType = types[typeChoice - 1];

        // Get product details
        String name = getStringInput("Enter product name: ");
        double price = getDoubleInput("Enter price: $");
        int quantity = getIntInput("Enter initial quantity: ");

        // Generate ID for the product
        String id = generateProductId(productType);

        // Add the product using Factory Pattern
        inventory.addProduct(id, name, productType, price, quantity);
        System.out.println("Product added successfully!");

        System.out.println();
    }

    /**
     * View all products in inventory.
     */
    private static void viewInventory() {
        inventory.showInventory();
    }

    /**
     * Sell a product using the Strategy Pattern for discounts.
     */
    private static void sellProduct() {
        System.out.println("\n=== SELL PRODUCT ===");

        // Show current inventory
        inventory.showInventory();

        // Get product to sell
        String productName = getStringInput("Enter product name to sell: ");
        int quantity = getIntInput("Enter quantity to sell: ");

        // Show available discount types
        String[] discountTypes = DiscountCalculator.getAvailableDiscountTypes();
        System.out.println("Available discount types:");
        for (int i = 0; i < discountTypes.length; i++) {
            System.out.println((i + 1) + ". " + discountTypes[i]);
        }

        // Get discount type
        int discountChoice = getIntInput("Choose discount type (1-" + discountTypes.length + "): ");
        if (discountChoice < 1 || discountChoice > discountTypes.length) {
            System.out.println("Invalid choice. Using no discount.");
            discountChoice = 3; // No discount
        }
        String discountType = discountTypes[discountChoice - 1];

        // Process the sale using Strategy Pattern
        inventory.sellProduct(productName, quantity, discountType);
    }

    /**
     * Add stock to an existing product.
     */
    private static void addStock() {
        System.out.println("\n=== ADD STOCK ===");

        // Show current inventory
        inventory.showInventory();

        // Get product and quantity
        String productName = getStringInput("Enter product name: ");
        int quantity = getIntInput("Enter quantity to add: ");

        inventory.addStock(productName, quantity);
        System.out.println();
    }

    /**
     * View inventory statistics.
     */
    private static void viewStatistics() {
        inventory.showStatistics();
    }

    /**
     * Helper method to generate a unique product ID based on type.
     */
    private static int bookCounter = 1;
    private static int electronicsCounter = 1;

    private static String generateProductId(String type) {
        if ("Book".equalsIgnoreCase(type)) {
            return "B" + String.format("%03d", bookCounter++);
        } else if ("Electronics".equalsIgnoreCase(type)) {
            return "E" + String.format("%03d", electronicsCounter++);
        } else {
            return "P" + System.currentTimeMillis();
        }
    }

    /**
     * Add some sample products to demonstrate the system.
     */
    private static void addSampleProducts() {
        System.out.println("Adding sample products...");

        // Add sample books
        inventory.addProduct("B001", "Java Programming", "BOOK", 29.99, 10);
        inventory.addProduct("B002", "Data Structures", "BOOK", 34.99, 8);
        inventory.addProduct("B003", "Web Development", "BOOK", 24.99, 15);

        // Add sample electronics
        inventory.addProduct("E001", "Laptop", "ELECTRONICS", 599.99, 5);
        inventory.addProduct("E002", "Mouse", "ELECTRONICS", 19.99, 20);
        inventory.addProduct("E003", "Keyboard", "ELECTRONICS", 49.99, 12);

        System.out.println("Sample products added!");
        System.out.println();
    }

    /**
     * Helper method to get string input from user.
     */
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Helper method to get integer input from user.
     */
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Helper method to get double input from user.
     */
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}