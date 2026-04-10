package payment;

public class upipayment implements payment {
    public void pay(double amount) {
        System.out.println("Processing UPI Payment...");
        System.out.println("Paid ₹" + amount + " successfully using UPI.");
    }
}