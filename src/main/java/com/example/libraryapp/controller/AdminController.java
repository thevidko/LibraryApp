package com.example.libraryapp.controller;


import com.example.libraryapp.model.Loan;
import com.example.libraryapp.model.User;
import com.example.libraryapp.service.LoanService;
import com.example.libraryapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    private UserService userService;
    private LoanService loanService;
    @Autowired
    public AdminController(UserService userService, LoanService loanService) {
        this.userService = userService;
        this.loanService = loanService;
    }

    @GetMapping("/dashboard/")
    public String dashboard(Model model) {
        // Získání aktuálního dne
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d. MMMM yyyy", Locale.getDefault());

        model.addAttribute("currentDay", today.format(formatter));
        return "admin_dashboard";
    }
    @GetMapping("/users/")
    public String users(@RequestParam(required = false) String query,
                        @RequestParam(required = false) String filter,
                        Model model) {
        if (query != null && !query.isEmpty()) {
            model.addAttribute("users", userService.searchUsers(query, filter));
        } else {
            model.addAttribute("users", userService.getAllUsers());
        }
        model.addAttribute("query", query); // Přidání hodnoty query
        model.addAttribute("filter", filter); // Přidání hodnoty filter
        return "users";
    }

    @GetMapping("/users/detail/{id}")
    public String userDetail(@PathVariable Integer id, Model model) {
        User user = userService.getUserById(id);
        if(user != null) {
            model.addAttribute("user", user);
            return "user_detail";
        }
        return "redirect:/";
    }

    @GetMapping("/loans/")
    public String searchLoans(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String bookTitle,
            @RequestParam(required = false) String copy,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String status,
            Model model) {

        Boolean returned = null;
        if ("returned".equals(status)) {
            returned = true;
        } else if ("not-returned".equals(status)) {
            returned = false;
        }

        List<Loan> loans = loanService.searchLoans(id, bookTitle, copy, email, returned);
        model.addAttribute("loans", loans);
        model.addAttribute("id", id);  // Zapamatování hodnoty id
        model.addAttribute("bookTitle", bookTitle);  // Zapamatování hodnoty bookTitle
        model.addAttribute("copy", copy);  // Zapamatování hodnoty copy
        model.addAttribute("email", email);  // Zapamatování hodnoty email
        model.addAttribute("status", status);  // Zapamatování hodnoty status
        return "loans";
    }

    // NOVÁ VÝPŮJČKA
    @GetMapping("/loans/new/")
    public String newLoan(Model model) {
        return "new_loan";
    }


    //REDIRECTS
    @GetMapping({"/","/dashboard",""})
    public RedirectView redirectToAdminDashboard() {
        return new RedirectView("/admin/dashboard/");
    }
}
