package main;

import model.*;
import services.*;
import payment.*;
import exception.*;

import java.util.Scanner;
import java.util.ArrayList;

public class mainapp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Create Movies and Shows ONCE before the loop starts.
        movie movie1 = new movie("Kung Fu Panda 4", false);
        movie movie2 = new movie("Dune: Part Two", false);
        movie movie3 = new movie("Deadpool & Wolverine", true); // 18+

        show show1 = new show(movie1, "6:00 PM", 20);
        show show2 = new show(movie2, "6:00 PM", 20);
        show show3 = new show(movie3, "6:00 PM", 20);

        bookingservice service = new bookingservice();

        System.out.println("=========================================");
        System.out.println("  Welcome to the Ticket Booking System!  ");
        System.out.println("=========================================");

        // 2. The INFINITE LOOP allows multiple users to book tickets
        while (true) {

            // --- NEW: Ask to continue in one line ---
            System.out.print("\nWant to continue booking? (yes/no): ");
            String continueChoice = sc.next();

            if (continueChoice.equalsIgnoreCase("no")) {
                System.out.println("\nThank you for using our system. Goodbye!");
                break; // Closes the app
            } else if (!continueChoice.equalsIgnoreCase("yes")) {
                System.out.println("Invalid input. Please type 'yes' or 'no'.");
                continue; // Restarts the loop to ask again
            }

            System.out.println("\n--- Now Showing ---");
            System.out.println("1. " + movie1.getName() + " (U/A)");
            System.out.println("2. " + movie2.getName() + " (U/A)");
            System.out.println("3. " + movie3.getName() + " (18+)");
            System.out.print("Select a movie (1-3): ");

            int movieChoice = sc.nextInt();

            show selectedShow = null;
            if (movieChoice == 1)
                selectedShow = show1;
            else if (movieChoice == 2)
                selectedShow = show2;
            else if (movieChoice == 3)
                selectedShow = show3;
            else {
                System.out.println("\nInvalid selection. Please try again.");
                continue; // Skips the rest of the code and restarts the loop
            }

            movie selectedMovie = selectedShow.getMovie();

            // 3. Ask for age ONLY if the movie is adult-only
            if (selectedMovie.isAdultOnly()) {
                System.out.print("\nThis movie is rated 18+. Please enter your age: ");
                int age = sc.nextInt();
                if (age < 18) {
                    System.out.println(
                            "ACCESS DENIED: You must be 18 or older to watch " + selectedMovie.getName() + ".");
                    continue;
                }
            }

            System.out.println("\nYou selected: " + selectedMovie.getName());

            // 4. Select Ticket Tier
            System.out.println("\n--- Select Ticket Tier ---");
            System.out.println("1. Silver  (₹150)");
            System.out.println("2. Gold    (₹250)");
            System.out.println("3. Premium (₹400)");
            System.out.print("Enter your choice (1-3): ");
            int tierChoice = sc.nextInt();

            double pricePerSeat = 0;
            String ticketType = "";

            switch (tierChoice) {
                case 1:
                    pricePerSeat = 150;
                    ticketType = "Silver";
                    break;
                case 2:
                    pricePerSeat = 250;
                    ticketType = "Gold";
                    break;
                case 3:
                    pricePerSeat = 400;
                    ticketType = "Premium";
                    break;
                default:
                    System.out.println("Invalid tier. Defaulting to Silver.");
                    pricePerSeat = 150;
                    ticketType = "Silver";
            }

            booking currentBooking = new booking();
            currentBooking.setTicketType(ticketType);

            // 5. Visual Seat Map
            System.out.println("\n--- Screen ---");
            ArrayList<seat> seatsList = selectedShow.getSeats();
            for (int i = 0; i < seatsList.size(); i++) {
                if (seatsList.get(i).isBooked()) {
                    System.out.print("[ X ] ");
                } else {
                    System.out.print(String.format("[%02d] ", seatsList.get(i).getSeatNumber()));
                }

                if ((i + 1) % 5 == 0) {
                    System.out.println();
                }
            }

            // 6. Seat Selection & Booking
            try {
                System.out.println("\nEnter number of seats you want to book:");
                int n = sc.nextInt();

                if (n <= 0 || n > 20) {
                    System.out.println("Invalid number of seats. Returning to menu...");
                    continue;
                }

                int[] selectedSeats = new int[n];
                for (int i = 0; i < n; i++) {
                    System.out.print("Enter seat number for passenger " + (i + 1) + ": ");
                    selectedSeats[i] = sc.nextInt();
                }

                // Attempt to book the seats
                service.bookSeats(selectedShow, selectedSeats, currentBooking);

                // Calculate Amount
                double totalAmount = n * pricePerSeat;
                currentBooking.setTotalAmount(totalAmount);

                // Payment Process
                System.out.println("\nTotal amount to pay: ₹" + totalAmount);
                payment paymentMethod = new upipayment();
                paymentMethod.pay(totalAmount);

                // Success
                currentBooking.showBookingDetails();
                System.out.println("Booking Successful! Enjoy your movie.\n");

            } catch (seatnotavailableexception e) {
                System.out.println("\nBOOKING FAILED: " + e.getMessage());
                System.out.println("Transaction cancelled. Returning to main menu...");
            } catch (Exception e) {
                System.out.println("\nAn error occurred. Returning to main menu...");
                sc.nextLine(); // Clears any broken input
            }
        }
        sc.close();
    }
}