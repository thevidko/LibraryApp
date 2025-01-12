package com.example.libraryapp.service;

import com.example.libraryapp.model.Type;
import com.example.libraryapp.repository.TypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class TypeServiceImpl implements TypeService {
    private TypeRepository typeRepository;

    @Override
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    @Override
    public void saveType(Type type) {
        typeRepository.save(type);
    }
}
