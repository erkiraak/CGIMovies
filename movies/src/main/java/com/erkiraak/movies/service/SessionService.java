package com.erkiraak.movies.service;

import org.springframework.stereotype.Service;

import com.erkiraak.movies.entity.Session;
import com.erkiraak.movies.repository.SessionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SessionService {

    private SessionRepository SessionRepository;
    private ObjectMapper objectMapper;

    public SessionService(com.erkiraak.movies.repository.SessionRepository sessionRepository,
            ObjectMapper objectMapper) {
        this.SessionRepository = sessionRepository;
        this.objectMapper = objectMapper;
    }

    public void saveSession(Session Session) {
        try {
            String seatWeightsJson = objectMapper.writeValueAsString(Session.getseatReservationArray());
            Session.setseatReservationJson(seatWeightsJson);

            SessionRepository.save(Session);
        } catch (Exception e) {
            // Handle exception
        }
    }

    public void getSession(Long SessionId) {
        Session Session = SessionRepository.findById(SessionId).orElse(null);
        if (Session != null) {
            String seatReservationJson = Session.getseatReservationJson();
            try {
                boolean[][] seatWeights = objectMapper.readValue(seatReservationJson, new TypeReference<boolean[][]>() {
                });
                Session.setseatReservationArray(seatWeights);

            } catch (Exception e) {
                // Handle exception
            }
        }
    }
}
