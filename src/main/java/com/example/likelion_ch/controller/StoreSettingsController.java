package com.example.likelion_ch.controller;

import com.example.likelion_ch.dto.MenuRequest;
import com.example.likelion_ch.dto.MenuResponse;
import com.example.likelion_ch.dto.StoreUpdateRequest;
import com.example.likelion_ch.entity.Menu;
import com.example.likelion_ch.entity.SiteUser;
import com.example.likelion_ch.repository.MenuRepository;
import com.example.likelion_ch.repository.SiteUserRepository;
import com.example.likelion_ch.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/store/{userId}/settings")
public class StoreSettingsController {

    private final SiteUserRepository siteUserRepository;
    private final MenuRepository menuRepository;

    public StoreSettingsController(SiteUserRepository siteUserRepository,
                                   MenuRepository menuRepository) {
        this.siteUserRepository = siteUserRepository;
        this.menuRepository = menuRepository;
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

    @PostMapping("/menu_info")
    public ResponseEntity<MenuResponse> addMenu(@PathVariable Long userId,
                                                @RequestBody MenuRequest request) {
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Menu menu = new Menu(
                request.getMenuName(),
                request.getMenuPrice(),        // price 적용
                request.getMenuDescription(),
                user
        );

        menu = menuRepository.save(menu);

        MenuResponse response = new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getDescription(),
                menu.getPrice()               // response에도 price 포함 가능
        );

        return ResponseEntity.ok(response);
    }
}
