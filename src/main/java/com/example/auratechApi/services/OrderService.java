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
    private final ProductMapper mapperProduct;
    private final UserMapper mapperUser;
    private final UserRepository userRepository;

    @Transactional
    public void createOrder(OrderRequestDTO orderDto, UserEntity userEntity) {

        UserEntity user = userRepository
                .findById(userEntity
                        .getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order creation failed: User account not found"));

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setOrderStatus(OrderStatusEnum.PROCESSING);

        orderDto.items().forEach(p -> {
            ProductEntity product = productRepository.findById(UUID.fromString(p.productId())).orElseThrow(() -> new ResourceNotFoundException("Could not add item to order: Product not found"));
            OrderItemEntity item = new OrderItemEntity(p.quantity(), order, product, product.getPrice());
            order.addItem(item);
        });

        orderRepository.save(order);
    }


    public List<OrderResponseDTO> findAllOrders() {
        return orderRepository.findAll().stream().map(p -> {
            return new OrderResponseDTO(
                    p.getTotal(),
                    p.getOrderStatus().toString(),
                    mapperUser.toDto(p.getUser()),
                    p.getItems().stream().map( item ->
                        new OrderItemResponseDTO(
                                    item.getOrder()
                                            .getId()
                                            .toString(),
                                    mapperProduct.toDto(item.getProduct()),
                                    item.getQuantity(),
                                    item.getUnitPrice())
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

        order.addItem(orderItemEntity);

        order.updateTotals();

        orderRepository.save(order);

    }

    public void removeItemFromOrder(UUID orderUuid, UUID itemUuid, UserEntity user) {

        OrderEntity order = orderRepository.findById(orderUuid).orElseThrow(() -> new ResourceNotFoundException("Failed to remove item: not found"));

        if(!user.getId().equals(order.getUser().getId())) {
            throw new UnauthorizedAccessException("You do not have permission to modify this order");
        }

        order.removeItem(itemUuid);
        order.updateTotals();
        orderRepository.save(order);

    }

}
