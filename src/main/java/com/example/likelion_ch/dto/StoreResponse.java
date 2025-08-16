package com.example.likelion_ch.dto;

import com.example.likelion_ch.entity.Menu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StoreResponse {
    private String restaurantName;
    private String restaurantAddress;
    private String shortDescription;
    private String longDescription;
    private List<Menu> menuList;

    public StoreResponse(String restaurantName, String restaurantAddress,
                         String shortDescription, String longDescription,
                         List<Menu> menuList) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.menuList = menuList;
    }
}