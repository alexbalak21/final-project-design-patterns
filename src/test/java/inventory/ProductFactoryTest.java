package inventory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple test class for ProductFactory.
 * These tests check that our Factory Pattern works correctly.
 */
public class ProductFactoryTest {

    /**
     * Test creating a valid book product.
     */
    @Test
    public void testCreateValidBook() {
        // Create a book using the factory with valid minimum price
        Product book = ProductFactory.createProduct("B001", "Test Book", "BOOK", 20.0, 5);

        // Check that the book was created correctly
        assertEquals("B001", book.getId());
        assertEquals("Test Book", book.getName());
        assertEquals("BOOK", book.getType());
        assertEquals(20.0, book.getPrice());
        assertEquals(5, book.getQuantity());
    }

    /**
     * Test creating a valid electronics product.
     */
    @Test
    public void testCreateValidElectronics() {
        // Create an electronics item using the factory with valid minimum price
        Product electronics = ProductFactory.createProduct("E001", "Test Laptop", "ELECTRONICS", 500.0, 3);

        // Check that the electronics item was created correctly
        assertEquals("E001", electronics.getId());
        assertEquals("Test Laptop", electronics.getName());
        assertEquals("ELECTRONICS", electronics.getType());
        assertEquals(500.0, electronics.getPrice());
        assertEquals(3, electronics.getQuantity());
    }

    /**
     * Test creating products with the general factory method.
     */
    @Test
    public void testCreateProduct() {
        // Create a book using the general method
        Product book = ProductFactory.createProduct("B002", "General Book", "BOOK", 15.0, 10);
        assertEquals("BOOK", book.getType());
        assertEquals("B002", book.getId());

        // Create electronics using the general method
        Product electronics = ProductFactory.createProduct("E002", "General Phone", "ELECTRONICS", 300.0, 7);
        assertEquals("ELECTRONICS", electronics.getType());
        assertEquals("E002", electronics.getId());
    }

    /**
     * Test that invalid product types throw an exception.
     */
    @Test
    public void testInvalidProductType() {
        // This should throw an exception because "InvalidType" is not supported
        assertThrows(IllegalArgumentException.class, () -> {
            ProductFactory.createProduct("P001", "Test", "INVALID", 10.0, 1);
        });
    }

    /**
     * Test minimum price enforcement for books.
     */
    @Test
    public void testBookMinimumPriceViolation() {
        // This should throw an exception because books must be at least $5.00
        assertThrows(IllegalArgumentException.class, () -> {
            ProductFactory.createProduct("B003", "Cheap Book", "BOOK", 2.0, 1);
        });
    }

    /**
     * Test minimum price enforcement for electronics.
     */
    @Test
    public void testElectronicsMinimumPriceViolation() {
        // This should throw an exception because electronics must be at least $10.00
        assertThrows(IllegalArgumentException.class, () -> {
            ProductFactory.createProduct("E003", "Cheap Electronics", "ELECTRONICS", 5.0, 1);
        });
    }

    /**
     * Test that books at exactly minimum price are accepted.
     */
    @Test
    public void testBookExactMinimumPrice() {
        // Create a book at exactly $5.00 - should work
        Product book = ProductFactory.createProduct("B004", "Min Price Book", "BOOK", 5.0, 1);
        assertEquals(5.0, book.getPrice());
    }

    /**
     * Test that electronics at exactly minimum price are accepted.
     */
    @Test
    public void testElectronicsExactMinimumPrice() {
        // Create electronics at exactly $10.00 - should work
        Product electronics = ProductFactory.createProduct("E004", "Min Price Electronics", "ELECTRONICS", 10.0, 1);
        assertEquals(10.0, electronics.getPrice());
    }
}
