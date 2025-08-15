package com.example.likelion_ch.controller;

import com.example.likelion_ch.dto.MenuRequest;
import com.example.likelion_ch.dto.MenuResponse;
import com.example.likelion_ch.entity.Menu;
import com.example.likelion_ch.entity.SiteUser;
import com.example.likelion_ch.repository.MenuRepository;
import com.example.likelion_ch.repository.SiteUserRepository;
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

    @PostMapping("/menu_info")
    public ResponseEntity<?> addMenu(@PathVariable Long userId, @RequestBody MenuRequest request) {
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Menu menu = new Menu();
        menu.setName(request.getMenuName());
        menu.setDescription(request.getMenuDescription());
        menu.setUser(user);
        menu.setPrice(null);

        Menu savedMenu = menuRepository.save(menu);
        MenuResponse response = new MenuResponse(savedMenu.getId(), savedMenu.getName(), savedMenu.getDescription());
        return ResponseEntity.ok(response);

    }
}
