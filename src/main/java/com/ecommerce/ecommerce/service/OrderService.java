package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Order;
import com.ecommerce.ecommerce.repository.OrderRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private static final Logger logger = Logger.getLogger(OrderService.class.getName());

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        if (order.getProducts() == null || order.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one product.");
        }

        try {
            logger.info("Attempting to save order: " + order);
            Order savedOrder = orderRepository.save(order);
            logger.info("Order saved successfully: " + savedOrder);
            return savedOrder;
        } catch (DataIntegrityViolationException e) {
            logger.severe("Data integrity violation: " + e.getMessage());
            throw new RuntimeException("Error saving order to the database.", e);
        } catch (Exception e) {
            logger.severe("General error occurred while saving order: " + e.getMessage());
            throw new RuntimeException("Error saving order.", e);
        }
    }
}
