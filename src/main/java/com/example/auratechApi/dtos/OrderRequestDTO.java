package com.example.auratechApi.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequestDTO(

        @NotEmpty(message = "The field items is required")
        List<OrderItemRequestDTO> items
) {

}
