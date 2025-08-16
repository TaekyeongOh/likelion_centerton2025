package com.example.likelion_ch.controller;

import com.example.likelion_ch.dto.StoreResponse;
import com.example.likelion_ch.dto.TopMenuResponse;
import com.example.likelion_ch.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.likelion_ch.dto.StoreMenusResponse;


@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;  // final로 선언하면 @RequiredArgsConstructor가 자동으로 생성자 생성

    // 가게 정보 + 메뉴 리스트
    @GetMapping("/{userId}")
    public ResponseEntity<StoreResponse> getStoreWithMenu(@PathVariable Long userId) {
        return ResponseEntity.ok(menuService.getStoreWithMenu(userId));
    }

    // 베스트 메뉴 TOP3
    @GetMapping("/{userId}/top3")
    public ResponseEntity<TopMenuResponse> getBestMenus(@PathVariable Long userId) {
        return ResponseEntity.ok(menuService.getBestMenus(userId));
    }

    // 언어 기반 추천 메뉴 TOP3
    @GetMapping("/{userId}/recommend")
    public ResponseEntity<TopMenuResponse> getRecommendedMenus(@PathVariable Long userId,
                                                               @RequestParam String lang) {
        return ResponseEntity.ok(menuService.getRecommendedMenus(userId, lang));
    }

    // MenuController.java
    @GetMapping("/{userId}/all")
    public ResponseEntity<StoreMenusResponse> getAllMenus(@PathVariable Long userId) {
        return ResponseEntity.ok(menuService.getAllMenus(userId));
    }



}
