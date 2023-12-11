package com.milexpress.milexpressserver.service;

import com.milexpress.milexpressserver.model.request.ProductRequest;
import com.milexpress.milexpressserver.model.db.Product;
import com.milexpress.milexpressserver.model.request.ProductRequestBulk;
import com.milexpress.milexpressserver.model.response.ProductResponse;
import com.milexpress.milexpressserver.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    ProductService(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(ProductRequest productRequest) {
        return productRepository.save(convertProductRequestToProduct(productRequest));
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public void saveAll(ProductRequestBulk productRequestBulk) {
        for (ProductRequest productRequest : productRequestBulk.products()) {
            productRepository.save(convertProductRequestToProduct(productRequest));
        }
    }

    private Product convertProductRequestToProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setTitle(productRequest.title());
        product.setDescription(productRequest.description());
        product.setValue(productRequest.value());
        product.setImage(productRequest.image());
        product.setNote(productRequest.note());
        return product;
    }

    public ProductResponse getProductById(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Produto nao encontrado " + productId));
        return convertToProductResponse(product);
    }

    private ProductResponse convertToProductResponse(Product product) {
        return new ProductResponse(
                product.getProductId(),
                product.getTitle(),
                product.getDescription(),
                product.getImage(),
                product.getNote(),
                product.getValue());
    }
}
