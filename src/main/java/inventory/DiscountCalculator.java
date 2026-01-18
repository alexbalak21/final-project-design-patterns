// File: src/main/java/inventory/DiscountCalculator.java
package inventory;

/**
 * DiscountCalculator class - demonstrates the Strategy Pattern.
 *
 * The Strategy Pattern lets us choose different ways to calculate discounts
 * depending on the situation. It's like having different coupons - you can
 * choose which one gives you the best deal.
 *
 * This class can calculate discounts in different ways:
 * 1. Student discount (10% off books)
 * 2. Bulk discount (15% off when buying 5 or more items)
 * 3. No discount
 */
public class DiscountCalculator {

    // Different types of discounts we can apply
    public static final String STUDENT_DISCOUNT = "Student";
    public static final String BULK_DISCOUNT = "Bulk";
    public static final String NO_DISCOUNT = "None";

    /**
     * Calculate discount based on the discount type.
     * This is the main method that chooses which discount strategy to use.
     *
     * @param product      The product we're buying
     * @param quantity     How many items we're buying
     * @param discountType What type of discount to apply
     * @return The discount amount (money to subtract from total)
     */
    public static double calculateDiscount(Product product, int quantity, String discountType) {
        // Choose which discount method to use based on the type
        switch (discountType) {
            case STUDENT_DISCOUNT:
                return calculateStudentDiscount(product, quantity);
            case BULK_DISCOUNT:
                return calculateBulkDiscount(product, quantity);
            case NO_DISCOUNT:
                return 0.0; // No discount
            default:
                return 0.0; // Unknown discount type, so no discount
        }
    }

    /**
     * Calculate student discount.
     * Students get 10% off books, but no discount on electronics.
     *
     * @param product  The product being purchased
     * @param quantity How many items
     * @return The discount amount
     */
    private static double calculateStudentDiscount(Product product, int quantity) {
        // Check if the product is a book
        if (product.getType().equals("Book")) {
            // Calculate 10% discount
            double totalPrice = product.getPrice() * quantity;
            return totalPrice * 0.10; // 10% discount
        } else {
            // No discount on electronics for students
            return 0.0;
        }
    }

    /**
     * Calculate bulk discount.
     * Get 15% off when buying 5 or more of any item.
     *
     * @param product  The product being purchased
     * @param quantity How many items
     * @return The discount amount
     */
    private static double calculateBulkDiscount(Product product, int quantity) {
        // Check if buying 5 or more items
        if (quantity >= 5) {
            // Calculate 15% discount on total price
            double totalPrice = product.getPrice() * quantity;
            return totalPrice * 0.15; // 15% discount
        } else {
            // No bulk discount for less than 5 items
            return 0.0;
        }
    }

    /**
     * Calculate the final price after discount.
     * This is a convenience method that does the math for us.
     *
     * @param product      The product being purchased
     * @param quantity     How many items
     * @param discountType What type of discount to apply
     * @return The final price after discount
     */
    public static double calculateFinalPrice(Product product, int quantity, String discountType) {
        // Calculate original total price
        double originalPrice = product.getPrice() * quantity;

        // Calculate discount amount
        double discount = calculateDiscount(product, quantity, discountType);

        // Return final price (original price minus discount)
        return originalPrice - discount;
    }

    /**
     * Get a description of what discount is being applied.
     * This helps users understand what discount they're getting.
     *
     * @param product      The product being purchased
     * @param quantity     How many items
     * @param discountType What type of discount to apply
     * @return A description of the discount
     */
    public static String getDiscountDescription(Product product, int quantity, String discountType) {
        double discount = calculateDiscount(product, quantity, discountType);

        if (discount > 0) {
            switch (discountType) {
                case STUDENT_DISCOUNT:
                    return "Student discount (10% off books): $" + String.format("%.2f", discount);
                case BULK_DISCOUNT:
                    return "Bulk discount (15% off 5+ items): $" + String.format("%.2f", discount);
                default:
                    return "Discount applied: $" + String.format("%.2f", discount);
            }
        } else {
            return "No discount applied";
        }
    }

    /**
     * Check if a discount type is valid.
     *
     * @param discountType The discount type to check
     * @return true if valid, false otherwise
     */
    public static boolean isValidDiscountType(String discountType) {
        return discountType.equals(STUDENT_DISCOUNT) ||
                discountType.equals(BULK_DISCOUNT) ||
                discountType.equals(NO_DISCOUNT);
    }

    /**
     * Get all available discount types.
     *
     * @return An array of available discount types
     */
    public static String[] getAvailableDiscountTypes() {
        return new String[]{STUDENT_DISCOUNT, BULK_DISCOUNT, NO_DISCOUNT};
    }
}