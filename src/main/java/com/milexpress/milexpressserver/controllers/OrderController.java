package com.milexpress.milexpressserver.controllers;

import com.milexpress.milexpressserver.model.db.OrderItems;
import com.milexpress.milexpressserver.model.request.OrderRequest;
import com.milexpress.milexpressserver.model.request.RateOrderRequest;
import com.milexpress.milexpressserver.model.request.UpdateOrderRequest;
import com.milexpress.milexpressserver.model.response.OrderResponse;
import com.milexpress.milexpressserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
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

    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable Integer orderId) {
        return orderService.getOrder(orderId);
    }

    @PutMapping("/update")
    public OrderResponse updateOrderStatus(@RequestBody UpdateOrderRequest updateOrderRequest) {
        return orderService.updateOrderStatus(updateOrderRequest);
    }

    @PostMapping("/rate")
    public OrderResponse rateOrder(@RequestBody RateOrderRequest rateOrderRequest){
        return orderService.rateOrder(rateOrderRequest);
    }
}
