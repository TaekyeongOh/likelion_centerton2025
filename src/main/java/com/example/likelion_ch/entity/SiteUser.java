package com.example.likelion_ch.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    // 2단계 회원가입에 필요한 필드 추가
    private String restaurantName;
    private String restaurantAddress;
    private String description;
    private Integer tableCount;

    @ManyToMany
    private List<StoreFeature> features;
}