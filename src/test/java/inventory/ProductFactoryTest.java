package inventory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple test class for ProductFactory.
 * These tests check that our Factory Pattern works correctly.
 */
public class ProductFactoryTest {

    /**
     * Test creating a book product.
     */
    @Test
    public void testCreateBook() {
        // Create a book using the factory
        Product book = ProductFactory.createBook("Test Book", 20.0, 5);

        // Check that the book was created correctly
        assertEquals("Test Book", book.getName());
        assertEquals("Book", book.getType());
        assertEquals(20.0, book.getPrice());
        assertEquals(5, book.getQuantity());
    }

    /**
     * Test creating an electronics product.
     */
    @Test
    public void testCreateElectronics() {
        // Create an electronics item using the factory
        Product electronics = ProductFactory.createElectronics("Test Laptop", 500.0, 3);

        // Check that the electronics item was created correctly
        assertEquals("Test Laptop", electronics.getName());
        assertEquals("Electronics", electronics.getType());
        assertEquals(500.0, electronics.getPrice());
        assertEquals(3, electronics.getQuantity());
    }

    /**
     * Test creating products with the general factory method.
     */
    @Test
    public void testCreateProduct() {
        // Create a book using the general method
        Product book = ProductFactory.createProduct("Book", "General Book", 15.0, 10);
        assertEquals("Book", book.getType());

        // Create electronics using the general method
        Product electronics = ProductFactory.createProduct("Electronics", "General Phone", 300.0, 7);
        assertEquals("Electronics", electronics.getType());
    }

    /**
     * Test that invalid product types throw an exception.
     */
    @Test
    public void testInvalidProductType() {
        // This should throw an exception because "InvalidType" is not supported
        assertThrows(IllegalArgumentException.class, () -> {
            ProductFactory.createProduct("InvalidType", "Test", 10.0, 1);
        });
    }

    /**
     * Test minimum price enforcement for books.
     */
    @Test
    public void testBookMinimumPrice() {
        // Create a book with very low price
        Product book = ProductFactory.createBook("Cheap Book", 2.0, 1);

        // The factory should enforce minimum price of $5 for books
        assertEquals(5.0, book.getPrice());
    }

    /**
     * Test minimum price enforcement for electronics.
     */
    @Test
    public void testElectronicsMinimumPrice() {
        // Create electronics with very low price
        Product electronics = ProductFactory.createElectronics("Cheap Electronics", 5.0, 1);

        // The factory should enforce minimum price of $10 for electronics
        assertEquals(10.0, electronics.getPrice());
    }
}
