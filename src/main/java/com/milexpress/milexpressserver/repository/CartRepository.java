package com.milexpress.milexpressserver.repository;

import com.milexpress.milexpressserver.model.db.Cart;
import com.milexpress.milexpressserver.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUser(User user);

    Optional<Cart> deleteByUser(User user);
}
