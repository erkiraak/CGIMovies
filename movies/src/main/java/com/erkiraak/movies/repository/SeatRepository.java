package com.erkiraak.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erkiraak.movies.entity.Seat;
import com.erkiraak.movies.entity.Session;


public interface SeatRepository extends JpaRepository<Seat, Integer> {
    
    Seat findBySessionIdAndSeatRowAndSeatColumn(int sessionId, int seatRow, int seatColumn);

}