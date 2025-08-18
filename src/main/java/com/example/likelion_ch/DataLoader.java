package com.example.likelion_ch;

import com.example.likelion_ch.entity.Menu;
import com.example.likelion_ch.entity.OrderItem;
import com.example.likelion_ch.entity.SiteUser;
import com.example.likelion_ch.repository.MenuRepository;
import com.example.likelion_ch.repository.OrderItemRepository;
import com.example.likelion_ch.repository.SiteUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    private final SiteUserRepository userRepository;
    private final MenuRepository menuRepository;
    private final OrderItemRepository orderItemRepository;

    public DataLoader(SiteUserRepository userRepository, MenuRepository menuRepository,
                      OrderItemRepository orderItemRepository) {
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. 가게 생성
        SiteUser user = new SiteUser();
        user.setRestaurantName("테스트 가게");
        user.setRestaurantAddress("서울시 강남구");
        user.setShortDescription("맛있는 음식점");
        user.setLongDescription("이 가게는 다양한 메뉴를 제공합니다.");
        userRepository.save(user);

        // 2. 메뉴 생성
        Menu menu1 = new Menu();
        menu1.setMenuName("김치찌개");
        menu1.setPrice(new BigDecimal(8000));
        menu1.setDescription("한국 전통의 매콤한 김치찌개");
        menu1.setUser(user);
        menuRepository.save(menu1);

        Menu menu2 = new Menu();
        menu2.setMenuName("된장찌개");
        menu2.setDescription("구수하고 깊은 맛의 된장찌개");
        menu2.setPrice(new BigDecimal(7500));
        menu2.setUser(user);
        menuRepository.save(menu2);

        Menu menu3 = new Menu();
        menu3.setMenuName("비빔밥");
        menu3.setDescription("각종 야채와 고추장으로 비빈 비빔밥");
        menu3.setPrice(new BigDecimal(7000));
        menu3.setUser(user);
        menuRepository.save(menu3);

        // 3. 주문 생성
        OrderItem order1 = new OrderItem();
        order1.setMenu(menu1);
        order1.setQuantity(5);
        order1.setLanguage("KR");
        order1.setUser(user);
        orderItemRepository.save(order1);

        OrderItem order2 = new OrderItem();
        order2.setMenu(menu2);
        order2.setQuantity(3);
        order2.setLanguage("KR");
        order2.setUser(user);
        orderItemRepository.save(order2);

        OrderItem order3 = new OrderItem();
        order3.setMenu(menu3);
        order3.setQuantity(8);
        order3.setLanguage("KR");
        order3.setUser(user);
        orderItemRepository.save(order3);

        System.out.println("더미 데이터 로드 완료!");
    }
}
