package com.example.libraryapp.controller;


import com.example.libraryapp.model.User;
import com.example.libraryapp.service.LoanService;
import com.example.libraryapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/users/")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
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
    public String loans(Model model) {
        model.addAttribute("loans", loanService.getLoans());
        return "loans";
    }
}
