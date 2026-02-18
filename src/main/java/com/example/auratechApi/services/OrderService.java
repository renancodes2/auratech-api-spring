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
import com.example.auratechApi.model.OrderEntity;
import com.example.auratechApi.model.OrderItemEntity;
import com.example.auratechApi.model.ProductEntity;
import com.example.auratechApi.model.UserEntity;
import com.example.auratechApi.repositories.OrderItemRepository;
import com.example.auratechApi.repositories.OrderRepository;
import com.example.auratechApi.repositories.ProductRepository;
import com.example.auratechApi.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

        orderDto.items().forEach(o -> {
            ProductEntity product = productRepository.findById(UUID.fromString(o.productId())).orElseThrow(() -> new ResourceNotFoundException("Could not add item to order: Product not found"));
            OrderItemEntity item = new OrderItemEntity(o.quantity(), order, product, product.getPrice());
            order.upsertItem(item);
            order.recalculateTotal();
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
        orderItemEntity.setQuantity(orderDto.quantity());

        order.upsertItem(orderItemEntity);
        order.recalculateTotal();

        orderRepository.save(order);

    }

    public void removeItemFromOrder(UUID orderUuid, UUID itemUuid, UserEntity user) {

        OrderEntity order = orderRepository.findById(orderUuid).orElseThrow(() -> new ResourceNotFoundException("Failed to remove item: not found"));

        if(!user.getId().equals(order.getUser().getId())) {
            throw new UnauthorizedAccessException("You do not have permission to modify this order");
        }

        order.decrementOrRemoveItem(itemUuid);
        order.recalculateTotal();

        orderRepository.save(order);

    }

}
