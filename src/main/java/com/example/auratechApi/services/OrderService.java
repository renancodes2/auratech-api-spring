package com.example.auratechApi.services;

import com.example.auratechApi.dtos.OrderItemRequestDTO;
import com.example.auratechApi.dtos.OrderItemResponseDTO;
import com.example.auratechApi.dtos.OrderRequestDTO;
import com.example.auratechApi.dtos.OrderResponseDTO;
import com.example.auratechApi.enums.OrderStatusEnum;
import com.example.auratechApi.exceptions.ResourceNotFoundException;
import com.example.auratechApi.exceptions.UnauthorizedAccessException;
import com.example.auratechApi.mappers.ProductMapper;
import com.example.auratechApi.mappers.UserMapper;
import com.example.auratechApi.models.OrderEntity;
import com.example.auratechApi.models.OrderItemEntity;
import com.example.auratechApi.models.ProductEntity;
import com.example.auratechApi.models.UserEntity;
import com.example.auratechApi.repositories.OrderItemRepository;
import com.example.auratechApi.repositories.OrderRepository;
import com.example.auratechApi.repositories.ProductRepository;
import com.example.auratechApi.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Transactional
    public void createOrder(OrderRequestDTO orderDto, UserEntity userEntity) {

        OrderEntity order = new OrderEntity();
        order.setUser(userEntity);
        order.setOrderStatus(OrderStatusEnum.PROCESSING);

        List<UUID> productIds = orderDto.items().stream()
                .map(o -> UUID.fromString(o.productId()))
                .toList();

        List<ProductEntity> productEntities = productRepository.findAllById(productIds);

        orderDto.items().forEach(o -> {
            ProductEntity product = productEntities.stream()
                    .filter(productEntity -> productEntity.getId().equals(UUID.fromString(o.productId())))
                            .findFirst()
                                    .orElseThrow();

            if (product.getStock() < o.quantity()) {
                throw new IllegalStateException("Insufficient stock for: " + product.getName());
            }

            product.setStock(product.getStock() - o.quantity());

            OrderItemEntity item = new OrderItemEntity(o.quantity(), order, product, product.getPrice());
            order.upsertItem(item);
            order.recalculateTotal();
            productRepository.save(product);
        });

        orderRepository.save(order);
    }


    public List<OrderResponseDTO> findAllOrders() {
        return orderRepository.findAll().stream().map(o -> {
            return new OrderResponseDTO(
                    o.getTotal(),
                    o.getOrderStatus().toString(),
                    userMapper.toDto(o.getUser()),
                    o.getItems().stream().map( i ->
                        new OrderItemResponseDTO(
                                    i.getOrder()
                                            .getId()
                                            .toString(),
                                    productMapper.toDto(i.getProduct()),
                                    i.getQuantity(),
                                    i.getUnitPrice())
            ).toList());
        }).toList();
    }

    @Transactional
    public void addItemToOrder(UUID orderUuid, OrderItemRequestDTO orderDto, UserEntity user) {
        UUID productUuid = UUID.fromString(orderDto.productId());

        ProductEntity product = productRepository.findById(productUuid).orElseThrow(() -> new ResourceNotFoundException("The requested product could not be found"));

        OrderEntity order = orderRepository.findById(orderUuid).orElseThrow(() -> new ResourceNotFoundException("The requested order could not be found"));

        if(!user.getId().equals(order.getUser().getId())) {
            throw new UnauthorizedAccessException("You do not have permission to modify this order");
        }

        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setProduct(product);
        orderItemEntity.setOrder(order);
        orderItemEntity.setUnitPrice(product.getPrice());
        orderItemEntity.setQuantity(orderDto.quantity());;

        if(product.getStock() < orderItemEntity.getQuantity()) {
            throw new IllegalStateException("Insufficient stock for: " + product.getName());
        }

        product.setStock(product.getStock() - orderDto.quantity());

        order.upsertItem(orderItemEntity);
        order.recalculateTotal();

        orderRepository.save(order);

    }

    @Transactional
    public void removeItemFromOrder(UUID orderUuid, UUID itemUuid, UserEntity user) {

        OrderEntity order = orderRepository.findById(orderUuid).orElseThrow(() -> new ResourceNotFoundException("Failed to remove item: not found"));

        if(!user.getId().equals(order.getUser().getId())) {
            throw new UnauthorizedAccessException("You do not have permission to modify this order");
        }

        orderItemRepository.findById(itemUuid).ifPresent(i -> {
            ProductEntity product = i.getProduct();
            product.setStock(product.getStock() + i.getQuantity());
        });

        order.decrementOrRemoveItem(itemUuid);
        order.recalculateTotal();

        orderRepository.save(order);

    }

}
