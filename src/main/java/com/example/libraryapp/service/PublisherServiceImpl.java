package com.example.libraryapp.service;

import com.example.libraryapp.model.Publisher;
import com.example.libraryapp.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PublisherServiceImpl implements PublisherService {
    private PublisherRepository publisherRepository;

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public void savePublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    @Override
    public Publisher getPublisher(int id) {
        Optional<Publisher> publisher = publisherRepository.findById((long)id);
        return publisher.orElse(null);
    }
}
