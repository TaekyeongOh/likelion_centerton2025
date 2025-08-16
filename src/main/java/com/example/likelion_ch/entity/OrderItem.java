package com.example.likelion_ch.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Integer quantity;
    private String language; // 주문자가 선택한 언어

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu; // 주문한 메뉴

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SiteUser user; // 어떤 가게인지

}
