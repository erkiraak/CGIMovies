package com.erkiraak.movies.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.erkiraak.movies.entity.Movie;
import com.erkiraak.movies.entity.Room;
import com.erkiraak.movies.entity.Seat;
import com.erkiraak.movies.entity.Session;
import com.erkiraak.movies.entity.Ticket;
import com.erkiraak.movies.repository.RoomRepository;
import com.erkiraak.movies.repository.TicketRepository;
import com.erkiraak.movies.service.SeatService;
import com.erkiraak.movies.service.SessionService;

@Component
public class SessionInitializer {

    private RoomRepository roomRepository;
    private SessionService sessionService;
    private TicketRepository ticketRepository;
    private SeatService seatService;

    public SessionInitializer(RoomRepository roomRepository, SessionService sessionService,
            TicketRepository ticketRepository, SeatService seatService) {
        this.roomRepository = roomRepository;
        this.sessionService = sessionService;
        this.ticketRepository = ticketRepository;
        this.seatService = seatService;
    }

    @SuppressWarnings("null")
    public void generateSessions(List<Movie> movies, int numberOfDays) throws Exception {

        List<Room> rooms = roomRepository.findAll();
        LocalDateTime startTime = LocalDateTime.now();
        Random random = new Random();

        // Create 5 movie sessions per room per day for n days, staggered by 10 minute
        // intervals
        for (Room room : rooms) {
            LocalDateTime sessionTime = startTime;
            for (int i = 0; i < numberOfDays; i++) {
                if (i == 0) {
                    sessionTime = sessionTime.truncatedTo(ChronoUnit.DAYS).plusDays(i).plusHours(12)
                            .plusMinutes(room.getId() * 10);
                } else {
                    sessionTime = sessionTime.plusMinutes(690);
                }

                for (int j = 0; j < 5; j++) {

                    Movie movie = movies.get(random.nextInt(movies.size()));

                    // Create a new session and save it
                    Session session = new Session();
                    session.setRoom(room);
                    session.setMovie(movie);
                    session.setTime(sessionTime);
                    session.setSeatsAvailable();
                    
                    
                    sessionService.saveSession(session);
                    session.setSeats();
                    seatService.saveAll(session.getSeats());
                    
                    // for (Seat seat : session.getSeats()) {
                    // seatService.save(seat);
                    // }
                    // Reserve a random percentage (between) of seats for the session

                    int rows = room.getRows();
                    int columns = room.getColumns();
                    int numSeatsToReserve = (int) (rows * columns * random.nextInt(20, 75) / 100);
                    for (int k = 0; k < numSeatsToReserve; k++) {
                        int row = random.nextInt(rows);
                        int column = random.nextInt(columns);

                        Ticket ticket = new Ticket();
                        ticket.setSession(session);
                        ticket.setSeat(session.getSeat(row, column));
                        ticket.setCreatedAt(LocalDateTime.now());

                        if (!session.getSeatReservation(row, column)) {

                            try {
                                seatService.setSeatReservation(session.getIntId(), row, column);
                                session.addTicket(ticket);
                                session.setSeatReservation();
                            } catch (IllegalArgumentException e) {
                                System.out.println(e);
                            }
                        }
                    }
                    ticketRepository.saveAll(session.getTickets());

                    sessionService.saveSession(session);

                    sessionTime = sessionTime.plusMinutes(150);
                }
            }
        }
    }
}
 