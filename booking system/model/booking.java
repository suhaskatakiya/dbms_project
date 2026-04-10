package model;

import java.util.ArrayList;

public class booking {
    private ArrayList<seat> bookedSeats;
    private double totalAmount;
    private String ticketType;

    public booking() {
        bookedSeats = new ArrayList<>();
    }

    public void addSeat(seat s) {
        bookedSeats.add(s);
    }

    public void setTotalAmount(double amount) {
        this.totalAmount = amount;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public void showBookingDetails() {
        System.out.println("\n--- BOOKING RECEIPT ---");
        System.out.println("Ticket Class: " + ticketType);
        System.out.print("Seats Booked: ");
        for (seat s : bookedSeats) {
            System.out.print(s.getSeatNumber() + " ");
        }
        System.out.println("\nTotal Amount: ₹" + totalAmount);
        System.out.println("-----------------------\n");
    }
}