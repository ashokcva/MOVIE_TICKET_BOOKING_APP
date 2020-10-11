package com.example.movie_ticket_booking_app;

public class TicketModel {
    private String moviename,bookingid,showtime,seats;

    public TicketModel() {
    }

    public TicketModel(String moviename, String bookingid, String showtime, String seats) {
        this.moviename = moviename;
        this.bookingid = bookingid;
        this.showtime = showtime;
        this.seats = seats;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getBookingid() {
        return bookingid;
    }

    public void setBookingid(String bookingid) {
        this.bookingid = bookingid;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }
}
