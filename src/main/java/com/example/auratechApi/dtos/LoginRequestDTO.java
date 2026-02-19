package com.example.auratechApi.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

        @NotBlank(message = "The field email is required")
        String email,
        @NotBlank(message = "The field password is required")
        String password
) {
}
