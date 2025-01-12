package com.example.libraryapp.repository;

import com.example.libraryapp.model.Book;
import com.example.libraryapp.model.Printout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrintoutRepository extends JpaRepository<Printout, Long> {
}
