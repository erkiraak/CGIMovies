package com.erkiraak.movies.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.erkiraak.movies.MoviesApplication;

@Configuration
public class LoggerConfig {

    @Bean
    Logger logger() {
        return LoggerFactory.getLogger(MoviesApplication.class);
    }
}