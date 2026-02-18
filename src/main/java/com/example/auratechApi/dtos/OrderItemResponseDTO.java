package com.example.auratechApi.dtos;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        String orderId,

        ProductResponseDTO product,

        Integer quantity,

        BigDecimal unitPrice
) {
}