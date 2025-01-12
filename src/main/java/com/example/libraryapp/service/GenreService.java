package com.example.libraryapp.service;

import com.example.libraryapp.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAllGenres();
    void saveGenre(Genre genre);
}
