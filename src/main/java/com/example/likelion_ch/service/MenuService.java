package com.example.likelion_ch.service;

import com.example.likelion_ch.dto.CreateOrderRequest;
import com.example.likelion_ch.dto.StoreResponse;
import com.example.likelion_ch.dto.TopMenuResponse;
import com.example.likelion_ch.entity.Menu;
import com.example.likelion_ch.entity.SiteUser;
import com.example.likelion_ch.repository.MenuRepository;
import com.example.likelion_ch.repository.SiteUserRepository;
import com.example.likelion_ch.repository.OrderItemRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.likelion_ch.entity.OrderItem;
import com.example.likelion_ch.dto.StoreMenusResponse;
import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final SiteUserRepository siteUserRepository;
    private final OrderItemRepository orderItemRepository;

    public MenuService(MenuRepository menuRepository, SiteUserRepository siteUserRepository, OrderItemRepository orderItemRepository) {
        this.menuRepository = menuRepository;
        this.siteUserRepository = siteUserRepository;
        this.orderItemRepository = orderItemRepository;
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

    // 베스트 메뉴 TOP3
    public TopMenuResponse getBestMenus(Long userId) {
        List<TopMenuResponse.MenuInfo> top3 = orderItemRepository.findTopMenu(userId, PageRequest.of(0, 3));
        return new TopMenuResponse(top3);
    }

    // 언어 기반 추천 메뉴 TOP3
    public TopMenuResponse getRecommendedMenus(Long userId, String lang) {
        List<TopMenuResponse.MenuInfo> top3 = orderItemRepository.findTopMenuByLanguage(userId, lang, PageRequest.of(0, 3));
        return new TopMenuResponse(top3);
    }


    public void createOrder(CreateOrderRequest request) {
        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        OrderItem orderItem = new OrderItem();
        orderItem.setMenu(menu);
        orderItem.setQuantity(request.getQuantity());
        orderItem.setLanguage(request.getLanguage());
        orderItem.setUser(menu.getUser());

        orderItemRepository.save(orderItem);
    }

    // MenuService.java
    public StoreMenusResponse getAllMenus(Long userId) {
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<StoreMenusResponse.MenuSummary> menuList = menuRepository.findByUser(user)
                .stream()
                .map(menu -> new StoreMenusResponse.MenuSummary(menu.getName(), menu.getDescription()))
                .toList();

        return new StoreMenusResponse(menuList);
    }



}
