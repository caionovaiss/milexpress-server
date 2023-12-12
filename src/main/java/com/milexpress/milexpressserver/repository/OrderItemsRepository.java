package com.milexpress.milexpressserver.repository;

import com.milexpress.milexpressserver.model.db.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository  extends JpaRepository<OrderItems, Integer> {
}