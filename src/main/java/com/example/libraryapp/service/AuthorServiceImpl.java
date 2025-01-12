package com.example.libraryapp.service;

import com.example.libraryapp.model.Author;
import com.example.libraryapp.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public Author getAuthor(int id) {
        Optional<Author> author = authorRepository.findById((long)id);
        return author.orElse(null);
    }
}
