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
        boolean result = inventory.addProduct("Book", "Test Book", 25.0, 5);

        // Should return true for successful addition
        assertTrue(result);

        // Should be able to find the product
        Product product = inventory.findProduct("Test Book");
        assertNotNull(product);
        assertEquals("Test Book", product.getName());
    }

    /**
     * Test finding a product by name.
     */
    @Test
    public void testFindProduct() {
        // Add a product
        inventory.addProduct("Electronics", "Test Phone", 200.0, 3);

        // Find the product
        Product product = inventory.findProduct("Test Phone");
        assertNotNull(product);
        assertEquals("Test Phone", product.getName());
        assertEquals("Electronics", product.getType());

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
        inventory.addProduct("Book", "Test Book", 20.0, 10);

        // Sell some items
        boolean result = inventory.sellProduct("Test Book", 3, "None");

        // Sale should be successful
        assertTrue(result);

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
        inventory.addProduct("Book", "Limited Book", 15.0, 2);

        // Try to sell more than available
        boolean result = inventory.sellProduct("Limited Book", 5, "None");

        // Sale should fail
        assertFalse(result);

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
        inventory.addProduct("Electronics", "Test Laptop", 500.0, 3);

        // Add more stock
        boolean result = inventory.addStock("Test Laptop", 5);

        // Should be successful
        assertTrue(result);

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
        inventory.addProduct("Book", "High Stock Book", 20.0, 10);
        inventory.addProduct("Book", "Low Stock Book", 15.0, 3);
        inventory.addProduct("Electronics", "Out of Stock", 100.0, 0);

        // Get low stock products (5 or fewer)
        var lowStockProducts = inventory.getLowStockProducts();

        // Should have 2 products (Low Stock Book and Out of Stock)
        assertEquals(2, lowStockProducts.size());
    }

    /**
     * Test inventory statistics.
     */
    @Test
    public void testInventoryValue() {
        // Add some products
        inventory.addProduct("Book", "Book 1", 20.0, 5);      // Value: $100
        inventory.addProduct("Electronics", "Phone", 300.0, 2); // Value: $600

        // Calculate total inventory value
        double totalValue = inventory.getTotalInventoryValue();

        // Should be $700 total
        assertEquals(700.0, totalValue, 0.01);
    }
}