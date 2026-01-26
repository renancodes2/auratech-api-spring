package com.example.auratechApi.controllers;

import com.example.auratechApi.dtos.CategoryDTO;
import com.example.auratechApi.mappers.CategoryMapper;
import com.example.auratechApi.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;
    private final CategoryMapper mapper;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CategoryDTO dto) {

        this.categoryService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



}
