package com.example.auratechApi.controllers;

import com.example.auratechApi.dtos.OrderItemRequestDTO;
import com.example.auratechApi.dtos.OrderRequestDTO;
import com.example.auratechApi.dtos.OrderResponseDTO;
import com.example.auratechApi.models.UserEntity;
import com.example.auratechApi.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> createOrder(
            @RequestBody @Valid OrderRequestDTO dto,
            @AuthenticationPrincipal UserEntity user
    ) {

        orderService.createOrder(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> findAllOrders(){
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @PostMapping("{id}")
    public ResponseEntity<Void> addItemToOrder(
            @PathVariable("id") UUID orderUuid,
            @RequestBody @Valid OrderItemRequestDTO orderItem,
            @AuthenticationPrincipal UserEntity user
    ) {
        orderService.addItemToOrder(orderUuid, orderItem, user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{orderId}/{itemId}")
    public ResponseEntity<Void> removeItemFromOrder(
            @PathVariable("orderId") UUID orderId,
            @PathVariable("itemId") UUID itemId,
            @AuthenticationPrincipal UserEntity user
    ) {
        orderService.removeItemFromOrder(orderId, itemId, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
