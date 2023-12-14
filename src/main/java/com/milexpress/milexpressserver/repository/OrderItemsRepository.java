package com.milexpress.milexpressserver.repository;

import com.milexpress.milexpressserver.model.db.Order;
import com.milexpress.milexpressserver.model.db.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository  extends JpaRepository<OrderItems, Integer> {
    List<OrderItems> findAllByOrder(Order order);
}
