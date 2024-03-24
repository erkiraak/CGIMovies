package com.erkiraak.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erkiraak.movies.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

}