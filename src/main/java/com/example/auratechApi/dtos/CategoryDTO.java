package com.example.auratechApi.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(
        @NotBlank(message = "The field name is required")
        String name
) {
}
