package com.erkiraak.movies.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.erkiraak.movies.entity.Movie;
import com.erkiraak.movies.entity.Session;
import com.erkiraak.movies.service.MovieService;
import com.erkiraak.movies.service.SessionService;

@Controller
public class MovieController {
    private final MovieService movieService;
    private final SessionService sessionService;


    public MovieController(MovieService movieService, SessionService sessionService) {
        this.movieService = movieService;
        this.sessionService = sessionService;
    }


    @GetMapping("/movies/{id}")
    public String getAllMovieSessions(@PathVariable int id, Model model) {
        Movie movie = movieService.findById(id);
        List<Session> sessionList = sessionService.getSessionsByMovieId(id);
        model.addAttribute("movie", movie);
        model.addAttribute("sessionList", sessionList);

        return "movies";
    }

    

}
