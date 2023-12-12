package com.milexpress.milexpressserver.service;

import com.milexpress.milexpressserver.model.db.*;
import com.milexpress.milexpressserver.model.request.OrderRequest;
import com.milexpress.milexpressserver.model.request.UpdateOrderRequest;
import com.milexpress.milexpressserver.model.response.CartResponse;
import com.milexpress.milexpressserver.model.response.OrderResponse;
import com.milexpress.milexpressserver.model.response.ProductResponse;
import com.milexpress.milexpressserver.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemsRepository cartItemsRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    OrderService(@Autowired OrderRepository orderRepository,
                 @Autowired CartRepository cartRepository,
                 @Autowired CartItemsRepository cartItemsRepository,
                 @Autowired UserRepository userRepository,
                 @Autowired CartService cartService,
                 @Autowired ProductRepository productRepository,
                 @Autowired OrderItemsRepository orderItemsRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.orderItemsRepository = orderItemsRepository;
    }

    public List<OrderResponse> getAll(String userEmail) {
        List<Order> orders = orderRepository.findByUserEmail(userEmail);
        List<OrderResponse> orderResponses = new ArrayList<>();

        if (orders == null) {
            orders = new ArrayList<>();
        }

        orders.forEach(order -> {
            orderResponses.add(convertToOrderResponse(order));
        });

        return orderResponses;
    }

    private OrderResponse convertToOrderResponse(Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getStatus(),
                order.getSubtotal(),
                order.getTax(),
                order.getTotal(),
                order.getDiscount()
        );
    }

    public List<OrderItems> createOrder(OrderRequest orderRequest) {
        User user = userRepository.findById(orderRequest.userEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Order order = new Order();
        order.setSubtotal(orderRequest.subtotal());
        order.setTax(orderRequest.tax());
        order.setTotal(orderRequest.total());
        order.setDiscount(orderRequest.discount());
        order.setStatus(orderRequest.status());
        order.setUser(user);

        order = orderRepository.save(order);

        CartResponse cartResponse = cartService.getUserCart(orderRequest.userEmail());

        List<OrderItems> items = new ArrayList<>();

        for (int i = 0; i < cartResponse.products().size(); i++) {
            OrderItems orderItems = new OrderItems();
            ProductResponse productResponse = cartResponse.products().get(i);
            Product product = productRepository.findById(productResponse.productId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            orderItems.setProduct(product);
            orderItems.setPriceOnPurchase(product.getValue());
            orderItems.setQuantity(cartResponse.quantities().get(i));
            orderItems.setOrder(order);
            orderItemsRepository.save(orderItems);
            items.add(orderItems);
        }
        cartService.cleanCart(orderRequest.userEmail());

        return items;

    }

    public OrderResponse getOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        return convertToOrderResponse(order);
    }

    public OrderResponse updateOrderStatus(UpdateOrderRequest updateOrderRequest) {
        Order order = orderRepository.findById(updateOrderRequest.orderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        order.setStatus(updateOrderRequest.status());
        orderRepository.save(order);
        return convertToOrderResponse(order);
    }
}