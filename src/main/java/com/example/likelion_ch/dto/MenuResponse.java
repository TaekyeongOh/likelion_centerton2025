package com.example.likelion_ch.dto;

public class MenuResponse {
    private Long menuId;
    private String menuName;
    private String menuDescription;

    public MenuResponse(Long menuId, String menuName, String menuDescription) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuDescription = menuDescription;
    }

    // Getter
    public Long getMenuId() { return menuId; }
    public String getMenuName() { return menuName; }
    public String getMenuDescription() { return menuDescription; }
}
