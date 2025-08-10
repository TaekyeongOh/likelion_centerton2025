package com.example.likelion_ch.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id", nullable = false, unique = true)
    private Long restaurantId;

    @Column(length = 50, nullable = false)
    private String restaurantName;

    @Column(length = 60, nullable = false)
    private String restaurantPw;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(nullable = false)
    private String role; // OWNER or CUSTOMER

    @Column(name = "table_count")
    private Integer tableCount;
}