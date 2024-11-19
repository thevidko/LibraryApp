package com.example.libraryapp.controller;

import com.example.libraryapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private BookService bookService;
    @Autowired
    public MainController(BookService bookService) {
        this.bookService = bookService;
    }



    /*@GetMapping("/")
    public String index(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "index";
    }*/

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
