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

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
@Tag(name = "Menu API", description = "메뉴 관리 API")
public class MenuController {

    private final MenuService menuService;
    private final StoreService storeService;

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
//    @Operation(summary = "전체 메뉴 조회", description = "페이지네이션, 검색, 정렬을 지원하는 메뉴 목록을 조회합니다.")
//    public ResponseEntity<Page<MenuResponse>> getAllMenus(
//            @PathVariable Long userId,
//            @RequestParam(required = false) String q,
//            @RequestParam(required = false) BigDecimal minPrice,
//            @RequestParam(required = false) BigDecimal maxPrice,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size,
//            @RequestParam(defaultValue = "nameKo,asc") String sort) {
//
//        // 정렬 파라미터 파싱
//        String[] sortParams = sort.split(",");
//        String sortField = sortParams[0];
//        Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])
//            ? Sort.Direction.DESC : Sort.Direction.ASC;
//
//        Pageable pageable = PageRequest.of(page, Math.min(size, 100), Sort.by(direction, sortField));
//
//        Page<MenuResponse> menus = menuService.searchMenus(userId, q, minPrice, maxPrice, pageable);
//        return ResponseEntity.ok(menus);
//    }

    // 단일 메뉴 조회
//    @GetMapping("/{userId}/menu_info/{menuId}")
//    @Operation(summary = "단일 메뉴 조회", description = "특정 메뉴의 상세 정보를 조회합니다.")
//    public ResponseEntity<MenuResponse> getMenu(
//            @PathVariable Long userId,
//            @PathVariable Long menuId) {
//        MenuResponse menu = menuService.getMenuById(userId, menuId);
//        return ResponseEntity.ok(menu);
//    }
//
//    // 메뉴 등록
//    @PostMapping("/{userId}/menus")
//    @Operation(summary = "메뉴 등록", description = "새로운 메뉴를 등록합니다.")
//    public ResponseEntity<MenuResponse> createMenu(
//            @PathVariable Long userId,
//            @Valid @RequestBody MenuRequest request) {
//        MenuResponse menu = menuService.createMenu(userId, request);
//        return ResponseEntity.ok(menu);
//    }
//
//    // 메뉴 수정
//    @PutMapping("/{userId}/menus/{menuId}")
//    @Operation(summary = "메뉴 수정", description = "기존 메뉴를 수정합니다.")
//    public ResponseEntity<MenuResponse> updateMenu(
//            @PathVariable Long userId,
//            @PathVariable Long menuId,
//            @Valid @RequestBody MenuRequest request) {
//        MenuResponse menu = menuService.updateMenu(userId, menuId, request);
//        return ResponseEntity.ok(menu);
//    }
//
//    // 메뉴 삭제
//    @DeleteMapping("/{userId}/menus/{menuId}")
//    @Operation(summary = "메뉴 삭제", description = "메뉴를 삭제합니다.")
//    public ResponseEntity<Void> deleteMenu(
//            @PathVariable Long userId,
//            @PathVariable Long menuId) {
//        menuService.deleteMenu(userId, menuId);
//        return ResponseEntity.noContent().build();
//    }

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

