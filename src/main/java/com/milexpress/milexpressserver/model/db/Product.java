package com.milexpress.milexpressserver.model.db;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "product_id")
    private Integer productId;

    private String title;
    private String description;
    private double value;
    private String image;
    private String note;
}