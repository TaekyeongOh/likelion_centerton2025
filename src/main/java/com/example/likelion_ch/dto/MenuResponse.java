package com.example.likelion_ch.dto;

public class MenuResponse {
    private Long menuId;
    private String menuName;
    private String menuDescription;
    private Integer menuPrice;

    public MenuResponse(Long menuId, String menuName, String menuDescription, Integer menuPrice) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuDescription = menuDescription;
        this.menuPrice = menuPrice;
    }

    // Getter
    public Long getMenuId() { return menuId; }
    public String getMenuName() { return menuName; }
    public String getMenuDescription() { return menuDescription; }
    public Integer getMenuPrice() { return menuPrice; }
}
