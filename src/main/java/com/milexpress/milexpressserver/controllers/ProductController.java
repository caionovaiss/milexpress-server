package com.milexpress.milexpressserver.controllers;

import com.milexpress.milexpressserver.model.request.ProductRequest;
import com.milexpress.milexpressserver.model.request.CartRequest;
import com.milexpress.milexpressserver.model.db.Product;
import com.milexpress.milexpressserver.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/register")
    public Product newProduct(@RequestBody ProductRequest productRegRequest) {
        return productService.save(productRegRequest);
    }

    @GetMapping
    public List<Product> getAll(){
        return productService.getAll();
    }

    @PostMapping("/cart/register")
    public Product addToCart(@RequestBody CartRequest cartRequest) {
        return productService.addProductToCart(cartRequest);
    }

    @PostMapping("/cart/remove")
    public Product removeFromCart(@RequestBody CartRequest removeProductFromCartRequest) {
        return productService.removeFromCart(removeProductFromCartRequest);
    }

}
