package com.example.libraryapp.service;

import com.example.libraryapp.model.Type;

import java.util.List;

public interface TypeService {
    List<Type> getAllTypes();
    void saveType(Type type);
}
