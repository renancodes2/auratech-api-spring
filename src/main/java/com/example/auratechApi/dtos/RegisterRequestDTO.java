package com.example.auratechApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record RegisterRequestDTO(

        @NotBlank(message = "The field name is required")
        String name,

        @NotBlank(message = "The field email is required")
        String email,

        @NotBlank(message = "The field password is required")
        String password,

        @NotEmpty(message = "The field roles is required")
        List<String> roles) {
}
