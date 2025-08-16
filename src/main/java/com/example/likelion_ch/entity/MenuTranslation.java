package com.example.likelion_ch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menu_translation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu; // 메뉴 참조

    @Enumerated(EnumType.STRING)
    @Column(name = "lang", nullable = false)
    private Language lang; // 언어 코드 (en, ja, zh)

    @Column(name = "translated_name", nullable = false)
    private String translatedName; // 번역된 메뉴명

    @Column(name = "translated_description", nullable = false)
    private String translatedDescription; // 번역된 메뉴 설명

    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;

    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        updatedAt = java.time.LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now();
    }

    public enum Language {
        EN("en", "English"),
        JA("ja", "Japanese"),
        ZH("zh", "Chinese");

        private final String code;
        private final String displayName;

        Language(String code, String displayName) {
            this.code = code;
            this.displayName = displayName;
        }

        public String getCode() {
            return code;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static Language fromCode(String code) {
            for (Language lang : values()) {
                if (lang.code.equals(code)) {
                    return lang;
                }
            }
            throw new IllegalArgumentException("Unknown language code: " + code);
        }
    }
}
