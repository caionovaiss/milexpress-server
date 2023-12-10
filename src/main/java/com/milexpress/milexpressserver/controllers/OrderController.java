package com.milexpress.milexpressserver.controllers;

import com.milexpress.milexpressserver.model.request.OrderRequest;
import com.milexpress.milexpressserver.model.response.OrderResponse;
import com.milexpress.milexpressserver.model.db.Order;
import com.milexpress.milexpressserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderResponse> getAll(String userEmail){
        return orderService.getAll(userEmail);
    }

//    @PostMapping("/register")
//    public Order makeOrder(@RequestBody OrderRequest orderRequest){
//
//    }
}
