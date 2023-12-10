package com.milexpress.milexpressserver.service;

import com.milexpress.milexpressserver.model.request.ProductRequest;
import com.milexpress.milexpressserver.model.request.CartRequest;
import com.milexpress.milexpressserver.model.db.Product;
import com.milexpress.milexpressserver.repository.ProductRepository;
import com.milexpress.milexpressserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private UserRepository userRepository;

    ProductService(@Autowired ProductRepository productRepository, @Autowired UserRepository userRepository) {
        this.productRepository = productRepository;
    }

    public Product save(ProductRequest productRequest) {
        return convertProductRequestToProduct(productRequest);
    }

    private Product convertProductRequestToProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setTitle(productRequest.title());
        product.setDescription(productRequest.description());
        product.setImage(productRequest.image());
        return product;
    }

    public Product addProductToCart(CartRequest cartRequest) {
        Integer productId = cartRequest.productId();
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found for ID: " + productId));

        product.addUser(userRepository.findByEmail(cartRequest.userEmail()));
        return productRepository.save(product);

    }

    public Product removeFromCart(CartRequest removeProductFromCartRequest) {
        Integer productId = removeProductFromCartRequest.productId();
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found for ID: " + productId));

        product.removeUserFromCart(userRepository.findByEmail(removeProductFromCartRequest.userEmail()));
        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
