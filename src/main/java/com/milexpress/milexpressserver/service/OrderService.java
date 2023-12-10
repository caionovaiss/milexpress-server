package com.milexpress.milexpressserver.service;

import com.milexpress.milexpressserver.model.response.OrderResponse;
import com.milexpress.milexpressserver.model.db.Order;
import com.milexpress.milexpressserver.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    OrderService(@Autowired OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderResponse> getAll(String userEmail) {
        List<Order> orders = orderRepository.findByUserEmail(userEmail);
        List<OrderResponse> orderResponses = new ArrayList<>();

        if (orders == null) {
            orders = new ArrayList<>();
        }

        orders.forEach(order -> {
            orderResponses.add(convertToOrderResponse(order));
        });

        return orderResponses;
    }

    private OrderResponse convertToOrderResponse(Order order) {
        return new OrderResponse(order.getOrderId(), order.getStatus(), order.getSubtotal(), order.getTax());
    }
}
