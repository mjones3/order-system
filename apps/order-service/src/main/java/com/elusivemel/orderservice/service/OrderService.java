package com.elusivemel.orderservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.elusivemel.orderservice.dto.OrderRequest;
import com.elusivemel.orderservice.model.Order;
import com.elusivemel.orderservice.repository.OrderRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.elusivemel.orderservice.OrderStatus;
import com.elusivemel.orderservice.model.OrderItem;
import com.elusivemel.orderservice.repository.OrderItemRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private static final Logger logger = LogManager.getLogger(OrderService.class);

    public Order createOrder(OrderRequest request) {
        logger.info("Creating order for items: {}", request.getItems());
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        logger.info("Order saved: {}", savedOrder);

        List<OrderItem> savedOrderItems = (List<OrderItem>) request.getItems().stream()
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

        return order;

    }

    public Optional<Order> cancelOrder(Long id) {

        logger.info("Cancelling order for orderId: {}", id);

        return orderRepository.findById(id)
                .map(o -> {
                    o.setStatus(OrderStatus.CANCELLED);
                    return orderRepository.save(o);
                });
    }

    public void setOrderItemRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

}
