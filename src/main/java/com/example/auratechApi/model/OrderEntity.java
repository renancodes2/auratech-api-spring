package com.example.auratechApi.model;

import com.example.auratechApi.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status")
    private OrderStatusEnum orderStatus;

    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void updateTotals() {
        this.total = this.items.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addItem(OrderItemEntity item) {


        boolean exist = false;

        for(OrderItemEntity i : this.items) {

            if(i.getProduct().getId().equals(item.getProduct().getId())) {
                i.setQuantity(i.getQuantity() + item.getQuantity());
                exist = true;
                break;
            }

        }

        if(!exist) {
            this.items.add(item);
        }

    }

    public void removeItem(UUID itemId) {
        this.items.stream()
                .filter(item -> item.getId() == itemId)
                .findFirst()
                .ifPresent(item -> {
                    if(item.getQuantity() > 1) {
                        item.setQuantity(item.getQuantity() - 1);
                    }else {
                        this.items.remove(item);
                    }
                });
    }
}
