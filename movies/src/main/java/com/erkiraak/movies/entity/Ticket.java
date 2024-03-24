package com.erkiraak.movies.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @ManyToOne
    private Session session;

    @ManyToOne
    @Nullable
    private User user;

    @Column(name = "row__number")
    private int rowNumber;
    private int seatNumber;

    
    public Ticket(Session session, int rowNumber, int seatNumber) {
        this.session = session;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        session.addTicket(this);
    }
    
    public Ticket(Session session, User user, int rowNumber, int seatNumber) {
        this.session = session;
        this.user = user;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        session.addTicket(this);
    }
    public Session getSession() {
        return session;
    }
    public void setSession(Session session) {
        this.session = session;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public int getRowNumber() {
        return rowNumber;
    }
    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
    public int getSeatNumber() {
        return seatNumber;
    }
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    
}