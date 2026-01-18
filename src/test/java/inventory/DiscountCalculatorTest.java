package inventory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple test class for DiscountCalculator.
 * These tests check that our Strategy Pattern works correctly.
 */
public class DiscountCalculatorTest {

    /**
     * Test student discount on books.
     */
    @Test
    public void testStudentDiscountOnBooks() {
        // Create a book
        Product book = new Product("B001", "Test Book", "BOOK", 20.0, 10);

        // Calculate student discount (should be 10% of total)
        DiscountCalculator.DiscountResult result = DiscountCalculator.calculateDiscount(book, 2, "STUDENT");
        double expectedDiscount = (20.0 * 2) * 0.10; // 10% of $40 = $4

        assertEquals(expectedDiscount, result.getDiscountAmount(), 0.01);
        assertTrue(result.getDescription().contains("Student discount"));
    }

    /**
     * Test student discount on electronics (should be zero).
     */
    @Test
    public void testStudentDiscountOnElectronics() {
        // Create an electronics item
        Product electronics = new Product("E001", "Test Laptop", "ELECTRONICS", 500.0, 5);

        // Calculate student discount (should be 0 for electronics)
        DiscountCalculator.DiscountResult result = DiscountCalculator.calculateDiscount(electronics, 1, "STUDENT");

        assertEquals(0.0, result.getDiscountAmount());
        assertTrue(result.getDescription().contains("only applies to books"));
    }

    /**
     * Test bulk discount when buying 5 or more items.
     */
    @Test
    public void testBulkDiscount() {
        // Create a product
        Product product = new Product("P001", "Test Product", "BOOK", 10.0, 20);

        // Calculate bulk discount for 5 items (should be 15% of total)
        DiscountCalculator.DiscountResult result = DiscountCalculator.calculateDiscount(product, 5, "BULK");
        double expectedDiscount = (10.0 * 5) * 0.15; // 15% of $50 = $7.50

        assertEquals(expectedDiscount, result.getDiscountAmount(), 0.01);
        assertTrue(result.getDescription().contains("Bulk discount"));
        assertTrue(result.getDescription().contains("15%"));
    }

    /**
     * Test no bulk discount when buying less than 5 items.
     */
    @Test
    public void testNoBulkDiscountForSmallQuantity() {
        // Create a product
        Product product = new Product("P002", "Test Product", "BOOK", 10.0, 20);

        // Calculate bulk discount for 3 items (should be 0)
        DiscountCalculator.DiscountResult result = DiscountCalculator.calculateDiscount(product, 3, "BULK");

        assertEquals(0.0, result.getDiscountAmount());
        assertTrue(result.getDescription().contains("requires 5+ items"));
    }

    /**
     * Test no discount type.
     */
    @Test
    public void testNoDiscount() {
        // Create a product
        Product product = new Product("P003", "Test Product", "BOOK", 10.0, 20);

        // Calculate no discount
        DiscountCalculator.DiscountResult result = DiscountCalculator.calculateDiscount(product, 5, "NONE");

        assertEquals(0.0, result.getDiscountAmount());
        assertEquals("No discount applied", result.getDescription());
    }

    /**
     * Test case insensitivity of discount types.
     */
    @Test
    public void testDiscountTypeCaseInsensitive() {
        // Create a book
        Product book = new Product("B002", "Test Book", "BOOK", 20.0, 10);

        // Test lowercase
        DiscountCalculator.DiscountResult result1 = DiscountCalculator.calculateDiscount(book, 2, "student");
        assertEquals(4.0, result1.getDiscountAmount(), 0.01);

        // Test mixed case
        DiscountCalculator.DiscountResult result2 = DiscountCalculator.calculateDiscount(book, 2, "StUdEnT");
        assertEquals(4.0, result2.getDiscountAmount(), 0.01);
    }

    /**
     * Test bulk discount with exactly 5 items.
     */
    @Test
    public void testBulkDiscountExactly5Items() {
        // Create a product
        Product product = new Product("P004", "Test Product", "ELECTRONICS", 100.0, 10);

        // Calculate bulk discount for exactly 5 items
        DiscountCalculator.DiscountResult result = DiscountCalculator.calculateDiscount(product, 5, "BULK");
        double expectedDiscount = (100.0 * 5) * 0.15; // 15% of $500 = $75

        assertEquals(expectedDiscount, result.getDiscountAmount(), 0.01);
    }

    /**
     * Test bulk discount with more than 5 items.
     */
    @Test
    public void testBulkDiscountMoreThan5Items() {
        // Create a product
        Product product = new Product("P005", "Test Product", "BOOK", 15.0, 20);

        // Calculate bulk discount for 10 items
        DiscountCalculator.DiscountResult result = DiscountCalculator.calculateDiscount(product, 10, "BULK");
        double expectedDiscount = (15.0 * 10) * 0.15; // 15% of $150 = $22.50

        assertEquals(expectedDiscount, result.getDiscountAmount(), 0.01);
    }
}