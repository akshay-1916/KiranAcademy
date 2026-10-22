
import java.util.Scanner;

public class BankingApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double balance = 10000;

        System.out.println("Welcome to Kiran Bank");

        while (true) {
            System.out.println("\nOpening Balance: " + balance);
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.println("Current Balance: " + balance);

            } else if (choice == 2) {
                System.out.print("Enter deposit amount: ");
                double amount = sc.nextDouble();

                if (amount > 0) {
                    balance += amount;
                    System.out.println("Deposit successful!");
                    System.out.println("Updated Balance: " + balance);
                } else {
                    System.out.println("Invalid amount! Enter an amount greater than 0.");
                }

            } else if (choice == 3) {
                System.out.print("Enter withdrawal amount: ");
                double amount = sc.nextDouble();

                if (amount <= 0) {
                    System.out.println("Invalid amount! Enter an amount greater than 0.");
                } else if (amount > balance) {
                    System.out.println("Transaction failed! Insufficient balance.");
                    System.out.println("Current Balance: " + balance);
                } else {
                    balance -= amount;
                    System.out.println("Withdrawal successful!");
                    System.out.println("Updated Balance: " + balance);
                }

            } else if (choice == 4) {
                System.out.println("Thank you");
                break;

            } else {
                System.out.println("Invalid choice! Please select 1 to 4.");
            }
        }

        sc.close();
    }
}
