package com.erkiraak.movies.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.erkiraak.movies.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micrometer.common.lang.Nullable;
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
    private Long id;
    private LocalDateTime time;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Movie movie;

    @OneToMany
    private List<Ticket> ticketList = new ArrayList<Ticket>();

    @Column(columnDefinition = "TEXT")
    private String seatReservationJson;

    @Nullable
    @JsonIgnore
    @Transient
    private boolean[][] seatReservationArray;

    public Session() {
    }

    public boolean setSeatReservation(int row, int seat) {
        // TODO remove
        if (seatReservationArray == null) {
            setSeatReservationArray();
        }
        if (!seatReservationArray[row][seat]) {
            seatReservationArray[row][seat] = true;
            setSeatReservationJson();
            return true;
        } else {
            return false;
        }
    }

    public boolean getSeatReservation(int row, int seat) {
        // TODO remove
        if (seatReservationArray == null) {
            setSeatReservationArray();
        }
        return seatReservationArray[row][seat];
    }

    public int getNumberOfAvailableSeats() {
        // TODO remove
        if (seatReservationArray == null) {
            setSeatReservationArray();
        }

        int count = 0;
        for (int i = 0; i < room.getRows(); i++) {
            for (int j = 0; j < room.getSeatsPerRow(); j++) {
                if (!seatReservationArray[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getNumberOfSeats() {
        return room.getRows() * room.getSeatsPerRow();
    }

    public void addTicket(Ticket ticket) {
        this.ticketList.add(ticket);
    }

    public Long getId() {
        return id;
    }

    public int getIntId() {
        return Math.toIntExact(id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
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

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public String getSeatReservationJson() {
        return seatReservationJson;
    }

    // save JSON after creating or updating array
    // TODO create DTO
    private void setSeatReservationJson() {
        this.seatReservationJson = JsonUtils.convertToJson(seatReservationArray);
    }

    public boolean[][] getSeatReservationArray() {
        return seatReservationArray;
    }

    // generate array or convert from JSON after deserialization
    // TODO create DTO
    public void setSeatReservationArray() {
        if (seatReservationJson == null) {
            this.seatReservationArray = new boolean[room.getRows()][room.getSeatsPerRow()];
        } else {
            this.seatReservationArray = JsonUtils.convertFromJson(seatReservationJson, boolean[][].class);
        }
    }

}
