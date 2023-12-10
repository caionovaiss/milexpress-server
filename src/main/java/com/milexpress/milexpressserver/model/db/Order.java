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
@Table(name = "orders")
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    @Column(name = "subtotal", nullable = false)
    private double subtotal;

    @Column(name = "tax", nullable = false)
    private double tax;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "userEmail", insertable = false, updatable = false)
    private User user;

    @Column(name = "userEmail", nullable = false)
    private String userEmail;

    @JsonIgnore
    @ManyToMany(mappedBy = "orders")
    private Set<Product> products;
}