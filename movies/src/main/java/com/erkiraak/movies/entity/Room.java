package com.erkiraak.movies.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Room {

    @Id
    private int roomId;
    @Column(name = "number_of_rows")
    private int rows;
    private int seatsPerRow;
    private int bestSeatRow;
    private int bestSeatColumn;

    @Column(columnDefinition = "TEXT")
    private String seatWeightsJson;

    @Transient
    private int[][] seatWeights;
    
        

    public Room() {
    }

    public Room(int id, int rows, int seatsPerRow, int bestSeatRow, int bestSeatColumn) {
        this.roomId = id;
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
        this.bestSeatRow = bestSeatRow;
        this.bestSeatColumn = bestSeatColumn;
        this.seatWeights = calculateSeatWeights(rows, seatsPerRow, bestSeatRow, bestSeatColumn);
    }

    private int[][] calculateSeatWeights(int rows, int seatsPerRow, int bestSeatRow, int bestSeatColumn) {
        int[][] seatWeights = new int[rows][seatsPerRow];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                // Assign weights based on the distance from the best seat and row
                int rowDistance = Math.abs(bestSeatRow - i);
                int columnDistance = Math.abs(bestSeatColumn - j);
                seatWeights[i][j] = rowDistance + columnDistance;
            }
        }

        return seatWeights;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getSeatWeightsJson() {
        return seatWeightsJson;
    }

    public void setSeatWeightsJson(String seatWeightsJson) {
        this.seatWeightsJson = seatWeightsJson;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    public int getBestSeatRow() {
        return bestSeatRow;
    }

    public void setBestSeatRow(int bestSeatRow) {
        this.bestSeatRow = bestSeatRow;
    }

    public int getBestSeatColumn() {
        return bestSeatColumn;
    }

    public void setBestSeatColumn(int bestSeatColumn) {
        this.bestSeatColumn = bestSeatColumn;
    }

    public int[][] getSeatWeights() {
        return seatWeights;
    }

    public void setSeatWeights(int[][] seatWeights) {
        this.seatWeights = seatWeights;
    }


}