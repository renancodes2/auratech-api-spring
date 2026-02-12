package com.example.auratechApi.dtos;

import com.example.auratechApi.model.CategoryEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        List<String> imagesUrls,
        String category
        ) {
}
