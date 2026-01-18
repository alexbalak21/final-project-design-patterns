// File: src/main/java/inventory/Product.java
package inventory;

/**
 * Simple Product class to represent items in our inventory.
 * This class keeps track of basic product information like name, price, and quantity.
 *
 * We use this class for both books and electronics - it's simple and flexible.
 */
public class Product {

    // Basic product information
    private String id;          // Unique identifier
    private String name;        // Product name (like "Java Programming Book")
    private String type;        // Product type ("Book" or "Electronics")
    private double price;       // How much it costs
    private int quantity;       // How many we have in stock

    /**
     * Constructor to create a new product.
     * This is like filling out a form with product details.
     *
     * @param id       Unique identifier for the product
     * @param name     What the product is called
     * @param type     What kind of product it is
     * @param price    How much it costs
     * @param quantity How many we have
     * @throws IllegalArgumentException if price or quantity is negative
     */
    public Product(String id, String name, String type, double price, int quantity) {
        // Validate price and quantity
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        
        // Set the product information
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter methods - these let us read the product information

    /**
     * Get the product ID
     * @return the unique identifier of the product
     */
    public String getId() {
        return id;
    }

    /**
     * Get the product name
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Get the product type
     * @return the type (Book or Electronics)
     */
    public String getType() {
        return type;
    }

    /**
     * Get the product price
     * @return how much the product costs
     */
    public double getPrice() {
        return price;
    }

    /**
     * Get how many items we have
     * @return the quantity in stock
     */
    public int getQuantity() {
        return quantity;
    }

    // Setter methods - these let us change the product information

    /**
     * Set the product ID
     * @param id the new unique identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Set the product name
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the product type
     * @param type the new type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Change the product price
     * @param price the new price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Change how many items we have
     * @param quantity the new quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Check if we have this product in stock
     * @return true if quantity is more than 0
     */
    public boolean isInStock() {
        return quantity > 0;
    }

    /**
     * Reduce the quantity when someone buys the product
     * @param amount how many items were sold
     * @return true if we had enough stock, false if not enough
     */
    public boolean sell(int amount) {
        // Check if amount is valid and we have enough items
        if (amount <= 0 || amount > quantity) {
            return false; // Invalid amount or not enough stock
        }

        // Reduce the quantity
        quantity = quantity - amount;
        return true; // Sale successful
    }

    /**
     * Add more items to stock
     * @param amount how many items to add
     */
    public void addStock(int amount) {
        quantity = quantity + amount;
    }

    /**
     * Create a nice string representation of the product
     * This is useful for printing product information
     */
    @Override
    public String toString() {
        return String.format("Product{id='%s', name='%s', type='%s', price=%.2f, quantity=%d}",
                id, name, type, price, quantity);
    }
}