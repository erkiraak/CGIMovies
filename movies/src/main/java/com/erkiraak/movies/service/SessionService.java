package com.erkiraak.movies.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.erkiraak.movies.entity.Session;
import com.erkiraak.movies.repository.SessionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SessionService {

    private SessionRepository sessionRepository;

    public SessionService(com.erkiraak.movies.repository.SessionRepository sessionRepository,
            ObjectMapper objectMapper) {
        this.sessionRepository = sessionRepository;
    }


    public Session getSession(@NonNull Long id) {
        Session session = sessionRepository.findById(id).orElse(null);
        return session;
    }

    public void saveSession(@NonNull Session session) {
        sessionRepository.save(session);
    }

    public List<Session> getAllSessions() {
        List<Session> sessions = sessionRepository.findAll();
        return sessions;
    }

    public List<Session> getSessionsForDate(LocalDate date) {

        LocalDateTime startTime = LocalDateTime.of(date, LocalTime.of(0, 0));
        LocalDateTime endTime = LocalDateTime.of(date, LocalTime.of(23, 59));
        if (LocalDate.now().isEqual(date)) {
            startTime = LocalDateTime.now();
        }

        List<Session> sessions = sessionRepository.findByTimeBetweenOrderByTimeAsc(startTime, endTime);
        return sessions;
    }
    
    public List<Session> getSessionsByMovieId(int id) {
        List<Session> sessions = sessionRepository.findByMovieId(id);

        return sessions;
    }
    


}
