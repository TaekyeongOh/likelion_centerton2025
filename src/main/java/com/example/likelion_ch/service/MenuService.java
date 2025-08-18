package com.example.likelion_ch.service;

import com.example.likelion_ch.dto.MenuInfo;
import com.example.likelion_ch.dto.MenuRequest;
import com.example.likelion_ch.dto.MenuResponse;
import com.example.likelion_ch.dto.TopMenuResponse;
import com.example.likelion_ch.dto.StoreResponse;
import com.example.likelion_ch.dto.CreateOrderRequest;
import com.example.likelion_ch.dto.TranslationRequest;
import com.example.likelion_ch.entity.Menu;
import com.example.likelion_ch.entity.OrderItem;
import com.example.likelion_ch.entity.SiteUser;
import com.example.likelion_ch.repository.MenuRepository;
import com.example.likelion_ch.repository.OrderItemRepository;
import com.example.likelion_ch.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final SiteUserRepository siteUserRepository;
    private final OrderItemRepository orderItemRepository;
    private final GeminiTranslationService translationService;

    // 주문 생성
    public void createOrder(CreateOrderRequest request) {
        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        OrderItem orderItem = new OrderItem();
        orderItem.setMenu(menu);
        orderItem.setQuantity(request.getQuantity());
        orderItem.setPrice(menu.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));

        orderItemRepository.save(orderItem);
    }

    // 가게 정보 + 메뉴 리스트
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

    // top 메뉴 조회
    public TopMenuResponse getTopMenu(Long userId) {
        List<MenuInfo> top3 = orderItemRepository.findTopMenu(userId, Pageable.ofSize(3));
        return new TopMenuResponse(top3);
    }

    // 언어 기반 top 메뉴 조회
    public TopMenuResponse getTopMenuByLanguage(Long userId, String lang) {
        List<MenuInfo> top3ByLang = orderItemRepository.findTopMenuByLanguage(userId, lang, Pageable.ofSize(3));
        return new TopMenuResponse(top3ByLang);
    }

    // 전체 메뉴 조회
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

    // 전체 메뉴 조회 (MenuResponse)
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

    // 단일 메뉴 조회
    public MenuResponse getMenuById(Long userId, Integer userMenuId) {
        Menu menu = menuRepository.findByUserMenuIdAndUser_Id(userMenuId, userId)
                .orElseThrow(() -> new RuntimeException("사용자 소유의 메뉴가 아닙니다."));

        return MenuResponse.builder()
                .id(menu.getId())
                .userMenuId(menu.getUserMenuId())
                .nameKo(menu.getMenuName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .userId(menu.getUser() != null ? menu.getUser().getId() : null)
                .build();
    }

    // 메뉴 등록
    public MenuResponse createMenu(Long userId, MenuRequest request) {
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Integer maxId = menuRepository.findMaxUserMenuId(user);
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

    // 메뉴 수정
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

    // 메뉴 삭제
    public void deleteMenu(Long userId, Integer userMenuId) {
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Menu menu = menuRepository.findByUserAndUserMenuId(user, userMenuId)
                .orElseThrow(() -> new RuntimeException("사용자 소유의 메뉴가 아닙니다."));

        menuRepository.delete(menu);
    }

    // 언어별 메뉴 조회
    public List<MenuInfo> getMenusByLanguage(Long userId, String langCode) {
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<Menu> menuList = menuRepository.findByUser(user);
        List<MenuInfo> result = new ArrayList<>();

        for (Menu menu : menuList) {
            String translatedName = menu.getNameKo();
            String translatedDescription = menu.getDescription();

            if (!"ko".equals(langCode)) {
                String userApiKey = user.getGeminiApiKey();
                TranslationRequest requestName = new TranslationRequest(menu.getNameKo());
                TranslationRequest requestDesc = new TranslationRequest(menu.getDescription());

                switch (langCode) {
                    case "en":
                        translatedName = translationService.translateToEnglish(requestName, userApiKey).getTranslatedText();
                        translatedDescription = translationService.translateToEnglish(requestDesc, userApiKey).getTranslatedText();
                        break;
                    case "ja":
                        translatedName = translationService.translateToJapanese(requestName, userApiKey).getTranslatedText();
                        translatedDescription = translationService.translateToJapanese(requestDesc, userApiKey).getTranslatedText();
                        break;
                    case "ch":
                        translatedName = translationService.translateToChinese(requestName, userApiKey).getTranslatedText();
                        translatedDescription = translationService.translateToChinese(requestDesc, userApiKey).getTranslatedText();
                        break;
                }
            }

            result.add(new MenuInfo(translatedName, translatedDescription, menu.getPrice()));
        }

        return result;
    }

}

