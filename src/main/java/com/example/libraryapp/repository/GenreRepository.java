package com.example.libraryapp.repository;

import com.example.libraryapp.model.Genre;
import com.example.libraryapp.model.Printout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
