package com.example.auratechApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductRequestDTO(
        @NotBlank(message = "the field name is required")
        String name,

        @NotBlank(message = "The field description is required")
        String description,

        @NotNull(message = "The field price is required")
        BigDecimal price,

        @NotNull(message = "The field stock is required")
        Integer stock,

        @NotNull(message = "The field slug is required")
        String slug,

        @NotNull(message = "The field category is required")
        UUID category,

        @NotEmpty(message = "The field imagesUrl is required")
        List<MultipartFile> imagesUrl
) {
}
