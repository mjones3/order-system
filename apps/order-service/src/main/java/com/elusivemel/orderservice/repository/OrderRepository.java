package com.elusivemel.orderservice.repository;

import com.elusivemel.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}