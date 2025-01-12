package com.example.libraryapp.service;

import com.example.libraryapp.model.Book;
import com.example.libraryapp.model.Printout;
import com.example.libraryapp.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Integer id){
        Optional<Book> book = bookRepository.findById(id.longValue());
        return book.orElse(null);
    }
    @Override
    public List<Book> searchBooks(String query, String filter) {
        switch (filter) {
            case "title":
                return bookRepository.findByTitleContainingIgnoreCase(query);
            case "author":
                // Pokud je filtr "author", hledáme podle jména a příjmení autora
                return bookRepository.findByAuthor_NameContainingIgnoreCaseOrAuthor_SurnameContainingIgnoreCase(query, query);
            case "genre":
                return bookRepository.findByGenreNameContainingIgnoreCase(query);
            default:
                return getAllBooks();
        }
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void addPrintout(Integer id) {
        Book book = getBookById(id);

        if (book != null) {
            // Například přidání výtisku
            Printout newPrintout = new Printout(book); // Použití BookService k vytvoření Printout
            book.getPrintouts().add(newPrintout);  // Předpokládáme, že Book má seznam Printoutů

            // Uložení změn na knize, což zahrnuje i nově přidaný Printout
            bookRepository.save(book);
        } else {
            throw new RuntimeException("Kniha nenalezena");
        }
    }


}
