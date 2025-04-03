package com.elusivemel.orderservice.controller;

import com.elusivemel.orderservice.model.Order;
import com.elusivemel.orderservice.service.SqsMessageSender;
import com.elusivemel.orderservice.repository.OrderRepository;

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
        logger.info("Preparing to send to queue...");

        for (int i = 0; i < 10; i++) {
            sqsMessageSender.sendMessage(savedOrder.toString());
            logger.info("Sending message to queue: #" + i);
        }

        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }
}
