
import java.util.*;

class Flight {
    String code, source, destination;
    double baseFare;
    int availableSeats;

    Flight(String code, String source, String destination,
           double baseFare, int availableSeats) {
        this.code = code;
        this.source = source;
        this.destination = destination;
        this.baseFare = baseFare;
        this.availableSeats = availableSeats;
    }

    void display() {
        System.out.println(code + " | " + source + " to " + destination
                + " | Fare: Rs." + baseFare
                + " | Seats: " + availableSeats);
    }
}

class Passenger {
    private String name;

    Passenger(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    double getDiscount() {
        return 0.0;
    }

    String getType() {
        return "Regular";
    }
}

class StudentPassenger extends Passenger {
    StudentPassenger(String name) {
        super(name);
    }

    @Override
    double getDiscount() {
        return 0.10;
    }

    @Override
    String getType() {
        return "Student";
    }
}

class Booking {
    private String bookingId;
    private Flight flight;
    private Passenger passenger;
    private int tickets;
    private double totalFare;

    Booking(String bookingId, Flight flight,
            Passenger passenger, int tickets) {
        this.bookingId = bookingId;
        this.flight = flight;
        this.passenger = passenger;
        this.tickets = tickets;

        double subtotal = flight.baseFare * tickets;
        totalFare = subtotal - (subtotal * passenger.getDiscount());
    }

    void displaySummary() {
        System.out.println("\nBooking confirmed!");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Flight: " + flight.code + " from "
                + flight.source + " to " + flight.destination);
        System.out.println("Passenger: " + passenger.getName());
        System.out.println("Passenger Type: " + passenger.getType());
        System.out.println("Tickets: " + tickets);
        System.out.printf("Base fare per ticket: Rs.%.2f%n",
                flight.baseFare);
        System.out.printf("Subtotal: Rs.%.2f%n",
                flight.baseFare * tickets);
        System.out.printf("Discount: %.0f%%%n",
                passenger.getDiscount() * 100);
        System.out.printf("Final amount: Rs.%.2f%n", totalFare);
    }
}

public class FlightBookingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Flight[] flights = {
                new Flight("PN101", "Pune", "Delhi", 4000, 20),
                new Flight("MB102", "Mumbai", "Bengaluru", 3500, 15),
                new Flight("PN103", "Pune", "Hyderabad", 2500, 10)
        };

        ArrayList<Booking> bookings = new ArrayList<>();
        int bookingCounter = 1001;

        while (true) {
            System.out.println("\nWelcome to Kiran Air Booking");
            System.out.println("1. View Flights");
            System.out.println("2. Book Flight");
            System.out.println("3. View Booking");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input!");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                for (Flight f : flights) {
                    f.display();
                }

            } else if (choice == 2) {
                for (Flight f : flights) {
                    f.display();
                }

                System.out.print("Enter flight code: ");
                String code = sc.nextLine().trim();

                Flight selected = null;
                for (Flight f : flights) {
                    if (f.code.equalsIgnoreCase(code)) {
                        selected = f;
                        break;
                    }
                }

                if (selected == null) {
                    System.out.println("Invalid flight code.");
                    continue;
                }

                System.out.print("Enter passenger name: ");
                String name = sc.nextLine().trim();

                if (name.isEmpty()) {
                    System.out.println("Passenger name cannot be blank.");
                    continue;
                }

                System.out.print("Passenger type (Regular/Student): ");
                String type = sc.nextLine().trim();

                Passenger passenger;
                if (type.equalsIgnoreCase("Regular")) {
                    passenger = new Passenger(name);
                } else if (type.equalsIgnoreCase("Student")) {
                    passenger = new StudentPassenger(name);
                } else {
                    System.out.println("Invalid passenger type.");
                    continue;
                }

                System.out.print("Enter number of tickets: ");
                if (!sc.hasNextInt()) {
                    System.out.println("Invalid ticket count.");
                    sc.nextLine();
                    continue;
                }

                int tickets = sc.nextInt();
                sc.nextLine();

                if (tickets <= 0) {
                    System.out.println("Tickets must be greater than 0.");
                    continue;
                }

                if (tickets > selected.availableSeats) {
                    System.out.println("Not enough seats available.");
                    System.out.println("No booking was created.");
                    continue;
                }

                Booking booking = new Booking(
                        "BK" + bookingCounter++,
                        selected, passenger, tickets);

                selected.availableSeats -= tickets;
                bookings.add(booking);
                booking.displaySummary();

            } else if (choice == 3) {
                if (bookings.isEmpty()) {
                    System.out.println("No bookings found.");
                } else {
                    for (Booking booking : bookings) {
                        booking.displaySummary();
                    }
                }

            } else if (choice == 4) {
                System.out.println("Thank you for using Kiran Air Booking!");
                break;

            } else {
                System.out.println("Invalid choice! Select 1 to 4.");
            }
        }

        sc.close();
    }
}
