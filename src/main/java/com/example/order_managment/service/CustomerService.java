package com.example.order_managment.service;

import com.example.order_managment.dto.CustomerDto;
import com.example.order_managment.entity.Customer;
import com.example.order_managment.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    CustomerRepository customerRepo;
    public CustomerService(CustomerRepository customerRepo){
        this.customerRepo=customerRepo;
    }
    public String createCustomer(CustomerDto dto){
        Optional<Customer> customer = customerRepo.findByEmail(dto.getEmail());
        if(customer.isPresent()){
            throw new IllegalArgumentException("Email already exist!!!");
        }
        Customer newCustomer = new Customer();
        newCustomer.setName(dto.getName());
        newCustomer.setEmail(dto.getEmail());
        customerRepo.save(newCustomer);
        return "Customer created!!!";
    }
}

