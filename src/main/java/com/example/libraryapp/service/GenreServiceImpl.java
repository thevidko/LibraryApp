package com.example.libraryapp.service;

import com.example.libraryapp.model.Genre;
import com.example.libraryapp.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Genre getGenreById(int id) {
        Optional<Genre> genre = genreRepository.findById((long) id);
        return genre.orElse(null);
    }
}
