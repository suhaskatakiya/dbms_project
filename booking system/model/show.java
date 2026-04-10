package model;

import java.util.ArrayList;

public class show {
    private movie movieObj;
    private String time;
    private ArrayList<seat> seats;

    public show(movie movieObj, String time, int totalSeats) {
        this.movieObj = movieObj;
        this.time = time;
        seats = new ArrayList<>();

        for (int i = 1; i <= totalSeats; i++) {
            seats.add(new seat(i));
        }
    }

    public ArrayList<seat> getSeats() {
        return seats;
    }

    public String getTime() {
        return time;
    }

    public movie getMovie() {
        return movieObj;
    }
}