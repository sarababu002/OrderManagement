package com.example.order_managment.entity;

import com.example.order_managment.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class CustomerOrder {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long order_id;
    private double amount;
    private OrderStatus status=OrderStatus.PLACED;
    private LocalDateTime createdAt= LocalDateTime.now();
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private Customer customer;
}
