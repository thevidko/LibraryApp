package com.example.libraryapp.repository;

import com.example.libraryapp.model.Author;
import com.example.libraryapp.model.Printout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
