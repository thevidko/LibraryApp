package com.example.libraryapp.controller;


import com.example.libraryapp.model.*;
import com.example.libraryapp.service.*;
import com.example.libraryapp.utils.TextNormalizer;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import javax.swing.text.Document;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    private UserService userService;
    private LoanService loanService;
    private BookService bookService;
    private PrintoutService printoutService;
    private AuthorService authorService;
    private GenreService genreService;
    private PublisherService publisherService;
    private TypeService typeService;

    @Autowired
    public AdminController(UserService userService, LoanService loanService, BookService bookService, PrintoutService printoutService, AuthorService authorService, GenreService genreService, PublisherService publisherService, TypeService typeService) {
        this.userService = userService;
        this.loanService = loanService;
        this.bookService = bookService;
        this.printoutService = printoutService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.publisherService = publisherService;
        this.typeService = typeService;
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
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("genres", genreService.getAllGenres());
        model.addAttribute("publishers", publisherService.getAllPublishers());
        model.addAttribute("types", typeService.getAllTypes());
        return "new_book";
    }
    @PostMapping("/books/new/create/")
    public String createNewBook(@ModelAttribute Book book,
                                @RequestParam(required = false) String newAuthorName,
                                @RequestParam(required = false) String newAuthorSurname,
                                @RequestParam(required = false) String newGenreName,
                                @RequestParam(required = false) String newTypeName,
                                @RequestParam(required = false) String newPublisherName,
                                @RequestParam(required = false) Integer author,
                                @RequestParam(required = false) Integer type,
                                @RequestParam(required = false) Integer publisher,
                                @RequestParam(required = false) Integer genre) {

        if (author != null){
            book.setAuthor(authorService.getAuthor(author));
        }
        if (!newAuthorName.isEmpty() && !newAuthorSurname.isEmpty()) {
            Author newAuthor = new Author(null, newAuthorName, newAuthorSurname);
            authorService.saveAuthor(newAuthor);
            book.setAuthor(newAuthor);
        }

        if (genre != null){
            book.setGenre(genreService.getGenreById(genre));
        }
        if (!newGenreName.isEmpty()) {
            Genre newGenre = new Genre(null, newGenreName);
            genreService.saveGenre(newGenre);
            book.setGenre(newGenre);
        }

        if (type != null){
            book.setType(typeService.getTypeById(type));
        }
        if (!newTypeName.isEmpty()) {
            Type newType = new Type(null, newTypeName);
            typeService.saveType(newType);
            book.setType(newType);
        }

        if (publisher != null){
            book.setPublisher(publisherService.getPublisher(publisher));
        }
        if (!newPublisherName.isEmpty()) {
            Publisher newPublisher = new Publisher(null, newPublisherName);
            publisherService.savePublisher(newPublisher);
            book.setPublisher(newPublisher);
        }
        System.out.println("Book created: " + book);
        bookService.saveBook(book);
        return "redirect:/admin/dashboard/";
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
            return "loan_error";
        }
        //Kontrola dostupnosti výtisku
        if (!printout.isAvailable()){
            model.addAttribute("error", "Výtisk je v systému již vypůjčený!");
            return "loan_error";
        }
        // Kontrola platnosti uživatele
        if (user == null) {
            model.addAttribute("error", "Uživatel s tímto ID neexistuje!");
            return "loan_error";
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
    @PostMapping("/loans/return/")
    public String returnLoanForm(
            @RequestParam("printoutId") Integer printoutId,
            @RequestParam("returnDate") String returnDate,
            Model model) {

        // Načtení jednotlivých instancí + formátování datumu
        Printout printout = printoutService.getPrintoutById(printoutId);
        Date date = Date.valueOf(returnDate);

        // Kontrola nulovosti výtisku
        if (printout == null) {
            model.addAttribute("error", "Výtisk s tímto ID neexistuje!");
            return "loan_error";
        }
        //Kontrola dostupnosti výtisku
        if (printout.isAvailable()){
            model.addAttribute("error", "Výtisk v systému není vypůjčený!");
            return "loan_error";
        }
        // Přidání informací do modelu
        model.addAttribute("user", loanService.getUserByPrintoutId(printoutId));
        model.addAttribute("printout", printout);
        model.addAttribute("returnDate", date);

        // Přesměrování na stránku potvrzení
        return "return_loan_confirm";
    }
    @PostMapping("/loans/return/confirm/")
    public String confirmReturnLoan(
            @RequestParam("printoutId") Integer printoutId,
            @RequestParam("returnDate") String returnDate) {

        //formátování datumu
        Date date = Date.valueOf(returnDate);

        // Nalezení loan + update
        loanService.updateLoanByPrintoutId(printoutId,date,false);
        //Změna statusu výtisku
        printoutService.changePrintoutStatus(printoutId);

        return "redirect:/admin/dashboard/";
    }

    //New printout
    @PostMapping("/books/{id}/new_printout/")
    public String addPrintout(@PathVariable("id") Integer id) {
        // Zavolání služby pro přidání výtisku
        bookService.addPrintout(id);

        // Přesměrování zpět na detail knihy
        return "redirect:/admin/books/detail/" + id;
    }

    //DELETE PRINTOUT
    @PostMapping("/books/delete_printout/{id}")
    public String deletePrintout(@PathVariable("id") Integer id) {
        Printout printout = printoutService.getPrintoutById(id);
        if (printout != null && printout.isAvailable()){
            String back = printout.getBook().getId().toString();
            printoutService.deletePrintoutById(id);
            return "redirect:/admin/books/detail/" + back;
        }
        return "redirect:/admin/books/";
    }

    //REDIRECTS
    @GetMapping({"/","/dashboard",""})
    public RedirectView redirectToAdminDashboard() {
        return new RedirectView("/admin/dashboard/");
    }

    //PDF
    @GetMapping("/print-label/{id}")
    public void generateLabel(@PathVariable("id") Integer printoutId, HttpServletResponse response) {
        Printout printout = printoutService.getPrintoutById(printoutId);
        String bookTitle = printout.getBook().getTitle();
        String printoutDetails = "Vytisk ID: " + printoutId;

        // Nastavení odpovědi jako PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=label-" + printoutId + ".pdf");

        try (PDDocument document = new PDDocument()) {
            // Vytvoření nové stránky
            PDPage page = new PDPage();
            document.addPage(page);

            // Přidání obsahu na stránku
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD), 15);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Knihovna ZS Skola");
                contentStream.endText();

                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD), 15);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 720);
                contentStream.showText("Nazev knihy: " + TextNormalizer.removeDiacritics(bookTitle));
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText(printoutDetails);
                contentStream.endText();
            }

            // Zapsání PDF do odpovědi
            document.save(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
