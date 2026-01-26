package com.example.auratechApi.services;

import com.example.auratechApi.dtos.CategoryDTO;
import com.example.auratechApi.mappers.CategoryMapper;
import com.example.auratechApi.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper mapper;
    private final CategoryRepository repository;

    public void create(CategoryDTO dto) {
        this.repository.save(mapper.toEntity(dto));
    }

}
