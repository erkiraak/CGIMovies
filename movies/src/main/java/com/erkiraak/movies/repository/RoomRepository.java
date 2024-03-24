package com.erkiraak.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erkiraak.movies.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

}