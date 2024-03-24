package com.erkiraak.movies.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String dateOfBirth;
    private boolean isAdult;

    @ManyToMany
    private List<Movie> watchedMovies;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;

    @ManyToMany
    private List<Genre> preferredGenres;

    

}
