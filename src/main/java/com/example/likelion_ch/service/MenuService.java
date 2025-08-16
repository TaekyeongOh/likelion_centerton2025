package com.example.likelion_ch.service;

import com.example.likelion_ch.dto.*;
import com.example.likelion_ch.entity.Menu;
import com.example.likelion_ch.entity.MenuTranslation;
import com.example.likelion_ch.entity.SiteUser;
import com.example.likelion_ch.repository.MenuRepository;
import com.example.likelion_ch.repository.MenuTranslationRepository;
import com.example.likelion_ch.repository.SiteUserRepository;
import com.example.likelion_ch.repository.OrderItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.likelion_ch.entity.OrderItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final SiteUserRepository siteUserRepository;
    private final OrderItemRepository orderItemRepository;
    private final GeminiTranslationService geminiTranslationService;
    private final MenuTranslationRepository menuTranslationRepository;

    public MenuService(MenuRepository menuRepository,
                       SiteUserRepository siteUserRepository,
                       OrderItemRepository orderItemRepository,
                       GeminiTranslationService geminiTranslationService,
                       MenuTranslationRepository menuTranslationRepository) {
        this.menuRepository = menuRepository;
        this.siteUserRepository = siteUserRepository;
        this.orderItemRepository = orderItemRepository;
        this.geminiTranslationService = geminiTranslationService;
        this.menuTranslationRepository = menuTranslationRepository;
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

    @Transactional
    public MenuResponse registerMenu(Long userId, MenuRequest request) {
        // 1. SiteUser 존재 여부 확인
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userId));

        // 2. Menu 엔티티 생성 및 저장 (기본 한국어 데이터)
        Menu menu = Menu.builder()
                .name(request.getMenuName())
                .description(request.getMenuDescription())
                .price(request.getMenuPrice())
                .user(user)
                .build();

        Menu savedMenu = menuRepository.save(menu);

        // 3. Gemini API 호출하여 번역
        try {
            TranslationRequest translationRequest = TranslationRequest.builder()
                    .menuName(request.getMenuName())
                    .menuDescription(request.getMenuDescription())
                    .build();

            TranslationResponse translationResponse = geminiTranslationService.translateMenu(translationRequest);

            // 4. 번역 결과를 MenuTranslation 테이블에 저장
            saveTranslations(savedMenu, translationResponse);

        } catch (Exception e) {
            log.error("메뉴 번역 저장 실패: {}", e.getMessage(), e);
            // 번역 실패 시에도 메뉴는 저장됨
        }

        return MenuResponse.builder()
                .menuId(savedMenu.getId())
                .menuName(savedMenu.getName())
                .menuDescription(savedMenu.getDescription())
                .menuPrice(savedMenu.getPrice())
                .build();
    }

    private void saveTranslations(Menu menu, TranslationResponse translationResponse) {
        if (translationResponse.getTranslations() != null) {
            translationResponse.getTranslations().forEach((langCode, translation) -> {
                try {
                    MenuTranslation.Language language = MenuTranslation.Language.fromCode(langCode);

                    MenuTranslation menuTranslation = MenuTranslation.builder()
                            .menu(menu)
                            .lang(language)
                            .translatedName(translation.getName())
                            .translatedDescription(translation.getDescription())
                            .build();

                    menuTranslationRepository.save(menuTranslation);

                } catch (IllegalArgumentException e) {
                    log.warn("지원하지 않는 언어 코드: {}", langCode);
                }
            });
        }
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> getMenusByLanguage(Long userId, String langCode) {
        // 1. SiteUser 존재 여부 확인
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userId));

        try {
            MenuTranslation.Language language = MenuTranslation.Language.fromCode(langCode);

            // 2. 해당 언어의 번역된 메뉴 정보 조회
            List<MenuTranslation> translations = menuTranslationRepository.findByUserIdAndLanguage(userId, language);

            if (!translations.isEmpty()) {
                // 번역된 데이터가 있으면 번역된 정보 반환
                return translations.stream()
                        .map(this::mapToMenuResponse)
                        .collect(Collectors.toList());
            }

        } catch (IllegalArgumentException e) {
            log.warn("지원하지 않는 언어 코드: {}", langCode);
        }

        // 3. 해당 언어가 없으면 기본 한국어 데이터 반환
        List<Menu> menus = menuRepository.findByUser(user);
        return menus.stream()
                .map(this::mapToMenuResponse)
                .collect(Collectors.toList());
    }

    private MenuResponse mapToMenuResponse(Menu menu) {
        return MenuResponse.builder()
                .menuId(menu.getId())
                .menuName(menu.getName())
                .menuDescription(menu.getDescription())
                .menuPrice(menu.getPrice())
                .build();
    }

    private MenuResponse mapToMenuResponse(MenuTranslation translation) {
        return MenuResponse.builder()
                .menuId(translation.getMenu().getId())
                .menuName(translation.getTranslatedName())
                .menuDescription(translation.getTranslatedDescription())
                .menuPrice(translation.getMenu().getPrice())
                .build();
    }

}
