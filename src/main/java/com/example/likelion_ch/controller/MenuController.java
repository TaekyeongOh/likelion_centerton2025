package com.example.likelion_ch.controller;

import com.example.likelion_ch.dto.*;
import com.example.likelion_ch.entity.SiteUser;
import com.example.likelion_ch.service.MenuService;
import com.example.likelion_ch.service.StoreService;
import com.example.likelion_ch.util.QRCodeGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
@Tag(name = "Menu API", description = "메뉴 관리 API")
public class MenuController {

    private final MenuService menuService;
    private final StoreService storeService;

    // 언어별 메뉴 조회 (이미 번역됨)
    @GetMapping("/{userId}/settings/menu_info/{langCode}")
    public ResponseEntity<List<MenuInfo>> getTranslatedMenuInfo(
            @PathVariable Long userId,
            @PathVariable String langCode) {

        log.info("번역 메뉴 조회 요청: 사용자={}, 언어={}", userId, langCode);

        try {
            // 기존 메뉴만 조회
            List<MenuInfo> menuInfoList = menuService.getExistingMenusByLanguage(userId, langCode);

            log.info("기존 메뉴 조회 완료: 사용자={}, 언어={}, 메뉴 수={}", userId, langCode, menuInfoList.size());

            return ResponseEntity.ok(menuInfoList);

        } catch (IllegalArgumentException e) {
            log.warn("잘못된 요청: {}", e.getMessage());
            return ResponseEntity.badRequest().build();

        } catch (Exception e) {
            log.error("메뉴 조회 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // 가게 정보 + 메뉴 리스트
    @GetMapping("/{userId}")
    @Operation(summary = "가게 정보와 메뉴 조회", description = "가게 정보와 함께 메뉴 목록을 조회합니다.")
    public ResponseEntity<StoreResponse> getStoreWithMenu(@PathVariable Long userId) {
        return ResponseEntity.ok(menuService.getStoreWithMenu(userId));
    }

    // 베스트 메뉴 TOP3
    @GetMapping("/{userId}/top3")
    public ResponseEntity<TopMenuResponse> getBestMenus(@PathVariable Long userId) {
        return ResponseEntity.ok(menuService.getTopMenu(userId));
    }

    // 언어 기반 추천 메뉴 TOP3
    @GetMapping("/{userId}/recommend")
    public ResponseEntity<TopMenuResponse> getRecommendedMenus(
            @PathVariable Long userId,
            @RequestParam String lang) {
        return ResponseEntity.ok(menuService.getTopMenuByLanguage(userId, lang));
    }

    // 전체 메뉴 조회 (페이지네이션, 검색, 정렬 지원)
    @GetMapping("/{userId}/all")
    public ResponseEntity<Map<String, Object>> getAllMenus(@PathVariable Long userId) {
        List<MenuInfo> menus = menuService.getAllMenusForStore(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("menus", menus);

        return ResponseEntity.ok(response);
    }

    // QRCode
    @GetMapping("/{userId}/qrCode")
    @Operation(summary = "가게 QR코드 생성", description = "가게 정보가 포함된 QR코드를 생성합니다.")
    public ResponseEntity<byte[]> getStoreQRCode(@PathVariable Long userId) {
        try {
            // QR코드에 넣을 URL (예: 해당 가게 상세 페이지)
            String storeUrl = "https://example.com/store/" + userId;

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
}
