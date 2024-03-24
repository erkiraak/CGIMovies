package com.erkiraak.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erkiraak.movies.entity.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

}