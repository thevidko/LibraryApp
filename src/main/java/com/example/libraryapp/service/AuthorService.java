package com.example.libraryapp.service;

import com.example.libraryapp.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthors();
    void saveAuthor(Author author);
    Author getAuthor(int id);
}
