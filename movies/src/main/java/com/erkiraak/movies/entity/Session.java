package com.erkiraak.movies.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    private LocalDateTime time;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Movie movie;

    @OneToMany(mappedBy = "session")
    private List<Ticket> ticketList;

    

    @Column(columnDefinition = "TEXT")
    private String seatReservationJson;

    @Transient
    private boolean[][] seatReservationArray;

    public Session(LocalDateTime time, Room room, Movie movie) {
        this.time = time;
        this.room = room;
        this.movie = movie;
        this.ticketList = new ArrayList<>();
        this.seatReservationArray = new boolean[room.getRows()][room.getSeatsPerRow()];
    }

    public LocalDateTime getTime() {
        return time;
    }

    protected void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> tickets) {
        this.ticketList = tickets;
    }

    public void addTicket(Ticket ticket) {
        this.ticketList.add(ticket);
    }

    public String getseatReservationJson() {
        return seatReservationJson;
    }

    public void setseatReservationJson(String seatReservationJson) {
        this.seatReservationJson = seatReservationJson;
    }

    public boolean[][] getseatReservationArray() {
        return seatReservationArray;
    }

    public void setseatReservationArray(boolean[][] seatReservationArray) {
        this.seatReservationArray = seatReservationArray;
    }

    public boolean setSeatReservation(int row, int seat) {
        if (!seatReservationArray[row][seat]) {
            seatReservationArray[row][seat] = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean getSeatReservationStatus(int row, int seat) {
        return seatReservationArray[row][seat];
    }

}
