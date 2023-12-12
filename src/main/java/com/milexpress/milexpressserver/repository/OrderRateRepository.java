package com.milexpress.milexpressserver.repository;

import com.milexpress.milexpressserver.model.db.OrderRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRateRepository extends JpaRepository<OrderRate, Integer> {
}
