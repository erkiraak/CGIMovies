package com.erkiraak.movies.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Room {

    @Id
    private int id;
    @Column(name = "number_of_rows")
    private int rows;
    private int columns;
    private int bestRow;
    private int bestColumn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int seatsPerRow) {
        this.columns = seatsPerRow;
    }

    public int getBestRow() {
        return bestRow;
    }

    public void setBestRow(int bestSeatRow) {
        this.bestRow = bestSeatRow;
    }

    public int getBestColumn() {
        return bestColumn;
    }

    public void setBestColumn(int bestSeatColumn) {
        this.bestColumn = bestSeatColumn;
    }

    public List<Seat> getSeats() {
        List<Seat> seats = new ArrayList<Seat>(this.rows * this.columns);

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                int seatWeight = Math.abs(this.bestRow - i) + Math.abs(this.bestColumn - j);
                Seat seat = new Seat();
                seat.setSeatRow(i);
                seat.setSeatColumn(j);
                seat.setSeatWeight(seatWeight);
                seat.setReserved(false);
                seats.add(seat);            
            }
        }
        return seats;
    }


}