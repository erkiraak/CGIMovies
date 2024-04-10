package com.erkiraak.movies.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.erkiraak.movies.entity.Session;
import com.erkiraak.movies.service.SessionService;

@Controller
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/")
    public String index(Model model) {
        LocalDate today = LocalDate.now();
        List<Session> sessionList = sessionService.getSessionsForDate(today);
        model.addAttribute("sessionList", sessionList);
        model.addAttribute("date", LocalDate.now());
        return "index";
    }

    @GetMapping("/sessionfragment")
    public String getSessionsForDate(@RequestParam("date") String dateString, Model model) {
        // date in format "2024-03-31"
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Session> sessionList = sessionService.getSessionsForDate(date);
        model.addAttribute("sessionList", sessionList);
        return "fragments/session :: session-table";
    }
   
    @GetMapping("/sessions/{id}")
    public String selectSeats(@PathVariable long id, Model model) {
        Session session = sessionService.getSession(id);
        model.addAttribute("sessionObject", session);

        return "select_seat";
    }

    @PostMapping("/sessions/update")
    public String updateSession(@ModelAttribute("sessionObject") Session session, Model model) {
        if (session != null) {
            sessionService.saveSession(session);
            model.addAttribute("sessionObject", session);
            model.addAttribute("success", true);
        }
        model.addAttribute("sessionObject", session);
        model.addAttribute("success", false);
        return "update_session";
    }
}
