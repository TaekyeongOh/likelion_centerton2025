package com.example.likelion_ch.repository;

import com.example.likelion_ch.dto.MenuInfo;
import com.example.likelion_ch.entity.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT new com.example.likelion_ch.dto.MenuInfo(m.nameKo, m.description, m.price) " +
            "FROM OrderItem oi " +
            "JOIN oi.menu m " +
            "WHERE oi.menu.user.id = :userId " +
            "GROUP BY m.id " +
            "ORDER BY COUNT(oi.id) DESC")
    List<MenuInfo> findTopMenu(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT new com.example.likelion_ch.dto.MenuInfo(m.nameKo, m.description, m.price) " +
            "FROM OrderItem oi " +
            "JOIN oi.menu m " +
            "WHERE oi.menu.user.id = :userId AND m.language = :lang " +
            "GROUP BY m.id " +
            "ORDER BY COUNT(oi.id) DESC")
    List<MenuInfo> findTopMenuByLanguage(@Param("userId") Long userId,
                                         @Param("lang") String lang,
                                         Pageable pageable);
}
