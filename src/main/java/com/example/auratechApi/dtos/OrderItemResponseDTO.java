package com.example.auratechApi.dtos;

import com.example.auratechApi.model.OrderEntity;
import com.example.auratechApi.model.ProductEntity;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        String orderId,

        ProductResponseDTO product,

        Integer quantity,

        BigDecimal unitPrice
) {
}