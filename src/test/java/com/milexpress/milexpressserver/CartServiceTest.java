package com.milexpress.milexpressserver;

import com.milexpress.milexpressserver.model.db.*;
import com.milexpress.milexpressserver.model.request.CartRequest;
import com.milexpress.milexpressserver.model.response.CartResponse;
import com.milexpress.milexpressserver.model.response.ProductResponse;
import com.milexpress.milexpressserver.repository.*;
import com.milexpress.milexpressserver.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemsRepository cartItemsRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    private User user;
    private Cart cart;
    private Product product;
    private CartItems cartItem;

    @BeforeEach
    void setUp() {
        user = new User();
        cart = new Cart(1, user);
        product = new Product(1, "Pizza marguerita", "Pizza com queijo, tomate, manjericao e oregano", 100.0, "marguerita.jpg", "Contem lactose");
        cartItem = new CartItems(1, cart, product, 10);
    }

    @Test
    void addProductToCart_whenUserAndProductExist_shouldAddProduct() {
        CartRequest cartRequest = new CartRequest(user.getEmail(), product.getProductId());

        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));
        when(cartItemsRepository.findByCartAndProduct(cart, product)).thenReturn(Optional.empty());

        cartService.addProductToCart(cartRequest);

        ArgumentCaptor<CartItems> captor = ArgumentCaptor.forClass(CartItems.class);
        verify(cartItemsRepository).save(captor.capture());
        CartItems capturedCartItem = captor.getValue();

        assertNotNull(capturedCartItem);
        assertEquals(cart, capturedCartItem.getCart());
        assertEquals(product, capturedCartItem.getProduct());
        assertEquals(1, capturedCartItem.getQuantity());    }

    @Test
    void getUserCart_whenCartExists_shouldReturnCartResponse() {
        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(cartItemsRepository.findAllByCart(cart)).thenReturn(Collections.singletonList(cartItem));
        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));

        CartResponse response = cartService.getUserCart(user.getEmail());

        assertNotNull(response);
        assertFalse(response.products().isEmpty());
        assertEquals(1, response.products().size());
        assertEquals(product.getProductId(), response.products().get(0).productId());
    }

    @Test
    void getUserCart_whenUserNotFound_shouldThrowException() {
        when(userRepository.findById("invalido@exemplo.com")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cartService.getUserCart("invalido@exemplo.com"));
    }
}
