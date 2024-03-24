package com.erkiraak.movies.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.erkiraak.movies.entity.Movie;
import com.erkiraak.movies.service.MovieService;

@RestController
public class MovieController {
    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping("/movies")
    public ResponseEntity<Iterable<Movie>> getAllMovies() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/movies")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(service.save(movie));
    }
}

// Other endpoints as needed
