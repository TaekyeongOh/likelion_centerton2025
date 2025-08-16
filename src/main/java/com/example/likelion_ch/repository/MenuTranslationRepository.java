package com.example.likelion_ch.repository;
import com.example.likelion_ch.entity.Menu;
import com.example.likelion_ch.entity.MenuTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuTranslationRepository extends JpaRepository<MenuTranslation, Long> {

    List<MenuTranslation> findByMenu(Menu menu);

    Optional<MenuTranslation> findByMenuAndLang(Menu menu, MenuTranslation.Language lang);

    @Query("SELECT mt FROM MenuTranslation mt WHERE mt.menu.user.id = :userId AND mt.lang = :lang")
    List<MenuTranslation> findByUserIdAndLanguage(@Param("userId") Long userId, @Param("lang") MenuTranslation.Language lang);
}
