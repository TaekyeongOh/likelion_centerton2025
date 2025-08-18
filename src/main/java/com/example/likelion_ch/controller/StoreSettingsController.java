package com.example.likelion_ch.controller;

import com.example.likelion_ch.dto.MenuInfo;
import com.example.likelion_ch.dto.MenuRequest;
import com.example.likelion_ch.dto.MenuResponse;
import com.example.likelion_ch.dto.StoreUpdateRequest;
import com.example.likelion_ch.entity.Menu;
import com.example.likelion_ch.entity.SiteUser;
import com.example.likelion_ch.repository.MenuRepository;
import com.example.likelion_ch.repository.SiteUserRepository;
import com.example.likelion_ch.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
@RestController
@RequestMapping("/api/store/{userId}/settings")
public class StoreSettingsController {

    private final SiteUserRepository siteUserRepository;
    private final MenuRepository menuRepository;
    private final MenuService menuService;

    public StoreSettingsController(SiteUserRepository siteUserRepository,
                                   MenuRepository menuRepository,
                                   MenuService menuService) {
        this.siteUserRepository = siteUserRepository;
        this.menuRepository = menuRepository;
        this.menuService = menuService;
    }

    @PatchMapping("/store_info")
    public ResponseEntity<?> updateStoreInfo(@PathVariable Long userId, @RequestBody StoreUpdateRequest request) {
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        user.setRestaurantName(request.getRestaurantName());
        user.setRestaurantAddress(request.getRestaurantAddress());
        user.setShortDescription(request.getShortDescription());
        user.setLongDescription(request.getLongDescription());

        siteUserRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "식당 정보가 수정되었습니다.");
        return ResponseEntity.ok(response);
    }

    // --- 메뉴 조회 (전체) ---
    @GetMapping("/menu_info")
    public ResponseEntity<List<MenuResponse>> getMenuInfo(@PathVariable Long userId) {
        List<MenuResponse> menus = menuService.getMenusByUser(userId);
        return ResponseEntity.ok(menus);
    }

    // --- 단일 메뉴 조회 ---
    @GetMapping("/menu_info/{menuId}")
    public ResponseEntity<MenuResponse> getMenu(
            @PathVariable Long userId,
            @PathVariable Long menuId) {
        MenuResponse menu = menuService.getMenuById(userId, menuId);
        return ResponseEntity.ok(menu);
    }

    // --- 메뉴 등록 ---
    @PostMapping("/menu_info")
    public ResponseEntity<MenuResponse> createMenu(
            @PathVariable Long userId,
            @Valid @RequestBody MenuRequest request) {
        MenuResponse menu = menuService.createMenu(userId, request);
        return ResponseEntity.ok(menu);
    }

    // --- 메뉴 수정 ---
    @PutMapping("/menu_info/{userMenuId}")
    public ResponseEntity<MenuResponse> updateMenu(
            @PathVariable Long userId,
            @PathVariable Integer userMenuId,
            @Valid @RequestBody MenuRequest request) {

        MenuResponse menu = menuService.updateMenu(userId, userMenuId, request);
        return ResponseEntity.ok(menu);
    }

    // --- 메뉴 삭제 ---
    @DeleteMapping("/menu_info/{userMenuId}")
    public ResponseEntity<Void> deleteMenu(
            @PathVariable Long userId,
            @PathVariable("userMenuId") Integer userMenuId) {
        menuService.deleteMenu(userId, userMenuId);
        return ResponseEntity.noContent().build();
    }
}
