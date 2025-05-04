package com.elusivemel.orderservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elusivemel.orderservice.OrderStatus;
import com.elusivemel.orderservice.dto.OrderRequest;
import com.elusivemel.orderservice.dto.OrderResponse;
import com.elusivemel.orderservice.model.Order;
import com.elusivemel.orderservice.model.OrderItem;
import com.elusivemel.orderservice.repository.OrderItemRepository;
import com.elusivemel.orderservice.repository.OrderRepository;

import lombok.experimental.PackagePrivate;

@RestController
@RequestMapping("/api")
public class OrderController {

    private static final Logger logger = LogManager.getLogger(OrderController.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderController(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderController(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public OrderController() {
    }

    @PostMapping("/orders/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest req) {
        // log the incoming items
        logger.info("Creating order for items: {}", req.getItems());
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        logger.info("Order saved: {}", savedOrder);

        List<OrderItem> savedOrderItems = (List<OrderItem>) req.getItems().stream()
                .map(itemReq -> {
                    // Convert DTO → Entity
                    OrderItem item = new OrderItem();
                    item.setOrder(savedOrder);
                    item.setProductId(itemReq.getProductId());
                    item.setQuantity(itemReq.getQuantity());

                    // Persist  return the managed entity
                    return orderItemRepository.save(item);
                })
                .collect(Collectors.toList());

        logger.info("Items to add to order: {}", savedOrderItems);

        order.setItems(savedOrderItems);
        // order.setStatus(OrderStatus.PENDING);

        OrderResponse response = new OrderResponse(order);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
