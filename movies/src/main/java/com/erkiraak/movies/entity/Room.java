package com.erkiraak.movies.entity;

import com.erkiraak.movies.util.JsonUtils;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Room {

    @Id
    private int id;
    @Column(name = "number_of_rows")
    private int rows;
    private int seatsPerRow;
    private int bestSeatRow;
    private int bestSeatColumn;

    @Column(columnDefinition = "TEXT")
    private String seatWeightsJson;

    @Transient
    @Nullable
    private int[][] seatWeightsArray;

    public void calculateSeatWeights() {
        // Assign weights based on the distance from the best seat and row
        int[][] seatWeights = new int[this.rows][this.seatsPerRow];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.seatsPerRow; j++) {
                int rowDistance = Math.abs(this.bestSeatRow - i);
                int columnDistance = Math.abs(this.bestSeatColumn - j);
                seatWeights[i][j] = rowDistance + columnDistance;
            }
        }
        this.seatWeightsArray = seatWeights;
        setSeatWeightsJson();
    }

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

    public String getSeatWeightsJson() {
        return seatWeightsJson;
    }

    // save JSON after creating or updating array
    private void setSeatWeightsJson() {
        this.seatWeightsJson = JsonUtils.convertToJson(seatWeightsArray);
        
    }

    public int[][] getSeatWeightsArray() {
        if (seatWeightsArray == null) {
            setSeatWeightsArray();
        }
        return seatWeightsArray;
    }

    // generate array from JSON after deserialization
    private void setSeatWeightsArray() {
            this.seatWeightsArray = JsonUtils.convertFromJson(seatWeightsJson, int[][].class);
    }

}