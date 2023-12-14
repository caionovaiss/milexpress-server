package com.milexpress.milexpressserver.service;

import com.milexpress.milexpressserver.model.db.*;
import com.milexpress.milexpressserver.model.request.OrderRequest;
import com.milexpress.milexpressserver.model.request.RateOrderRequest;
import com.milexpress.milexpressserver.model.request.UpdateOrderRequest;
import com.milexpress.milexpressserver.model.response.*;
import com.milexpress.milexpressserver.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemsRepository cartItemsRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final OrderRateRepository orderRateRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    OrderService(@Autowired OrderRepository orderRepository,
                 @Autowired CartRepository cartRepository,
                 @Autowired CartItemsRepository cartItemsRepository,
                 @Autowired UserRepository userRepository,
                 @Autowired CartService cartService,
                 @Autowired ProductRepository productRepository,
                 @Autowired OrderItemsRepository orderItemsRepository,
                 @Autowired OrderRateRepository orderRateRepository
    ) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.orderRateRepository = orderRateRepository;
    }

    public GetAllOrdersResponse getAll(String userEmail) {
        List<Order> orders = orderRepository.findByUserEmail(userEmail);
        List<OrderResponse> orderResponses = new ArrayList<>();

        if (orders == null) {
            orders = new ArrayList<>();
        }

        orders.forEach(order -> {
            orderResponses.add(convertToOrderResponse(order));
        });

        List<List<OrderItems>> orderItemsList = new ArrayList<>();

        for(OrderResponse order : orderResponses){
            OrderItemsResponse orderItemsResponse = getOrder(order.orderId());
            orderItemsList.add(orderItemsResponse.orderItemsList());
        }
        return new GetAllOrdersResponse(orderResponses, orderItemsList);
    }

    private OrderResponse convertToOrderResponse(Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getStatus(),
                order.getSubtotal(),
                order.getTax(),
                order.getTotal(),
                order.getDiscount(),
                order.getCreatedAt()
        );
    }

    public OrderResponse createOrder(OrderRequest orderRequest) {
        User user = userRepository.findById(orderRequest.userEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Order order = new Order();
        order.setSubtotal(orderRequest.subtotal());
        order.setTax(orderRequest.tax());
        order.setTotal(orderRequest.total());
        order.setDiscount(orderRequest.discount());
        order.setStatus(orderRequest.status());
        order.setUser(user);
        order.setCreatedAt(new Date().toInstant());

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

        return convertToOrderResponse(order);

    }

    public OrderItemsResponse getOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        List<OrderItems> items = orderItemsRepository.findAllByOrder(order);


        return new OrderItemsResponse(items, convertToOrderResponse(order));

    }

    public OrderResponse updateOrderStatus(UpdateOrderRequest updateOrderRequest) {
        Order order = orderRepository.findById(updateOrderRequest.orderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        order.setStatus(updateOrderRequest.status());
        orderRepository.save(order);
        return convertToOrderResponse(order);
    }

    public OrderResponse rateOrder(RateOrderRequest rateOrderRequest) {
        Order order = orderRepository.findById(rateOrderRequest.orderId())
                .orElseThrow(() -> new EntityNotFoundException("Trying to rate an null order"));

        OrderRate orderRate = new OrderRate();
        orderRate.setStarRating(rateOrderRequest.starRating());
        orderRate.setNote(rateOrderRequest.note());
        orderRate = orderRateRepository.save(orderRate);

        order.setOrderRate(orderRate);

        order = orderRepository.save(order);

        return convertToOrderResponse(order);
    }

    public GetAllOrdersResponse getAllOrdersWithProducts(String userEmail) {
        List<OrderResponse> orderResponses = getAll(userEmail);
        List<Order> orders = orderRepository.findByUserEmail(userEmail);

        List<List<OrderItems>> orderItemsList = new ArrayList<>();

        for (Order order : orders) {
            List<OrderItems> orderItems = orderItemsRepository.findAllByOrder(order);
            orderItemsList.add(orderItems);
        }

        GetAllOrdersResponse getAllOrdersResponse = new GetAllOrdersResponse(orderResponses, orderItemsList);

        System.out.println(getAllOrdersResponse);
        return getAllOrdersResponse;
    }
}