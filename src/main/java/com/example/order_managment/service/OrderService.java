package com.example.order_managment.service;

import com.example.order_managment.OrderStatus;
import com.example.order_managment.dto.OrderRequest;
import com.example.order_managment.dto.OrderResponse;
import com.example.order_managment.entity.Customer;
import com.example.order_managment.entity.CustomerOrder;
import com.example.order_managment.entity.User;
import com.example.order_managment.exception.CustomerNotFoundException;
import com.example.order_managment.exception.InvalidOrderStatusException;
import com.example.order_managment.exception.OrderNotFoundException;
import com.example.order_managment.repository.CustomerOrderRepository;
import com.example.order_managment.repository.CustomerRepository;
import com.example.order_managment.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    CustomerOrderRepository orderRepo;
    CustomerRepository customerRepo;
    UserRepository userRepository;
    public OrderService(CustomerOrderRepository orderRepo, CustomerRepository customerRepo, UserRepository userRepository){
        this.orderRepo=orderRepo;
        this.customerRepo=customerRepo;
        this.userRepository=userRepository;
    }

    public OrderResponse orderPlace(OrderRequest req, Authentication auth){
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new AccessDeniedException("Access denied"));

        Customer customer = user.getCustomer();
        if (customer == null) {
            throw new IllegalArgumentException("This account has no customer profile");
        }

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

    public OrderResponse fetchOrderByOrderId(Long orderId, Authentication auth){
        CustomerOrder order = orderRepo.findById(orderId)
                .orElseThrow(()->new OrderNotFoundException("Invalid order id!!!"));
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return toResponse(order);                                  // admin sees any order
        }

        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(()-> new AccessDeniedException("Access Denied"));
        Customer customer = user.getCustomer();
        if(customer.equals(order.getCustomer())){
            return toResponse(order);
        }
        else{
            throw new AccessDeniedException("Access Denied");
        }
    }


    @Transactional
    public OrderResponse pay(Long orderId, Authentication auth){
        CustomerOrder order = orderRepo.findById(orderId)
                .orElseThrow(()->new OrderNotFoundException("Invalid order id !!!"));
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(()-> new AccessDeniedException("Access Denied"));
        if(user.getCustomer()!=order.getCustomer()){
            throw new AccessDeniedException("Access Denied");
        }
        if(order.getStatus()!= OrderStatus.PLACED){
            throw new InvalidOrderStatusException("Order "+orderId+ " is "+order.getStatus()+" cannot be paid.");
        }
        order.setStatus(OrderStatus.PAID);
        return toResponse(order);
    }

    @Transactional
    public OrderResponse cancel(Long orderId, Authentication auth){
        CustomerOrder order = orderRepo.findById(orderId)
                .orElseThrow(()->new OrderNotFoundException("Invalid order id !!!"));
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(()-> new AccessDeniedException("Access Denied"));
        if(user.getCustomer()!=order.getCustomer()){
            throw new AccessDeniedException("Access Denied");
        }
        if(order.getStatus()!= OrderStatus.PLACED){
            throw new InvalidOrderStatusException("Order "+orderId+ " is "+order.getStatus()+" cannot be cancelled.");
        }
        order.setStatus(OrderStatus.CANCELLED);
        return toResponse(order);
    }
    public List<OrderResponse> fetchAllOrder(){
        return orderRepo.findAll()
                .stream().map(this::toResponse).toList();

    }
    public List<OrderResponse> fetchAllOrderCustomer(Authentication auth){
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(()-> new AccessDeniedException("Access Denied"));
        Customer customer = user.getCustomer();
        return orderRepo.findByCustomerId(customer.getId())
                .stream().map(this::toResponse).toList();


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
