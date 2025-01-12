package com.example.libraryapp.service;

import com.example.libraryapp.model.Genre;
import com.example.libraryapp.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private GenreRepository genreRepository;

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public void saveGenre(Genre genre) {
        genreRepository.save(genre);
    }
}
