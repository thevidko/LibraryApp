package com.example.libraryapp.repository;

import com.example.libraryapp.model.Printout;
import com.example.libraryapp.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
