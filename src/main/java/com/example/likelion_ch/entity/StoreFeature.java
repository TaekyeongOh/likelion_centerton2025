package com.example.likelion_ch.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "store_features")
@Getter
@Setter
public class StoreFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long featureId;

    @Column(length = 100, nullable = false, unique = true)
    private String name;
}
