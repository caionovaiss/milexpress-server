package com.milexpress.milexpressserver.repository;

import com.milexpress.milexpressserver.model.db.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT * FROM orders WHERE USER_EMAIL = ?1", nativeQuery = true)
    List<Order> findByUserEmail(String userEmail);

}
