// File: src/main/java/inventory/ProductFactory.java
package inventory;

/**
 * ProductFactory class - demonstrates the Factory Pattern.
 *
 * The Factory Pattern helps us create objects without having to know
 * all the details of how to create them. It's like ordering food at a
 * restaurant - you just say "I want a burger" and the kitchen (factory)
 * knows how to make it.
 *
 * This factory creates two types of products: Books and Electronics.
 */
public class ProductFactory {

    // Constants for product types
    public static final String BOOK_TYPE = "BOOK";
    public static final String ELECTRONICS_TYPE = "ELECTRONICS";

    /**
     * Create a product based on business rules.
     * 
     * @param id       Unique identifier for the product
     * @param name     The product name
     * @param type     The type of product (BOOK or ELECTRONICS)
     * @param price    The product price
     * @param quantity The initial quantity
     * @return A new Product of the specified type
     * @throws IllegalArgumentException if type is invalid or business rules are violated
     */
    public static Product createProduct(String id, String name, String type, double price, int quantity) {
        // Validate product type first
        if (!BOOK_TYPE.equals(type) && !ELECTRONICS_TYPE.equals(type)) {
            throw new IllegalArgumentException("Invalid product type: " + type);
        }
        
        // Apply business rules based on type
        if (BOOK_TYPE.equals(type) && price < 5.0) {
            throw new IllegalArgumentException("Books must have minimum price of $5.00");
        }
        
        if (ELECTRONICS_TYPE.equals(type) && price < 10.0) {
            throw new IllegalArgumentException("Electronics must have minimum price of $10.00");
        }
        
        // Return new Product instance
        return new Product(id, name, type, price, quantity);
    }

    /**
     * Create a Book product.
     * Books have standard pricing and are educational materials.
     *
     * @param name     The title of the book
     * @param price    How much the book costs
     * @param quantity How many books we have
     * @return A new Product representing a book
     */
    public static Product createBook(String name, double price, int quantity) {
        // Create a new product with type "Book"
        Product book = new Product(name, "Book", price, quantity);

        // We could add book-specific logic here if needed
        // For example, books might have a minimum price
        if (price < 5.0) {
            book.setPrice(5.0); // Set minimum book price to $5
        }

        return book;
    }

    /**
     * Create an Electronics product.
     * Electronics are typically more expensive and need special handling.
     *
     * @param name     The name of the electronic item
     * @param price    How much it costs
     * @param quantity How many we have in stock
     * @return A new Product representing an electronic item
     */
    public static Product createElectronics(String name, double price, int quantity) {
        // Create a new product with type "Electronics"
        Product electronics = new Product(name, "Electronics", price, quantity);

        // Electronics might have special business rules
        // For example, electronics might have a minimum price
        if (price < 10.0) {
            electronics.setPrice(10.0); // Set minimum electronics price to $10
        }

        return electronics;
    }

    /**
     * General factory method that creates products based on type (legacy method).
     * This method decides which specific creation method to call.
     *
     * @param type     The type of product ("Book" or "Electronics")
     * @param name     The product name
     * @param price    The product price
     * @param quantity The initial quantity
     * @return A new Product of the specified type
     */
    public static Product createProduct(String type, String name, double price, int quantity) {
        // Check what type of product to create
        if (type.equals("Book")) {
            return createBook(name, price, quantity);
        } else if (type.equals("Electronics")) {
            return createElectronics(name, price, quantity);
        } else {
            // If we don't know the type, throw an error
            throw new IllegalArgumentException("Unknown product type: " + type);
        }
    }

    /**
     * Helper method to check if a product type is valid.
     * This is useful for validating user input.
     *
     * @param type The product type to check
     * @return true if the type is valid, false otherwise
     */
    public static boolean isValidType(String type) {
        return type.equals("Book") || type.equals("Electronics");
    }

    /**
     * Get a list of all valid product types.
     * This is useful for showing options to users.
     *
     * @return An array of valid product types
     */
    public static String[] getValidTypes() {
        return new String[]{"Book", "Electronics"};
    }
}