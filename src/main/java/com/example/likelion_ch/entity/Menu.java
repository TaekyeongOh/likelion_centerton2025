package com.example.likelion_ch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "menus")
@Getter
@Setter
@AllArgsConstructor
@Builder
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
    @JsonIgnore
    private List<OrderItem> orderItems;

    // get
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public SiteUser getUser() {
        return user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    //setter
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(SiteUser user) {
        this.user = user;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }


}
