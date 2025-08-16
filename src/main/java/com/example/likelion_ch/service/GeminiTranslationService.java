package com.example.likelion_ch.service;

import com.example.likelion_ch.dto.TranslationRequest;
import com.example.likelion_ch.dto.TranslationResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiTranslationService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url:https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent}")
    private String apiUrl;

    public TranslationResponse translateMenu(TranslationRequest request) {
        try {
            String prompt = buildTranslationPrompt(request.getMenuName(), request.getMenuDescription());

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("contents", new Object[]{
                    Map.of("parts", new Object[]{
                            Map.of("text", prompt)
                    })
            });

            String response = webClient.post()
                    .uri(apiUrl + "?key=" + apiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return parseTranslationResponse(response);

        } catch (Exception e) {
            log.error("Gemini API 번역 실패: {}", e.getMessage(), e);
            // 번역 실패 시 기본 한국어 정보만 반환
            return createDefaultResponse(request);
        }
    }

    private String buildTranslationPrompt(String menuName, String menuDescription) {
        return String.format("""
            Translate the following Korean text into English, Japanese, and Chinese.
            Please respond in JSON format with the following structure:
            {
                "translations": {
                    "en": {"name": "English name", "description": "English description"},
                    "ja": {"name": "Japanese name", "description": "Japanese description"},
                    "zh": {"name": "Chinese name", "description": "Chinese description"}
                }
            }
            
            Menu Name: %s
            Menu Description: %s
            """, menuName, menuDescription);
    }

    private TranslationResponse parseTranslationResponse(String response) {
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode candidates = rootNode.path("candidates");

            if (candidates.isArray() && candidates.size() > 0) {
                JsonNode content = candidates.get(0).path("content");
                if (content.has("parts") && content.get("parts").isArray()) {
                    JsonNode parts = content.get("parts");
                    if (parts.size() > 0) {
                        String text = parts.get(0).path("text").asText();
                        // JSON 응답에서 실제 번역 데이터 추출
                        return parseTranslationText(text);
                    }
                }
            }

            throw new RuntimeException("Invalid response format from Gemini API");

        } catch (Exception e) {
            log.error("Gemini API 응답 파싱 실패: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to parse translation response", e);
        }
    }

    private TranslationResponse parseTranslationText(String text) {
        try {
            // JSON 텍스트에서 번역 데이터 추출
            JsonNode translationNode = objectMapper.readTree(text);
            return objectMapper.treeToValue(translationNode, TranslationResponse.class);
        } catch (Exception e) {
            log.error("번역 텍스트 파싱 실패: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to parse translation text", e);
        }
    }

    private TranslationResponse createDefaultResponse(TranslationRequest request) {
        Map<String, TranslationResponse.TranslationDto> translations = new HashMap<>();
        translations.put("en", TranslationResponse.TranslationDto.builder()
                .name(request.getMenuName())
                .description(request.getMenuDescription())
                .build());
        translations.put("ja", TranslationResponse.TranslationDto.builder()
                .name(request.getMenuName())
                .description(request.getMenuDescription())
                .build());
        translations.put("zh", TranslationResponse.TranslationDto.builder()
                .name(request.getMenuName())
                .description(request.getMenuDescription())
                .build());

        return TranslationResponse.builder()
                .translations(translations)
                .build();
    }
}
