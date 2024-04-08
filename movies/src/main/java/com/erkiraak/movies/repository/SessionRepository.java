package com.erkiraak.movies.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erkiraak.movies.entity.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

    
    List<Session> findByTimeBetweenOrderByTimeAsc(LocalDateTime startTime, LocalDateTime endTime);

    List<Session> findByMovieId(int id);

    @SuppressWarnings("null")
    List<Session> findAll();
}