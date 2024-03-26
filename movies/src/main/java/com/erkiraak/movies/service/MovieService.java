package com.erkiraak.movies.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erkiraak.movies.entity.Movie;
import com.erkiraak.movies.repository.MovieRepository;

@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

        public void saveMovie(Movie movie) {
        repository.save(movie);
    }

    public List<Movie> findAll() {
        return repository.findAll();
    }

    public Movie save(Movie movie) {
        return repository.save(movie);
    }

    public Movie findById(int id) {
        return repository.getReferenceById(id);

    }
}