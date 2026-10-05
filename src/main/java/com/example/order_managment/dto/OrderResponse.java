package com.example.order_managment.dto;
import com.example.order_managment.entity.Customer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponse {
    private Long id;
    private double amount;
    private String status;
    private LocalDateTime createdAt;
    private Long customerId;
}
