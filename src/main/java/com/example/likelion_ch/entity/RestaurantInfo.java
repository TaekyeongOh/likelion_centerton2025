package com.example.likelion_ch.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "restaurant_info")
@Getter
@Setter
public class RestaurantInfo {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id") // user_id 컬럼을 외래 키로 사용
    private SiteUser user;

    @Column(length = 50)
    private String restaurantName; // 가게명

    @Column(length = 255)
    private String restaurantAddress; // 가게 주소

    @Column(length = 200, name = "short_description")
    private String shortDescription; // 한 줄 설명

    @Column(length = 1000, name = "long_description")
    private String longDescription; // 상세 설명@Column(length = 255)
    private String description; // 한 줄 설명

    @Column(name = "table_count")
    private Integer tableCount; // 테이블 수
}