package com.milexpress.milexpressserver.repository;

import com.milexpress.milexpressserver.model.db.Cart;
import com.milexpress.milexpressserver.model.db.CartItems;
import com.milexpress.milexpressserver.model.db.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {
    Optional<CartItems> findByCartAndProduct(Cart cart, Product product);

    List<CartItems> findAllByCart(Cart cart);

    List<CartItems> deleteAllByCart(Cart cart);
}
