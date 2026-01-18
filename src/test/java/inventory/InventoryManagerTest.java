package inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple test class for InventoryManager.
 * These tests check that our inventory operations work correctly.
 */
public class InventoryManagerTest {

    private InventoryManager inventory;

    /**
     * Set up a fresh inventory before each test.
     */
    @BeforeEach
    public void setUp() {
        inventory = new InventoryManager();
    }

    /**
     * Test adding a product to inventory.
     */
    @Test
    public void testAddProduct() {
        // Add a book to inventory
        inventory.addProduct("B001", "Test Book", "BOOK", 25.0, 5);

        // Should be able to find the product by name (using legacy method)
        Product product = inventory.findProduct("Test Book");
        assertNotNull(product);
        assertEquals("B001", product.getId());
        assertEquals("Test Book", product.getName());
    }

    /**
     * Test finding a product by name.
     */
    @Test
    public void testFindProduct() {
        // Add a product
        inventory.addProduct("E001", "Test Phone", "ELECTRONICS", 200.0, 3);

        // Find the product
        Product product = inventory.findProduct("Test Phone");
        assertNotNull(product);
        assertEquals("Test Phone", product.getName());
        assertEquals("ELECTRONICS", product.getType());

        // Try to find a product that doesn't exist
        Product notFound = inventory.findProduct("Non-existent Product");
        assertNull(notFound);
    }

    /**
     * Test selling a product.
     */
    @Test
    public void testSellProduct() {
        // Add a product
        inventory.addProduct("B002", "Test Book", "BOOK", 20.0, 10);

        // Sell some items using product ID
        inventory.sellProduct("B002", 3, "NONE");

        // Check remaining stock
        Product product = inventory.findProduct("Test Book");
        assertEquals(7, product.getQuantity()); // 10 - 3 = 7
    }

    /**
     * Test selling more items than available.
     */
    @Test
    public void testSellMoreThanAvailable() {
        // Add a product with limited stock
        inventory.addProduct("B003", "Limited Book", "BOOK", 15.0, 2);

        // Try to sell more than available - should not throw but print error message
        inventory.sellProduct("B003", 5, "NONE");

        // Stock should remain unchanged
        Product product = inventory.findProduct("Limited Book");
        assertEquals(2, product.getQuantity());
    }

    /**
     * Test adding stock to existing product.
     */
    @Test
    public void testAddStock() {
        // Add a product
        inventory.addProduct("E002", "Test Laptop", "ELECTRONICS", 500.0, 3);

        // Add more stock using product ID
        inventory.addStock("E002", 5);

        // Check new quantity
        Product product = inventory.findProduct("Test Laptop");
        assertEquals(8, product.getQuantity()); // 3 + 5 = 8
    }

    /**
     * Test getting low stock products.
     */
    @Test
    public void testGetLowStockProducts() {
        // Add products with different stock levels
        inventory.addProduct("B004", "High Stock Book", "BOOK", 20.0, 10);
        inventory.addProduct("B005", "Low Stock Book", "BOOK", 15.0, 3);
        inventory.addProduct("E003", "Out of Stock", "ELECTRONICS", 100.0, 0);

        // Get low stock products (5 or fewer)
        var lowStockProducts = inventory.getLowStockProducts(5);

        // Should have 2 products (Low Stock Book and Out of Stock)
        assertEquals(2, lowStockProducts.size());
    }

    /**
     * Test inventory statistics.
     */
    @Test
    public void testInventoryValue() {
        // Add some products
        inventory.addProduct("B006", "Book 1", "BOOK", 20.0, 5);      // Value: $100
        inventory.addProduct("E004", "Phone", "ELECTRONICS", 300.0, 2); // Value: $600

        // Calculate total inventory value
        double totalValue = inventory.getInventoryValue();

        // Should be $700 total
        assertEquals(700.0, totalValue, 0.01);
    }

    /**
     * Test selling product with discount.
     */
    @Test
    public void testSellProductWithDiscount() {
        // Add a book
        inventory.addProduct("B007", "Discount Book", "BOOK", 10.0, 20);

        // Sell with student discount
        inventory.sellProduct("B007", 5, "STUDENT");

        // Check remaining stock
        Product product = inventory.findProduct("Discount Book");
        assertEquals(15, product.getQuantity()); // 20 - 5 = 15
    }

    /**
     * Test selling product with bulk discount.
     */
    @Test
    public void testSellProductWithBulkDiscount() {
        // Add a product
        inventory.addProduct("E005", "Bulk Item", "ELECTRONICS", 50.0, 15);

        // Sell with bulk discount (5+ items get 15% off)
        inventory.sellProduct("E005", 6, "BULK");

        // Check remaining stock
        Product product = inventory.findProduct("Bulk Item");
        assertEquals(9, product.getQuantity()); // 15 - 6 = 9
    }

    /**
     * Test viewing inventory.
     */
    @Test
    public void testViewInventory() {
        // Add some products
        inventory.addProduct("B008", "View Book", "BOOK", 25.0, 5);
        inventory.addProduct("E006", "View Phone", "ELECTRONICS", 300.0, 3);

        // Should not throw exception
        assertDoesNotThrow(() -> inventory.viewInventory());
    }

    /**
     * Test adding product with invalid price.
     */
    @Test
    public void testAddProductWithInvalidPrice() {
        // Try to add a book with price below minimum - should print error
        inventory.addProduct("B009", "Cheap Book", "BOOK", 2.0, 5);

        // Product should not be added
        Product product = inventory.findProduct("Cheap Book");
        assertNull(product);
    }

    /**
     * Test low stock products with different thresholds.
     */
    @Test
    public void testGetLowStockProductsWithThreshold() {
        // Add products with different stock levels
        inventory.addProduct("B010", "Stock 2", "BOOK", 10.0, 2);
        inventory.addProduct("B011", "Stock 5", "BOOK", 10.0, 5);
        inventory.addProduct("B012", "Stock 8", "BOOK", 10.0, 8);

        // Get low stock products with threshold of 5
        var lowStock5 = inventory.getLowStockProducts(5);
        assertEquals(2, lowStock5.size()); // Stock 2 and Stock 5

        // Get low stock products with threshold of 10
        var lowStock10 = inventory.getLowStockProducts(10);
        assertEquals(3, lowStock10.size()); // All three
    }
}