package com.example.auratechApi.dtos;

public record OrderItemRequestDTO(
        String productId,
        Integer quantity
) {
}
