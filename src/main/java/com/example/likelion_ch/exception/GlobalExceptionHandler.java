package com.example.likelion_ch.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("잘못된 요청: {}", e.getMessage());
        return createProblemDetail(HttpStatus.BAD_REQUEST, "잘못된 요청", e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ProblemDetail> handleRuntimeException(RuntimeException e) {
        log.error("런타임 오류: {}", e.getMessage(), e);
        return createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, Object> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        log.warn("검증 오류: {}", errors);
        return createProblemDetail(HttpStatus.BAD_REQUEST, "입력값 검증 실패", "입력값을 확인해주세요", errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("잘못된 JSON 형식: {}", e.getMessage());
        return createProblemDetail(HttpStatus.BAD_REQUEST, "잘못된 요청 형식", "JSON 형식을 확인해주세요");
    }

    // 번역 서비스 관련 예외 처리
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ProblemDetail> handleWebClientResponseException(WebClientResponseException e) {
        log.error("외부 API 호출 오류: {}", e.getMessage(), e);
        return createProblemDetail(HttpStatus.BAD_GATEWAY, "번역 서비스 오류", "외부 번역 서비스에 문제가 발생했습니다");
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ProblemDetail> handleResourceAccessException(ResourceAccessException e) {
        log.error("외부 API 연결 오류: {}", e.getMessage(), e);
        return createProblemDetail(HttpStatus.GATEWAY_TIMEOUT, "번역 서비스 연결 오류", "번역 서비스 연결에 실패했습니다");
    }

    private ResponseEntity<ProblemDetail> createProblemDetail(HttpStatus status, String title, String detail) {
        return createProblemDetail(status, title, detail, null);
    }

    private ResponseEntity<ProblemDetail> createProblemDetail(HttpStatus status, String title, String detail, Map<String, Object> additionalData) {
        ProblemDetail problem = ProblemDetail.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .title(title)
                .detail(detail)
                .additionalData(additionalData)
                .build();

        return ResponseEntity.status(status).body(problem);
    }


    // RFC 7807 Problem Details 표준에 맞는 응답 클래스
    @lombok.Data
    @lombok.Builder
    public static class ProblemDetail {
        private Instant timestamp;
        private int status;
        private String error;
        private String title;
        private String detail;
        private Map<String, Object> additionalData;
    }
}
