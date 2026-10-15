
public class ShoppingBill {

    public static double calculateElectronicsGST(double a, double b) {
        return (a + b) * 1.18;
    }

    public static double calculateFashionGST(double c, double d) {
        return (c + d) * 1.13;
    }

    public static double calculateFoodGST(double e) {
        return e * 1.07;
    }

    public static double totalBill(double a, double b, double c,
                                   double d, double e) {
        return calculateElectronicsGST(a, b)
                + calculateFashionGST(c, d)
                + calculateFoodGST(e);
    }

    public static double calculateDiscountedBill(double a, double b,
                                                 double c, double d,
                                                 double e) {
        double total = totalBill(a, b, c, d, e);
        double discount = total * 0.20;
        return total - discount;
    }

    public static void main(String[] args) {
        double a = 100, b = 100, c = 100, d = 100, e = 100;

        double electronics = calculateElectronicsGST(a, b);
        double fashion = calculateFashionGST(c, d);
        double food = calculateFoodGST(e);
        double total = totalBill(a, b, c, d, e);
        double discount = total * 0.20;
        double payable = calculateDiscountedBill(a, b, c, d, e);

        System.out.printf("Electronics after GST: %.2f%n", electronics);
        System.out.printf("Fashion after GST: %.2f%n", fashion);
        System.out.printf("Food after GST: %.2f%n", food);
        System.out.printf("Total after GST: %.2f%n", total);
        System.out.printf("Discount (20%%): %.2f%n", discount);
        System.out.printf("Final payable amount: %.2f%n", payable);
    }
}