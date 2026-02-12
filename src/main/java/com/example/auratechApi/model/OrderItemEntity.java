package com.example.auratechApi.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class OrderItemEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public OrderItemEntity() {};

    public OrderItemEntity(Integer quantity, OrderEntity order, ProductEntity product, BigDecimal unitPrice) {
        this.quantity = quantity;
        this.order = order;
        this.product = product;
        this.unitPrice = unitPrice;
    }
}
