package com.example.likelion_ch.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private SiteUser user;

    //  JPA용 기본 생성자
    public StoreFeature() {}

    public StoreFeature(String name) {
        this.name = name;
    }
}
