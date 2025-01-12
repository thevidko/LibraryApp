package com.example.libraryapp.service;

import com.example.libraryapp.model.Publisher;

import java.util.List;

public interface PublisherService {
    List<Publisher> getAllPublishers();
    void savePublisher(Publisher publisher);
}
