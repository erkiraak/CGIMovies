package com.erkiraak.movies.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erkiraak.movies.entity.Room;
import com.erkiraak.movies.repository.RoomRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ObjectMapper objectMapper; 

    public void saveRoom(Room room) {
        try {
            String seatWeightsJson = objectMapper.writeValueAsString(room.getSeatWeights());
            room.setSeatWeightsJson(seatWeightsJson);

            roomRepository.save(room);
        } catch (Exception e) {
            // Handle exception
        }
    }
    
    public Room getRoom(int roomId) {
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room != null) {
            String seatWeightsJson = room.getSeatWeightsJson();
            try {
                int[][] seatWeights = objectMapper.readValue(seatWeightsJson, new TypeReference<int[][]>() {
                });
                room.setSeatWeights(seatWeights);

            } catch (Exception e) {
                // Handle exception
            }
        }
        return room;
    }

    public List<Room> getAllRooms(){
        List<Room> rooms = roomRepository.findAll();
        for (Room room : rooms) {
            if (room != null) {
                String seatWeightsJson = room.getSeatWeightsJson();
                try {
                    int[][] seatWeights = objectMapper.readValue(seatWeightsJson, new TypeReference<int[][]>() {
                    });
                    room.setSeatWeights(seatWeights);

                } catch (Exception e) {
                    // Handle exception
                }
            }
        }

        return rooms;
    }
}
