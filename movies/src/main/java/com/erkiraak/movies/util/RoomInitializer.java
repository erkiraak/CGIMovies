package com.erkiraak.movies.util;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.erkiraak.movies.entity.Room;
import com.erkiraak.movies.repository.RoomRepository;

@Component
public class RoomInitializer {

    private RoomRepository roomRepository;

    public RoomInitializer(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void createRooms(int numberOfRooms) {
        Random random = new Random();

        for (int i = 1; i <= numberOfRooms; i++) {
            int rows = random.nextInt(5, 15);
            int seatsPerRow = random.nextInt(8, 20);
            int bestSeatRow = rows / 2;
            int bestSeatColumn = seatsPerRow / 2;

            Room room = new Room();
            room.setId(i);
            room.setRows(rows);
            room.setSeatsPerRow(seatsPerRow);
            room.setBestSeatRow(bestSeatRow);
            room.setBestSeatColumn(bestSeatColumn);
            room.calculateSeatWeights();

            roomRepository.save(room);
        }
    }

}
