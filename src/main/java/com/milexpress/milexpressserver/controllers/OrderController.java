package com.milexpress.milexpressserver.controllers;

import com.milexpress.milexpressserver.model.db.OrderItems;
import com.milexpress.milexpressserver.model.request.OrderRequest;
import com.milexpress.milexpressserver.model.response.OrderResponse;
import com.milexpress.milexpressserver.model.db.Order;
import com.milexpress.milexpressserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://192.168.15.18:8080")
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderResponse> getAll(@RequestBody String userEmail) {
        return orderService.getAll(userEmail);
    }

    @PostMapping("/create")
    public List<OrderItems> createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }
}
