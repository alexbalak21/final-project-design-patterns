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
    private String name;        // Product name (like "Java Programming Book")
    private String type;        // Product type ("Book" or "Electronics")
    private double price;       // How much it costs
    private int quantity;       // How many we have in stock

    /**
     * Constructor to create a new product.
     * This is like filling out a form with product details.
     *
     * @param name     What the product is called
     * @param type     What kind of product it is
     * @param price    How much it costs
     * @param quantity How many we have
     */
    public Product(String name, String type, double price, int quantity) {
        // Set the product information
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter methods - these let us read the product information

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
        // Check if we have enough items
        if (amount > quantity) {
            return false; // Not enough stock
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
        return String.format("%s (%s) - $%.2f [Stock: %d]",
                name, type, price, quantity);
    }
}