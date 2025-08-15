package com.example.likelion_ch.service;

import com.example.likelion_ch.dto.StoreResponse;
import com.example.likelion_ch.entity.Menu;
import com.example.likelion_ch.entity.SiteUser;
import com.example.likelion_ch.repository.MenuRepository;
import com.example.likelion_ch.repository.SiteUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final SiteUserRepository siteUserRepository;

    public MenuService(MenuRepository menuRepository, SiteUserRepository siteUserRepository) {
        this.menuRepository = menuRepository;
        this.siteUserRepository = siteUserRepository;
    }

    public StoreResponse getStoreWithMenu(Long userId) {
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        List<Menu> menuList = menuRepository.findByUser(user);

        return new StoreResponse(
                user.getRestaurantName(),
                user.getRestaurantAddress(),
                user.getShortDescription(),
                user.getLongDescription(),
                menuList
        );
    }
}