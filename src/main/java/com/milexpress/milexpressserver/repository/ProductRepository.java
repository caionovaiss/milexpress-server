package com.milexpress.milexpressserver.repository;

import com.milexpress.milexpressserver.model.db.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
