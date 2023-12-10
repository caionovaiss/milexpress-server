package com.milexpress.milexpressserver.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@Entity(name = "products")
@EqualsAndHashCode(of = "productId")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId", nullable = false)
    private Integer productId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "image", nullable = false)
    private String image;

    @ManyToMany
    @JoinTable(name = "cart",
            joinColumns = @JoinColumn(name = "product_fk"),
            inverseJoinColumns = @JoinColumn(name = "user_fk"))
    private Set<User> userCart;

    @ManyToMany
    @JoinTable(name = "product_order",
            joinColumns = @JoinColumn(name = "product_fk"),
            inverseJoinColumns = @JoinColumn(name = "order_fk"))
    private Set<Order> orders;

    public void addUser(User user) {
        if (!userCart.contains(user)) {
            userCart.add(user);
        } else {
            throw new RuntimeException("Esse produto ja esta no carrinho");
        }
    }

    public void removeUserFromCart(User user) {
        if (userCart.contains(user)) {
            userCart.remove(user);
        } else {
            throw new RuntimeException("Esse produto nao esta no carrinho para ser removido");
        }
    }
}