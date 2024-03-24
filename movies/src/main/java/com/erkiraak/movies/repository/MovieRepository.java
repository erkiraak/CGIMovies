package com.erkiraak.movies.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erkiraak.movies.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findByoverviewContaining(String string);
    
}