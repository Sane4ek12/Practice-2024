package com.example.practice.repository;

import com.example.practice.enums.OrderStatus;
import com.example.practice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByClientId(Long id);
    List<Order> findAllByProductId(Long id);
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);
}
