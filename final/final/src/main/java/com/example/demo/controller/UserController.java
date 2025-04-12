package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class UserController {

    @Autowired
    private UserService userService;

    // Show login form
    @GetMapping
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "loan/login";  
    }

    // Handle login logic
    @PostMapping
    public String login(@ModelAttribute("user") User user, Model model) {
        // Remove any extra spaces that might break matching
        user.setUsername(user.getUsername().trim());
        user.setPassword(user.getPassword().trim());

        boolean authenticated = userService.authenticate(user.getUsername(), user.getPassword());

        if (authenticated) {
            return "redirect:/loans";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "loan/login";
        }
    }

}