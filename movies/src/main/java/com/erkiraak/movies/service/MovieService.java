package com.erkiraak.movies.service;

import org.springframework.stereotype.Service;

import com.erkiraak.movies.repository.MovieRepository;


import com.erkiraak.movies.entity.Movie;

@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

        public void saveMovie(Movie movie) {
        repository.save(movie);
    }

    public Iterable<Movie> findAll() {
        return repository.findAll();
    }

    public Movie save(Movie movie) {
        return repository.save(movie);
    }

    public Movie findById(int id) {
        return repository.getReferenceById(id);

    }
}