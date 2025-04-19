package com.elusivemel.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elusivemel.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
