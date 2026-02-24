package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signup() {
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model) {

        // Check if username already taken
        if (!userService.isUsernameAvailable(user.getUsername())) {
            model.addAttribute("error", "Username already exists!");
            return "signup";
        }

        int result = userService.createUser(user);

        if (result > 0) {
            model.addAttribute("success", "Account created! Please login.");
            return "signup";
        } else {
            model.addAttribute("error", "Signup failed, please try again.");
            return "signup";
        }
    }
}