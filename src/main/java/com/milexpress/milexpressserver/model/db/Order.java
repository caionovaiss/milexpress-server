package com.milexpress.milexpressserver.model.db;

import jakarta.persistence.*;
import lombok.*;


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

    @Column(name = "discount", nullable = false)
    private double discount;

    @Column(name = "total", nullable = false)
    private double total;

    @OneToOne
    @JoinColumn(name = "rateId")
    private OrderRate orderRate;

    @ManyToOne
    @JoinColumn(name = "userEmail")
    private User user;
}