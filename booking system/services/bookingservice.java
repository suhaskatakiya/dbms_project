package services;

import model.*;
import exception.*;
import java.util.ArrayList;

public class bookingservice {

    public void bookSeats(show currentShow, int[] seatNumbers, booking currentBooking) 
            throws seatnotavailableexception {

        ArrayList<seat> seats = currentShow.getSeats();

        for (int num : seatNumbers) {
            if (num < 1 || num > seats.size()) {
                throw new seatnotavailableexception("Invalid seat number: " + num + ". Please select between 1 and " + seats.size());
            }

            seat currentSeat = seats.get(num - 1);

            if (currentSeat.isBooked()) {
                throw new seatnotavailableexception("Seat " + num + " is already booked! Transaction cancelled.");
            }

            currentSeat.bookSeat();
            currentBooking.addSeat(currentSeat);
        }
    }
}