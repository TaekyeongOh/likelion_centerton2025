package com.example.likelion_ch.repository;

import com.example.likelion_ch.entity.Menu;
import com.example.likelion_ch.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByUser(SiteUser user);
}
