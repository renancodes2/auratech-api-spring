package com.example.auratechApi.dtos;

import java.util.List;

public record OrderRequestDTO(
        List<OrderItemRequestDTO> items
) {

}
