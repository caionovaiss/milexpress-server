package com.milexpress.milexpressserver.service;

import com.milexpress.milexpressserver.model.db.Cart;
import com.milexpress.milexpressserver.model.db.CartItems;
import com.milexpress.milexpressserver.model.db.Product;
import com.milexpress.milexpressserver.model.db.User;
import com.milexpress.milexpressserver.model.request.CartRequest;
import com.milexpress.milexpressserver.model.response.CartResponse;
import com.milexpress.milexpressserver.model.response.ProductResponse;
import com.milexpress.milexpressserver.repository.CartItemsRepository;
import com.milexpress.milexpressserver.repository.CartRepository;
import com.milexpress.milexpressserver.repository.ProductRepository;
import com.milexpress.milexpressserver.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemsRepository cartItemsRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    CartService(@Autowired CartRepository cartRepository,
                @Autowired UserRepository userRepository,
                @Autowired ProductRepository productRepository,
                @Autowired CartItemsRepository cartItemsRepository
    ) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemsRepository = cartItemsRepository;
    }

    public void addProductToCart(CartRequest cartRequest) {
        User user = userRepository.findById(cartRequest.userEmail())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(cartRequest.productId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        Optional<CartItems> existingCartItem = cartItemsRepository.findByCartAndProduct(cart, product);

        if (existingCartItem.isPresent()) {
            CartItems cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemsRepository.save(cartItem);
        } else {
            CartItems newCartItem = new CartItems();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(1);
            cartItemsRepository.save(newCartItem);
        }
    }

    public void subtractProductFromCart(CartRequest cartRequest) {
        User user = userRepository.findById(cartRequest.userEmail())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(cartRequest.productId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        Optional<CartItems> existingCartItem = cartItemsRepository.findByCartAndProduct(cart, product);

        if (existingCartItem.isEmpty()) {
            throw new EntityNotFoundException("Esse item nao esta no carrinho do usuario");
        } else {
            CartItems cartItem = existingCartItem.get();

            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartItemsRepository.save(cartItem);
            } else {
                cartItemsRepository.delete(cartItem);
            }
        }
    }

    public CartResponse getUserCart(String userEmail) {
        User user = userRepository.findById(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("User has no cart"));

        List<CartItems> cartItems = cartItemsRepository.findAllByCart(cart);

        List<ProductResponse> productResponses = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        for (CartItems item : cartItems) {
            Product product = productRepository.findById(item.getProduct().getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            ProductResponse productResponse = new ProductResponse(
                    product.getProductId(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getImage(),
                    product.getNote(),
                    product.getValue()
            );
            productResponses.add(productResponse);
            quantities.add(item.getQuantity());
        }

        return new CartResponse(productResponses, quantities);
    }

    @Transactional
    public void cleanCart(String userEmail) {
        User user = userRepository.findById(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        List<CartItems> cartItemsList = cartItemsRepository.deleteAllByCart(cart);

        cartRepository.deleteByUser(user);
    }

    @Transactional
    public void removeProductFromCart(CartRequest cartRequest) {
        User user = userRepository.findById(cartRequest.userEmail())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(cartRequest.productId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        Optional<CartItems> existingCartItem = cartItemsRepository.findByCartAndProduct(cart, product);

        if (existingCartItem.isEmpty()) {
            throw new EntityNotFoundException("Esse item nao esta no carrinho do usuario");
        } else {
            CartItems cartItem = existingCartItem.get();
            cartItemsRepository.delete(cartItem);
        }
    }
}
