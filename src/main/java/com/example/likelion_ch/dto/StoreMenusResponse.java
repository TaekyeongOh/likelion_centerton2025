// StoreMenusResponse.java
package com.example.likelion_ch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StoreMenusResponse {
    private List<MenuSummary> menus;

    @Getter
    @AllArgsConstructor
    public static class MenuSummary {
        private String menuName;
        private String shortDescription;
    }
}