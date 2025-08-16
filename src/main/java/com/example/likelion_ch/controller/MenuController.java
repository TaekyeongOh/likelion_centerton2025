package com.example.likelion_ch.controller;

import com.example.likelion_ch.dto.*;

import com.example.likelion_ch.entity.SiteUser;
import com.example.likelion_ch.service.MenuService;
import com.example.likelion_ch.service.StoreService;
import com.example.likelion_ch.util.QRCodeGenerator;

// import com.example.likelion_ch.service.MenuService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final StoreService storeService;

//    public MenuController(MenuService menuService, StoreService storeService) {
//        this.menuService = menuService;
//        this.storeService = storeService;
//    }

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

    // QRCode
    @GetMapping("/{userId}/qrCode")
    public ResponseEntity<byte[]> getStoreQRCode(@PathVariable Long userId) {
        try {
            // QR코드에 넣을 URL (예: 해당 가게 상세 페이지)
            String storeUrl = "https:///example.com/store/" + userId;

            BufferedImage qrImage = QRCodeGenerator.generateQRCodeImage(storeUrl, 250, 250);

            // BufferedImage -> byte[]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();

            return ResponseEntity
                    .ok()
                    .header("Content-Type", "image/png")
                    .body(imageBytes);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // 다국어 메뉴 조회
    @GetMapping("/{userId}/lan/{lang}")
    public ResponseEntity<List<MenuResponse>> getMenusByLanguage(
            @PathVariable Long userId,
            @PathVariable String lang) {

        log.info("다국어 메뉴 조회 요청 - userId: {}, language: {}", userId, lang);

        try {
            List<MenuResponse> menus = menuService.getMenusByLanguage(userId, lang);
            log.info("다국어 메뉴 조회 성공 - userId: {}, language: {}, menuCount: {}",
                    userId, lang, menus.size());
            return ResponseEntity.ok(menus);

        } catch (Exception e) {
            log.error("다국어 메뉴 조회 실패 - userId: {}, language: {}, error: {}",
                    userId, lang, e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }
}

