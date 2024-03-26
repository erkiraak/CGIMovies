package com.erkiraak.movies.controller;

import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.erkiraak.movies.config.WebSecurityConfig;
import com.erkiraak.movies.entity.User;
import com.erkiraak.movies.repository.UserRepository;


@Controller
@Import(WebSecurityConfig.class)
public class ApplicationController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    

    public ApplicationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user, RedirectAttributes redirectAttributes) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            userRepository.save(user);

        return "register_success";
        } else {
            redirectAttributes.addFlashAttribute("error", "Email already exists");
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/register";

        }
        

    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
    

    @GetMapping("/403")
    public String notAuthorized() {
        return "403";
    }
}
