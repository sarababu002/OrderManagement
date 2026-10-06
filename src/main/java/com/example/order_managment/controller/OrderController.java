package com.example.order_managment.controller;

import com.example.order_managment.PageResponse;
import com.example.order_managment.dto.OrderRequest;
import com.example.order_managment.dto.OrderResponse;
import com.example.order_managment.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest req, Authentication auth){
           return ResponseEntity.status(HttpStatus.CREATED).body(orderService.orderPlace(req, auth));
    }
    @GetMapping("/getAllOrders")
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<OrderResponse> getAll(@PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
           return orderService.fetchAllOrder(pageable);
    }
    @GetMapping("/getOrder/customerId/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> getOrderByCustomerId(@PathVariable Long customerId){
        return orderService.fetchOrderByCustomerId(customerId);
    }

    @GetMapping("/getOrders")
    public List<OrderResponse> getOrdersByCustomer(Authentication auth){
        return orderService.fetchAllOrderCustomer(auth);
    }

    @GetMapping("/getOrder/orderId/{orderId}")
    public OrderResponse getOrderByOrderId(@PathVariable Long orderId, Authentication auth){
        return orderService.fetchOrderByOrderId(orderId, auth);
    }

    @PutMapping("/{orderId}/pay")
    public OrderResponse payOrder(@PathVariable Long orderId, Authentication auth){
        return orderService.pay(orderId, auth);
    }

    @PutMapping("/{orderId}/cancel")
    public OrderResponse cancelOrder(@PathVariable Long orderId, Authentication auth){
        return orderService.cancel(orderId, auth);
    }


}
