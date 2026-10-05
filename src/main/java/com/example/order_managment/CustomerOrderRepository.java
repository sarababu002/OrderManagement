package com.example.order_managment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.net.InterfaceAddress;
import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByCustomerId(Long customer_id);
}
