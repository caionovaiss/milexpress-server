package com.milexpress.milexpressserver;

import com.milexpress.milexpressserver.model.db.Order;
import com.milexpress.milexpressserver.model.db.OrderRate;
import com.milexpress.milexpressserver.model.db.User;
import com.milexpress.milexpressserver.model.db.UserRole;
import com.milexpress.milexpressserver.model.request.RateOrderRequest;
import com.milexpress.milexpressserver.model.response.OrderResponse;
import com.milexpress.milexpressserver.repository.*;
import com.milexpress.milexpressserver.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemsRepository orderItemsRepository;
    @Mock
    private OrderRateRepository orderRateRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("marcelo@eler.com", "123", "Marcelo Eler", "123456789", UserRole.USER, null);
        order = new Order(1, 100.0, 10.0, "em processamento", 0.0, 110.0, null, user, Instant.now());
    }

    @Test
    void getOrder_whenOrderExists_shouldReturnOrder() {
        when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));

        OrderResponse response = orderService.getOrder(order.getOrderId()).orderResponse();

        assertNotNull(response);
        assertEquals(order.getOrderId(), response.orderId());
        verify(orderRepository, times(1)).findById(order.getOrderId());
    }

    @Test
    void getOrder_whenOrderNotExists_shouldThrowException() {
        int invalidOrderId = 99;
        when(orderRepository.findById(invalidOrderId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.getOrder(invalidOrderId));
    }
}
