package com.example.likelion_ch.repository;

import com.example.likelion_ch.dto.TopMenuResponse;
import com.example.likelion_ch.entity.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    //top3
    @Query("SELECT new com.example.likelion_ch.dto.TopMenuResponse$MenuInfo(m.name, m.description) " +
            "FROM OrderItem oi JOIN oi.menu m " +
            "WHERE oi.user.id = :userId " +
            "GROUP BY m.name, m.description ORDER BY SUM(oi.quantity) DESC")
    List<TopMenuResponse.MenuInfo> findTopMenu(@Param("userId") Long userId, Pageable pageable);
    // 언어 기반
    @Query("SELECT new com.example.likelion_ch.dto.TopMenuResponse$MenuInfo(m.name, m.description) " +
            "FROM OrderItem oi JOIN oi.menu m " +
            "WHERE oi.user.id = :userId AND oi.language = :lang " +
            "GROUP BY m.name, m.description ORDER BY SUM(oi.quantity) DESC")
    List<TopMenuResponse.MenuInfo> findTopMenuByLanguage(@Param("userId") Long userId,
                                                         @Param("lang") String lang,
                                                         Pageable pageable);

}