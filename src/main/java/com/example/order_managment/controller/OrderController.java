package com.example.order_managment.controller;

import com.example.order_managment.dto.OrderRequest;
import com.example.order_managment.dto.OrderResponse;
import com.example.order_managment.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    OrderService orderService;
    public OrderController(OrderService orderService){
        this.orderService=orderService;
    }
    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest req){
           return ResponseEntity.status(HttpStatus.CREATED).body(orderService.orderPlace(req));
    }

    @GetMapping("/getOrder/customerId/{customerId}")
    public List<OrderResponse> getOrderByCustomerId(@PathVariable Long customerId){
        return orderService.fetchOrderByCustomerId(customerId);
    }

    @GetMapping("/getOrder/orderId/{orderId}")
    public OrderResponse getOrderByOrderId(@PathVariable Long orderId){
        return orderService.fetchOrderByOrderId(orderId);

    }
}
