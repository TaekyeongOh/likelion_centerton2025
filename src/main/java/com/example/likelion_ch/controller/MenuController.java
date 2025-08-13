package com.example.likelion_ch.controller;

import com.example.likelion_ch.dto.StoreResponse;
import com.example.likelion_ch.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // 가게 정보 + 메뉴 리스트 한 번에 GET
    @GetMapping("/{userId}")
    public ResponseEntity<StoreResponse> getStoreWithMenu(@PathVariable Long userId) {
        StoreResponse response = menuService.getStoreWithMenu(userId);
        return ResponseEntity.ok(response);
    }
}
