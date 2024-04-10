package com.erkiraak.movies.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Session {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime time;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Movie movie;

    @OneToMany
    // @OneToMany(cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<Ticket>();
    
    @OneToMany
    // @OneToMany(cascade = CascadeType.ALL)
    private List<Seat> seats;

    private int seatsAvailable;

    
    public void setSeatReservation() {
        seatsAvailable--;
    }

    public boolean getSeatReservation(int row, int column) {
        Seat seat = getSeat(row, column);

        return seat.isReserved();
        }
    

    public List<Seat> getBestAvailableSeats(int n) {
        if (n > seatsAvailable) {
            throw new IllegalArgumentException("Not enough available seats");
        }

        List<Seat> bestSeats = new ArrayList<>();
        int bestSeatsWeight = Integer.MAX_VALUE;

        for (int i = 0; i < room.getColumns(); i++) {
            // get seats in row i
            final int row = i;
            List<Seat> availableSeatsInRow = seats.stream()
                    .filter(seat -> seat.getSeatRow() == row && !seat.isReserved())
                    .collect(Collectors.toList());

            for (int j = 0; j <= availableSeatsInRow.size() - n; j++) {
                // create a sublist with length n
                List<Seat> availableSeats = availableSeatsInRow.subList(j, j + n);
                // check if all seats are sequential
                if (IntStream.range(0, availableSeats.size() - 1)
                        .allMatch(k -> availableSeats.get(k + 1).isSequentialTo(seats.get(k)))) {

                    int weight = availableSeats.stream().mapToInt(seat -> seat.getSeatWeight()).sum();
                    if (weight < bestSeatsWeight) {
                        bestSeatsWeight = weight;
                        bestSeats = availableSeats;
                    }
                }
            }
        }
        
        
        return bestSeats;
    }

    
    
    

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> ticketList) {
        this.tickets = ticketList;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        if (this.tickets.contains(ticket)) {
            this.tickets.remove(ticket);
        }
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public Seat getSeat(int row, int column) {
        Optional<Seat> seat = seats.stream()
                .filter(s -> s.getSeatRow() == row && s.getSeatColumn() == column)
                .findFirst();
        if (seat.isPresent()) {
            return seat.get();
        } else {
            throw new IllegalArgumentException("Seat not found");
        }

    }
    
    public Seat[][] getSeatArray() {
        Seat[][] seatArray = new Seat[room.getRows()][room.getColumns()];
        for (int i = 0; i < room.getRows(); i++) {
            for (int j = 0; j < room.getColumns(); j++) {
                seatArray[i][j] = getSeat(i, j);
            }
        }
        return seatArray;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
        for (Seat seat : seats) {
            seat.setSession(this);
        }
    }

    public void setSeats() {
        this.seats = room.getSeats();
        for (Seat seat : seats) {
            seat.setSession(this);
        }
    }
    public int getNumberOfSeats() {
        return room.getRows() * room.getColumns();
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int numberOfSeatsAvailable) {
        this.seatsAvailable = numberOfSeatsAvailable;
    }

    public void setSeatsAvailable() {
        this.seatsAvailable = room.getRows() * room.getColumns();
    }

    public Long getId() {
        return id;
    }
    public int getIntId() {
        return Math.toIntExact(id);
    }

    public void setId(Long id) {
        this.id = id;
    }

}
