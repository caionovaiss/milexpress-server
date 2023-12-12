package com.milexpress.milexpressserver.controllers;

import com.milexpress.milexpressserver.model.request.CartRequest;
import com.milexpress.milexpressserver.model.response.CartResponse;
import com.milexpress.milexpressserver.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public CartResponse getUserCart(@RequestBody String userEmail) {
        return cartService.getUserCart(userEmail);
    }

    @PostMapping("/add")
    public void addProductToCart(@RequestBody CartRequest cartRequest) {
        cartService.addProductToCart(cartRequest);
    }

    @DeleteMapping("/remove")
    public void removeProductFromCart(@RequestBody CartRequest cartRequest) {
        cartService.removeProductFromCart(cartRequest);
    }

    @DeleteMapping("/clean")
    public void cleanCart(@RequestBody String userEmail){
        cartService.cleanCart(userEmail);
    }
}
