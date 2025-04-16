package com.elusivemel.orderservice.controller;

import com.elusivemel.orderservice.model.Order;
import com.elusivemel.orderservice.service.SqsMessageSender;
import com.elusivemel.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LogManager.getLogger(OrderController.class);
    String json = "{}";

    @Autowired
    SqsMessageSender sqsMessageSender;

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order savedOrder = orderRepository.save(order);
        logger.info(savedOrder);

        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }
}
