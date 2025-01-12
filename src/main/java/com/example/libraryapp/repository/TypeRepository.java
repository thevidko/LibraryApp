package com.example.libraryapp.repository;

import com.example.libraryapp.model.Printout;
import com.example.libraryapp.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Long> {
}
