package com.example.auratechApi.dtos;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDTO(
        BigDecimal total,

        String status,

        UserResponseDTO user,

        List<OrderItemResponseDTO> items
) {
}


