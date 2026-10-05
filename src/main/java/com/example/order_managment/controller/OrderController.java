package com.example.order_managment.controller;

import com.example.order_managment.dto.OrderRequest;
import com.example.order_managment.dto.OrderResponse;
import com.example.order_managment.service.OrderService;
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
    public String placeOrder(@RequestBody OrderRequest req){
           return orderService.orderPlace(req);
    }

    @GetMapping("/getOrder/{customerId}")
    public List<OrderResponse> getOrderByCustomerId(@PathVariable Long customerId){
        return orderService.fetchOrderByCustomerId(customerId);
    }
}
