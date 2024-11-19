package com.example.libraryapp.service;

import com.example.libraryapp.model.Book;
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


}
