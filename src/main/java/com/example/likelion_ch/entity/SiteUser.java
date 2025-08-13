package com.example.likelion_ch.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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

    @Column(length = 200)
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String longDescription;
    private Integer tableCount;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<StoreFeature> features = new ArrayList<>();

}