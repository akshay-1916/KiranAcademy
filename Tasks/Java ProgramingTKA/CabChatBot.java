
import java.util.Random;
import java.util.Scanner;

public class CabChatBot {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        while (true) {

            System.out.println("\n===== CAB BOOKING CHATBOT =====");
            System.out.println("1. Book Ride");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            String input = sc.nextLine();

            if (!input.equals("1") && !input.equals("2")) {
                System.out.println("Invalid choice! Please select 1 or 2.");
                continue;
            }

            if (input.equals("2")) {
                System.out.println("Thank you for using Cab Booking Chatbot!");
                break;
            }

            // Step 1: Choose cab type
            System.out.println("\nChoose Cab Type:");
            System.out.println("1. Auto  - Rs.12/km");
            System.out.println("2. Sedan - Rs.18/km");
            System.out.println("3. SUV   - Rs.25/km");
            System.out.print("Enter your choice: ");

            String cabChoice = sc.nextLine();
            String cabType;
            int rate;

            switch (cabChoice) {
                case "1":
                    cabType = "Auto";
                    rate = 12;
                    break;
                case "2":
                    cabType = "Sedan";
                    rate = 18;
                    break;
                case "3":
                    cabType = "SUV";
                    rate = 25;
                    break;
                default:
                    System.out.println("Invalid cab choice!");
                    continue;
            }

            // Step 2: Choose route
            System.out.println("\nChoose Route:");
            System.out.println("1. Pune Station to Shivajinagar (5 km)");
            System.out.println("2. Swargate to Kothrud (8 km)");
            System.out.println("3. Hinjawadi to Wakad (10 km)");
            System.out.print("Enter route number: ");

            String routeChoice = sc.nextLine();
            String source;
            String destination;
            int distance;

            switch (routeChoice) {
                case "1":
                    source = "Pune Station";
                    destination = "Shivajinagar";
                    distance = 5;
                    break;
                case "2":
                    source = "Swargate";
                    destination = "Kothrud";
                    distance = 8;
                    break;
                case "3":
                    source = "Hinjawadi";
                    destination = "Wakad";
                    distance = 10;
                    break;
                default:
                    System.out.println("Invalid route choice!");
                    continue;
            }

            // Step 3: Calculate fare
            int fare = distance * rate;

            System.out.println("\n===== RIDE DETAILS =====");
            System.out.println("Cab Type   : " + cabType);
            System.out.println("Source     : " + source);
            System.out.println("Destination: " + destination);
            System.out.println("Distance   : " + distance + " km");
            System.out.println("Rate       : Rs." + rate + "/km");
            System.out.println("Total Fare : Rs." + fare);

            // Step 4: Confirm or cancel before OTP
            System.out.println("\n1. Confirm & Share OTP");
            System.out.println("2. Cancel Ride");
            System.out.print("Enter your choice: ");

            String confirmChoice = sc.nextLine();

            if (confirmChoice.equals("2")) {
                System.out.println("Ride cancelled successfully.");
                System.out.println("No OTP has been generated.");
                continue;
            }

            if (!confirmChoice.equals("1")) {
                System.out.println("Invalid choice! Ride not confirmed.");
                continue;
            }

            // Step 5: Generate OTP only after confirmation
            int otp = 1000 + random.nextInt(9000);

            System.out.println("\n===== BOOKING CONFIRMED =====");
            System.out.println("Your OTP is: " + otp);
            System.out.println("OTP shared successfully.");
            System.out.println("Your ride is confirmed.");
            System.out.println("Cancellation is not allowed after OTP sharing.");
        }

        sc.close();
    }
}
