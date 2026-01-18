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
        Product book = new Product("Test Book", "Book", 20.0, 10);

        // Calculate student discount (should be 10% of total)
        double discount = DiscountCalculator.calculateDiscount(book, 2, "Student");
        double expectedDiscount = (20.0 * 2) * 0.10; // 10% of $40 = $4

        assertEquals(expectedDiscount, discount, 0.01); // Allow small rounding differences
    }

    /**
     * Test student discount on electronics (should be zero).
     */
    @Test
    public void testStudentDiscountOnElectronics() {
        // Create an electronics item
        Product electronics = new Product("Test Laptop", "Electronics", 500.0, 5);

        // Calculate student discount (should be 0 for electronics)
        double discount = DiscountCalculator.calculateDiscount(electronics, 1, "Student");

        assertEquals(0.0, discount);
    }

    /**
     * Test bulk discount when buying 5 or more items.
     */
    @Test
    public void testBulkDiscount() {
        // Create a product
        Product product = new Product("Test Product", "Book", 10.0, 20);

        // Calculate bulk discount for 5 items (should be 15% of total)
        double discount = DiscountCalculator.calculateDiscount(product, 5, "Bulk");
        double expectedDiscount = (10.0 * 5) * 0.15; // 15% of $50 = $7.50

        assertEquals(expectedDiscount, discount, 0.01);
    }

    /**
     * Test no bulk discount when buying less than 5 items.
     */
    @Test
    public void testNoBulkDiscountForSmallQuantity() {
        // Create a product
        Product product = new Product("Test Product", "Book", 10.0, 20);

        // Calculate bulk discount for 3 items (should be 0)
        double discount = DiscountCalculator.calculateDiscount(product, 3, "Bulk");

        assertEquals(0.0, discount);
    }

    /**
     * Test no discount type.
     */
    @Test
    public void testNoDiscount() {
        // Create a product
        Product product = new Product("Test Product", "Book", 10.0, 20);

        // Calculate no discount
        double discount = DiscountCalculator.calculateDiscount(product, 5, "None");

        assertEquals(0.0, discount);
    }

    /**
     * Test final price calculation.
     */
    @Test
    public void testFinalPriceCalculation() {
        // Create a book
        Product book = new Product("Test Book", "Book", 20.0, 10);

        // Calculate final price with student discount
        double finalPrice = DiscountCalculator.calculateFinalPrice(book, 2, "Student");
        double expectedPrice = 40.0 - 4.0; // $40 - $4 discount = $36

        assertEquals(expectedPrice, finalPrice, 0.01);
    }

    /**
     * Test discount description for student discount.
     */
    @Test
    public void testDiscountDescription() {
        // Create a book
        Product book = new Product("Test Book", "Book", 20.0, 10);

        // Get discount description
        String description = DiscountCalculator.getDiscountDescription(book, 2, "Student");

        // Should mention student discount and amount
        assertTrue(description.contains("Student discount"));
        assertTrue(description.contains("$4.00"));
    }
}