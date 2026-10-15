import java.util.Scanner;

public class VotingAge {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter age:");
        int age = sc.nextInt();

        if (age < 0) {
            System.out.println("Invalid age");
        } else if (age >= 18) {
            System.out.println("Eligible");
        } else {
            System.out.println("Not eligible");
        }

        sc.close();
    }
}