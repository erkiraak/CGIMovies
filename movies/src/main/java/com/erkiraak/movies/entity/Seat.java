package com.erkiraak.movies.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Session session;

    private int seatRow;
    private int seatColumn;
    private int seatWeight;
    private boolean reserved;


    public boolean isSequentialTo(Seat otherSeat) {
        return this.getSeatRow() == otherSeat.getSeatRow() && this.getSeatColumn() == otherSeat.getSeatColumn() + 1;
    }
    
    public int getSeatRow() {
        return seatRow;
    }
    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }
    public int getSeatColumn() {
        return seatColumn;
    }
    public void setSeatColumn(int seatColumn) {
        this.seatColumn = seatColumn;
    }
    public int getSeatWeight() {
        return seatWeight;
    }
    public void setSeatWeight(int seatWeight) {
        this.seatWeight = seatWeight;
    }
    public boolean isReserved() {
        return reserved;
    }
    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    
    
}