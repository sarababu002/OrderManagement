package com.example.order_managment.controller;

import com.example.order_managment.dto.OrderRequest;
import com.example.order_managment.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
