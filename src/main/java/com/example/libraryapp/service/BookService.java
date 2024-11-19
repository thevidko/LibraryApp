package com.example.libraryapp.service;

import com.example.libraryapp.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Integer id);
}
