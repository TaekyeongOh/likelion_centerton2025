package com.example.likelion_ch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "menus")
@Getter
@Setter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    private String name;            // 메뉴 이름
    private Integer price;          // 가격
    private String description;  //메뉴 설명

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private SiteUser user;

    // 기본 생성자
    public Menu() {}

    public Menu(String name, Integer price, String description, SiteUser user) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.user = user;
    }

    @OneToMany(mappedBy = "menu")
    private List<OrderItem> orderItems;

}
