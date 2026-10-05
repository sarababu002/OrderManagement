package com.example.order_managment.service;

import com.example.order_managment.dto.OrderRequest;
import com.example.order_managment.dto.OrderResponse;
import com.example.order_managment.entity.Customer;
import com.example.order_managment.entity.CustomerOrder;
import com.example.order_managment.exception.CustomerNotFoundException;
import com.example.order_managment.exception.OrderNotFoundException;
import com.example.order_managment.repository.CustomerOrderRepository;
import com.example.order_managment.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    CustomerOrderRepository orderRepo;
    CustomerRepository customerRepo;
    public OrderService(CustomerOrderRepository orderRepo, CustomerRepository customerRepo){
        this.orderRepo=orderRepo;
        this.customerRepo=customerRepo;

    }

    public OrderResponse orderPlace(OrderRequest req){
        Customer customer = customerRepo.findById(req.getCustomer_id())
                .orElseThrow(()->new CustomerNotFoundException("Invalid Customer Id"));
        CustomerOrder order = new CustomerOrder();
        order.setAmount(req.getAmount());
        order.setCustomer(customer);
        orderRepo.save(order);
        return toResponse(order);
    }

    public List<OrderResponse> fetchOrderByCustomerId(Long customerId){
         return orderRepo.findByCustomerId(customerId)
                .stream().map(this::toResponse).toList();
    }

    public OrderResponse fetchOrderByOrderId(Long orderId){
        CustomerOrder order = orderRepo.findById(orderId)
                .orElseThrow(()->new OrderNotFoundException("Invalid order id!!!"));

        return toResponse(order);
    }
    public OrderResponse toResponse(CustomerOrder order){
          OrderResponse orderResponse= new OrderResponse();
          orderResponse.setAmount(order.getAmount());
          orderResponse.setId(order.getOrder_id());
          orderResponse.setStatus(String.valueOf(order.getStatus()));
          orderResponse.setCreatedAt(order.getCreatedAt());
          orderResponse.setCustomerId(order.getCustomer().getId());

          return orderResponse;
    }


}
