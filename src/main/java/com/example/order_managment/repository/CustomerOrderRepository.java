package com.example.order_managment.repository;

import com.example.order_managment.entity.CustomerOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByCustomerId(Long customer_id);
    Page<CustomerOrder> findAll(Pageable pageable);
}
