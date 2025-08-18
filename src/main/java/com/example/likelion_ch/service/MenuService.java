package com.example.likelion_ch.service;

import com.example.likelion_ch.dto.*;
import com.example.likelion_ch.entity.Menu;
import com.example.likelion_ch.entity.OrderItem;
import com.example.likelion_ch.entity.SiteUser;
import com.example.likelion_ch.repository.MenuRepository;
import com.example.likelion_ch.repository.OrderItemRepository;
import com.example.likelion_ch.repository.SiteUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final SiteUserRepository siteUserRepository;
    private final OrderItemRepository orderItemRepository;

    public MenuService(MenuRepository menuRepository,
                       SiteUserRepository siteUserRepository,
                       OrderItemRepository orderItemRepository) {
        this.menuRepository = menuRepository;
        this.siteUserRepository = siteUserRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // 주문 생성
    public void createOrder(CreateOrderRequest request) {
        // 1. 메뉴 조회
        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        // 2. 주문 항목 생성
        OrderItem orderItem = new OrderItem();
        orderItem.setMenu(menu);
        orderItem.setQuantity(request.getQuantity());
        orderItem.setPrice(menu.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));

        // 3. DB 저장
        orderItemRepository.save(orderItem);
    }

    // 1. 가게 정보 + 메뉴 리스트
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

    // 2. top 메뉴 조회
    public TopMenuResponse getTopMenu(Long userId) {
        List<MenuInfo> top3 = orderItemRepository.findTopMenu(userId, Pageable.ofSize(3));
        return new TopMenuResponse(top3);
    }

    // 3. 언어 기반 top 메뉴 조회
    public TopMenuResponse getTopMenuByLanguage(Long userId, String lang) {
        List<MenuInfo> top3ByLang = orderItemRepository.findTopMenuByLanguage(userId, lang, Pageable.ofSize(3));
        return new TopMenuResponse(top3ByLang);
    }

    // 4. 전체 메뉴 조회
    public List<MenuInfo> getAllMenusForStore(Long userId) {
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        List<Menu> menuList = menuRepository.findByUser(user);

        return menuList.stream()
                .map(menu -> new MenuInfo(
                        menu.getMenuName(),
                        menu.getDescription(),
                        menu.getPrice()
                ))
                .toList();
    }

    // --- 전체 메뉴 조회 ---
    public List<MenuResponse> getMenusByUser(Long userId) {
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return menuRepository.findByUser(user).stream()
                .map(menu -> MenuResponse.builder()
                        .id(menu.getId())
                        .nameKo(menu.getNameKo())
                        .description(menu.getDescription())
                        .price(menu.getPrice())
                        .userId(user.getId())
                        .createdAt(menu.getCreatedAt())
                        .updatedAt(menu.getUpdatedAt())
                        .build())
                .toList();
    }

    // --- 단일 메뉴 조회 ---
    public MenuResponse getMenuById(Long userId, Long menuId) {
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        if (!menu.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("사용자 소유의 메뉴가 아닙니다.");
        }

        return MenuResponse.builder()
                .id(menu.getId())
                .nameKo(menu.getNameKo())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .userId(user.getId())
                .createdAt(menu.getCreatedAt())
                .updatedAt(menu.getUpdatedAt())
                .build();
    }

    // --- 메뉴 등록 ---
    public MenuResponse createMenu(Long userId, MenuRequest request) {
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Integer maxId = menuRepository.findMaxUserMenuId(user); // 현재 사용자 최대 메뉴 ID
        Menu menu = new Menu();
        menu.setUser(user);
        menu.setUserMenuId(maxId + 1);
        menu.setNameKo(request.getMenuName());
        menu.setDescription(request.getMenuDescription());
        menu.setPrice(request.getMenuPrice());

        menuRepository.save(menu);

        return MenuResponse.builder()
                .id(menu.getId())
                .userMenuId(menu.getUserMenuId())
                .nameKo(menu.getNameKo())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .userId(user.getId())
                .build();
    }

    // --- 메뉴 수정 ---
    public MenuResponse updateMenu(Long userId, Integer userMenuId, MenuRequest request) {
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Menu menu = menuRepository.findByUserAndUserMenuId(user, userMenuId)
                .orElseThrow(() -> new RuntimeException("사용자 소유의 메뉴가 아닙니다."));

        menu.setNameKo(request.getMenuName());
        menu.setDescription(request.getMenuDescription());
        menu.setPrice(request.getMenuPrice());

        menuRepository.save(menu);

        return MenuResponse.builder()
                .id(menu.getId())
                .userMenuId(menu.getUserMenuId())
                .nameKo(menu.getNameKo())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .userId(user.getId())
                .build();
    }

    // --- 메뉴 삭제 ---
    public void deleteMenu(Long userId, Integer userMenuId) {
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Menu menu = menuRepository.findByUserAndUserMenuId(user, userMenuId)
                .orElseThrow(() -> new RuntimeException("사용자 소유의 메뉴가 아닙니다."));

        menuRepository.delete(menu);
    }
}
