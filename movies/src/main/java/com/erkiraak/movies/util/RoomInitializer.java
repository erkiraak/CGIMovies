package com.erkiraak.movies.util;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.erkiraak.movies.entity.Room;
import com.erkiraak.movies.service.RoomService;

@Component
public class RoomInitializer {

    private RoomService roomService;

    public RoomInitializer(RoomService roomService) {
        this.roomService = roomService;
    }




    public void createRooms(int numberOfRooms) {
        Random random = new Random();

        for (int i = 1; i <= numberOfRooms; i++) {
            int rows = random.nextInt(5, 15);
            int seatsPerRow = random.nextInt(8, 20);
            int bestSeatRow = rows / 2;
            int bestSeatColumn = seatsPerRow / 2;
            
            Room room = new Room(
                i,
                rows, 
                seatsPerRow, 
                bestSeatRow, 
                bestSeatColumn
                );
            
            roomService.saveRoom(room);
        }
    }

    
}
