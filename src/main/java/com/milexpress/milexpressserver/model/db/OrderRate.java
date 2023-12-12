package com.milexpress.milexpressserver.model.db;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_rates")
@Entity(name = "order_rates")
@EqualsAndHashCode(of = "rateId")
public class OrderRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    private Integer rateId;

    @Column(name = "star_rating")
    private Integer starRating;

    @Column(name = "note")
    private String note;
}
