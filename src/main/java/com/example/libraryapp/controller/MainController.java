package com.example.libraryapp.controller;

import com.example.libraryapp.model.User;
import com.example.libraryapp.service.BookService;
import com.example.libraryapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public MainController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(User user) {
        String email = user.getUsername();
        User userCheck = userService.findByUsername(email);
        if (userCheck != null) {
            return "redirect:/register?error";
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword())); // Hashování hesla
            user.setRole("USER");
            user.setEnabled(false); // Uživatel není aktivní
            userService.save(user);
            return "redirect:/register?success";
        }
    }
}
