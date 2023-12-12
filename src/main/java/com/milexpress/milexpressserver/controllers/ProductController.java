package com.milexpress.milexpressserver.controllers;

import com.milexpress.milexpressserver.model.request.ProductRequest;
import com.milexpress.milexpressserver.model.db.Product;
import com.milexpress.milexpressserver.model.request.ProductRequestBulk;
import com.milexpress.milexpressserver.model.response.ProductResponse;
import com.milexpress.milexpressserver.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/register")
    public Product newProduct(@RequestBody ProductRequest productRegRequest) {
        return productService.save(productRegRequest);
    }

    @PostMapping("/register/all")
    public void addAllProducts(@RequestBody ProductRequestBulk productRequestBulk){
        productService.saveAll(productRequestBulk);
    }

    @GetMapping("/{productId}")
    public ProductResponse getProduct(@PathVariable Integer productId){
        return productService.getProductById(productId);
    }

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

}
