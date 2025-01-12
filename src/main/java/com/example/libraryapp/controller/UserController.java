package com.example.libraryapp.controller;

import com.example.libraryapp.model.Book;
import com.example.libraryapp.model.User;
import com.example.libraryapp.service.BookService;
import com.example.libraryapp.service.LoanService;
import com.example.libraryapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
public class UserController {
    private BookService bookService;
    private UserService userService;
    private LoanService loanService;

    @Autowired
    public UserController(BookService bookService, UserService userService, LoanService loanService) {
        this.bookService = bookService;
        this.userService = userService;
        this.loanService = loanService;
    }

    @GetMapping("/dashboard/")
    public String dashboard(Model model) {
        // Získání aktuálního uživatele
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);

        // Získání aktuálního dne
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d. MMMM yyyy", Locale.getDefault());

        // Přidání atributů do modelu
        model.addAttribute("user", user);
        model.addAttribute("currentDay", today.format(formatter));
        model.addAttribute("loansActive", loanService.getActiveLoansByUser(user));
        model.addAttribute("loansAll", loanService.getLoansByUser(user));
        return "user_dashboard";
    }

    @GetMapping("/books/")
    public String books(@RequestParam(required = false) String query,
                        @RequestParam(required = false) String filter,
                        Model model) {
        if (query != null && !query.isEmpty()) {
            model.addAttribute("books", bookService.searchBooks(query, filter));
        } else {
            model.addAttribute("books", bookService.getAllBooks());
        }
        model.addAttribute("query", query);  // Uložení hodnoty query
        model.addAttribute("filter", filter); // Uložení hodnoty filter
        return "user_books";
    }

    @GetMapping("/books/detail/{id}")
    public String bookDetail(Model model, @PathVariable Integer id) {
        Book book = bookService.getBookById(id);
        if(book != null) {
            model.addAttribute("book", book);
            return "user_book_detail";
        }
        return "redirect:/books/";
    }

    @GetMapping("/me/")
    public String me(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        if(user != null) {
            model.addAttribute("user", user);
            model.addAttribute("loansAll", loanService.getLoansByUser(user));
            return "me";
        }
        return "redirect:/";
    }

    // Redirects
    @GetMapping({"/","/dashboard"})
    public RedirectView redirectToDashboard() {
        return new RedirectView("/dashboard/");
    }
}
