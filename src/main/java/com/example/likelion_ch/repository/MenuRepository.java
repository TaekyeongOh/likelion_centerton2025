package com.example.likelion_ch.repository;

import com.example.likelion_ch.entity.Menu;
import com.example.likelion_ch.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByUser(SiteUser user);

    @Query("SELECT m FROM Menu m WHERE m.user = :user")
    List<Menu> findAllByUserId(@Param("user") SiteUser user);
}
