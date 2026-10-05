package com.example.order_managment.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private double amount;
    private Long customer_id;
}
