package com.example.auratechApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequestDTO(

        @NotBlank(message = "The field productId is required")
        String productId,
        @NotNull(message = "The field quantity is required")
        Integer quantity
) {
}
