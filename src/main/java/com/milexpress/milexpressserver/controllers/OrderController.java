package com.milexpress.milexpressserver.controllers;

import com.milexpress.milexpressserver.model.db.OrderItems;
import com.milexpress.milexpressserver.model.request.OrderRequest;
import com.milexpress.milexpressserver.model.request.RateOrderRequest;
import com.milexpress.milexpressserver.model.request.UpdateOrderRequest;
import com.milexpress.milexpressserver.model.response.GetAllOrdersResponse;
import com.milexpress.milexpressserver.model.response.OrderItemsResponse;
import com.milexpress.milexpressserver.model.response.OrderResponse;
import com.milexpress.milexpressserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class    OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public GetAllOrdersResponse getAll(@RequestBody String userEmail) {
        return orderService.getAll(userEmail);
    }

    @PostMapping("/create")
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @GetMapping("/{orderId}")
    public OrderItemsResponse getOrder(@PathVariable Integer orderId) {
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
