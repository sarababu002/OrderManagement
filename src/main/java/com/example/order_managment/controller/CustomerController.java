package com.example.order_managment.controller;

import com.example.order_managment.dto.CustomerDto;
import com.example.order_managment.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }
    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(dto));
    }
}
