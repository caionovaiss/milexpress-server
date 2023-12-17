package com.milexpress.milexpressserver;

import com.milexpress.milexpressserver.model.db.Product;
import com.milexpress.milexpressserver.repository.ProductRepository;
import com.milexpress.milexpressserver.model.response.ProductResponse;
import com.milexpress.milexpressserver.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(1, "Batata frita", "batata frita da boa", 100.0, "batata.jpg", "salgada");
    }

    @Test
    void getProductById_whenProductExists_shouldReturnProduct() {
        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(product.getProductId());

        assertNotNull(response);
        assertEquals(product.getTitle(), response.title());
        verify(productRepository, times(1)).findById(product.getProductId());
    }

    @Test
    void getProductById_whenProductNotExists_shouldThrowException() {
        int invalidProductId = 99;
        when(productRepository.findById(invalidProductId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.getProductById(invalidProductId));
    }
}
