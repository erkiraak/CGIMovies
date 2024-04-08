package com.erkiraak.movies.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.erkiraak.movies.entity.Movie;
import com.erkiraak.movies.entity.Room;
import com.erkiraak.movies.entity.Session;
import com.erkiraak.movies.entity.Ticket;
import com.erkiraak.movies.repository.RoomRepository;
import com.erkiraak.movies.repository.TicketRepository;
import com.erkiraak.movies.service.SessionService;

@Component
public class SessionInitializer {

    private RoomRepository roomRepository;
    private SessionService sessionService;
    private TicketRepository ticketRepository;

    public SessionInitializer(RoomRepository roomRepository, SessionService sessionService,
            TicketRepository ticketRepository) {
        this.roomRepository = roomRepository;
        this.sessionService = sessionService;
        this.ticketRepository = ticketRepository;
    }

    @SuppressWarnings("null")
    public void generateSessions(List<Movie> movies, int numberOfDays) throws Exception {

        List<Room> rooms = roomRepository.findAll();
        LocalDateTime sessiontime = LocalDateTime.now().minusDays(1);
        Random random = new Random();

        // Create 5 movie sessions per room per day for n days, staggered by 10 minute
        // intervals
        for (Room room : rooms) {
            for (int i = 0; i < numberOfDays; i++) {
                if (i == 0) {
                    sessiontime = sessiontime.truncatedTo(ChronoUnit.DAYS).plusHours(12).plusMinutes(room.getId() * 10);
                } else {
                    sessiontime = sessiontime.plusMinutes(690);
                }

                for (int j = 0; j < 5; j++) {

                    Movie movie = movies.get(random.nextInt(movies.size()));

                    // Create a new session and save it
                    Session session = new Session();
                    session.setRoom(room);
                    session.setMovie(movie);
                    session.setTime(sessiontime);

                    sessionService.saveSession(session);

                    // Reserve a random percentage (between) of seats for the session

                    int numRows = room.getRows();
                    int numSeatsPerRow = room.getSeatsPerRow();
                    int numSeatsToReserve = (int) (numRows * numSeatsPerRow * random.nextInt(20, 75) / 100);
                    for (int k = 0; k < numSeatsToReserve; k++) {
                        int row = random.nextInt(numRows);
                        int seat = random.nextInt(numSeatsPerRow);

                        Ticket ticket = new Ticket();
                        ticket.setSession(session);
                        ticket.setRowNumber(row);
                        ticket.setSeatNumber(seat);
                        ticket.setCreatedAt(LocalDateTime.now());

                        session.setSeatReservation(row, seat);
                        session.addTicket(ticket);
                    }

                    ticketRepository.saveAll(session.getTicketList());

                    sessionService.saveSession(session);

                    sessiontime = sessiontime.plusMinutes(150);
                }
            }
        }
    }
}
