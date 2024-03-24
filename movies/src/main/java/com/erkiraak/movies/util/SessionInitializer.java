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
import com.erkiraak.movies.repository.TicketRepository;
import com.erkiraak.movies.service.RoomService;
import com.erkiraak.movies.service.SessionService;

@Component
public class SessionInitializer {

    private RoomService roomService;
    private SessionService sessionService;
    private TicketRepository ticketRepository;

    public SessionInitializer(RoomService roomService, SessionService sessionService,
            TicketRepository ticketRepository) {
        this.roomService = roomService;
        this.sessionService = sessionService;
        this.ticketRepository = ticketRepository;
    }

    public void generateSessions(List<Movie> movies, int numberOfDays, int percentOfReservedTickets) throws Exception {

        List<Room> rooms = roomService.getAllRooms();
        LocalDateTime sessiontime = LocalDateTime.now().minusDays(1);
        Random random = new Random();

        // Create 5 movie sessions per room per day for two weeks starting with
        // yesterday
        for (Room room : rooms) {
            for (int i = 0; i < 14; i++) {
                if (i == 0) {
                    sessiontime = sessiontime.truncatedTo(ChronoUnit.DAYS).plusHours(12);
                } else {
                    sessiontime = sessiontime.plusMinutes(690);
                }

                for (int j = 0; j < 5; j++) {

                    Movie movie = movies.get(random.nextInt(movies.size()));

                    // Create a new session
                    Session session = new Session(sessiontime, room, movie);

                    // Reserve x% of seats for the session
                    int numRows = room.getRows();
                    int numSeatsPerRow = room.getSeatsPerRow();
                    int numSeatsToReserve = (int) (numRows * numSeatsPerRow * percentOfReservedTickets / 100);
                    for (int k = 0; k < numSeatsToReserve; k++) {
                        int row = random.nextInt(numRows);
                        int seat = random.nextInt(numSeatsPerRow);

                        Ticket ticket = new Ticket(session, row, seat);
                        session.setSeatReservation(row, seat);
                        session.addTicket(ticket);
                    }

                    sessionService.saveSession(session);
                    ticketRepository.saveAll(session.getTicketList());

                    sessiontime = sessiontime.plusMinutes(150);
                }
            }
        }
    }
}
