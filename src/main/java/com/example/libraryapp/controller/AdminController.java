package com.example.libraryapp.controller;


import com.example.libraryapp.model.Book;
import com.example.libraryapp.model.Loan;
import com.example.libraryapp.model.Printout;
import com.example.libraryapp.model.User;
import com.example.libraryapp.service.BookService;
import com.example.libraryapp.service.LoanService;
import com.example.libraryapp.service.PrintoutService;
import com.example.libraryapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    private UserService userService;
    private LoanService loanService;
    private BookService bookService;
    private PrintoutService printoutService;
    @Autowired
    public AdminController(UserService userService, LoanService loanService, BookService bookService, PrintoutService printoutService) {
        this.userService = userService;
        this.loanService = loanService;
        this.bookService = bookService;
        this.printoutService = printoutService;
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
        return "admin_books";
    }

    @GetMapping("/books/new/")
    public String newBook(Model model) {
        return "new_book";
    }

    @GetMapping("/books/detail/{id}")
    public String bookDetail(Model model, @PathVariable Integer id) {
        Book book = bookService.getBookById(id);
        if(book != null) {
            model.addAttribute("book", book);
            return "admin_book_detail";
        }
        return "redirect:/books/";
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
        model.addAttribute("loan", new Loan());
        return "new_loan";
    }

    @PostMapping("/loans/new/create")
    public String processLoanForm(
            @RequestParam("userId") Integer userId,
            @RequestParam("printoutId") Integer printoutId,
            @RequestParam("loanDate") String loanDate,
            Model model) {

        // Načtení jednotlivých instancí + formátování datumu
        User user = userService.getUserById(userId);
        Printout printout = printoutService.getPrintoutById(printoutId);
        Date date = Date.valueOf(loanDate);

        // Kontrola nulovosti výtisku
        if (printout == null) {
            model.addAttribute("error", "Výtisk s tímto ID neexistuje!");
            return "new_loan_error";
        }
        //Kontrola dostupnosti výtisku
        if (!printout.isAvailable()){
            model.addAttribute("error", "Výtisk je v systému již vypůjčený!");
            return "new_loan_error";
        }
        // Kontrola platnosti uživatele
        if (user == null) {
            model.addAttribute("error", "Uživatel s tímto ID neexistuje!");
            return "new_loan_error";
        }

        // Přidání informací do modelu
        model.addAttribute("user", user);
        model.addAttribute("printout", printout);
        model.addAttribute("loanDate", date);

        // Přesměrování na stránku potvrzení
        return "new_loan_confirm";
    }
    @PostMapping("/loans/new/confirm/")
    public String confirmLoan(
            @RequestParam("userId") Integer userId,
            @RequestParam("printoutId") Integer printoutId,
            @RequestParam("loanDate") String loanDate) {

        // Načtení jednotlivých instancí + formátování datumu
        User user = userService.getUserById(userId);
        Printout printout = printoutService.getPrintoutById(printoutId);
        Date date = Date.valueOf(loanDate);

        // uložení výpůjčky do databáze
        loanService.createLoan(user,printout,date);
        //Změna statusu výtisku
        printoutService.changePrintoutStatus(printoutId);

        return "redirect:/admin/dashboard/";
    }

    //VRACENÍ VÝPŮJČKY
    @GetMapping("/loans/return/")
    public String returnLoan(Model model) {
        return "return_loan";
    }

    //REDIRECTS
    @GetMapping({"/","/dashboard",""})
    public RedirectView redirectToAdminDashboard() {
        return new RedirectView("/admin/dashboard/");
    }
}
