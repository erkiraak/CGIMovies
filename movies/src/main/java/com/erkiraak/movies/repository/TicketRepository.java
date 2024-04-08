package com.erkiraak.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erkiraak.movies.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    

}