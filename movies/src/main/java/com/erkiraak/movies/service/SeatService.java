package com.erkiraak.movies.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erkiraak.movies.entity.Seat;
import com.erkiraak.movies.entity.Session;
import com.erkiraak.movies.repository.SeatRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public boolean setSeatReservation(int sessionId, int row, int column) {
        Seat seat = seatRepository.findBySessionIdAndSeatRowAndSeatColumn(sessionId, row, column);

        if (!seat.isReserved()) {
            seat.setReserved(true);
            seatRepository.save(seat);
            return true;
        } else {
            throw new IllegalArgumentException("Seat is already reserved");
        }
    }

    public void saveAll(List<Seat> seats) {
        seatRepository.saveAll(seats);
    }

    public void save(Seat seat) {
        seatRepository.save(seat);
    }
}
