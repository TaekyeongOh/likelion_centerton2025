package com.example.likelion_ch.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_rating")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int star; // 1~5

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order; // FK 참조
}
