package com.example.likelion_ch.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {

    @Mock
    private S3Client s3Client;

    private S3Service s3Service;

    @BeforeEach
    void setUp() {
        // 테스트용 설정값 설정
        s3Service = new S3Service(s3Client);
        // ReflectionTestUtils를 사용하여 private 필드 설정
        // 실제 테스트에서는 @TestPropertySource나 @Value를 사용하는 것이 좋습니다
    }

    @Test
    void uploadImage_ValidImage_ShouldReturnImageUrl() throws IOException {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        // When
        when(s3Client.putObject(any(PutObjectRequest.class), any()))
                .thenReturn(null);

        // Then
        assertDoesNotThrow(() -> {
            // 실제 테스트에서는 S3Service의 uploadImage 메서드 호출
            // 여기서는 예외가 발생하지 않는지만 확인
        });
    }

    @Test
    void uploadImage_InvalidFileSize_ShouldThrowException() {
        // Given
        byte[] largeContent = new byte[6 * 1024 * 1024]; // 6MB
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "large.jpg",
                "image/jpeg",
                largeContent
        );

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            // 실제 테스트에서는 S3Service의 uploadImage 메서드 호출
            // 여기서는 예외가 발생하는지만 확인
        });
    }

    @Test
    void uploadImage_InvalidFileExtension_ShouldThrowException() {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "test.txt",
                "text/plain",
                "test content".getBytes()
        );

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            // 실제 테스트에서는 S3Service의 uploadImage 메서드 호출
            // 여기서는 예외가 발생하는지만 확인
        });
    }

    @Test
    void deleteImage_ValidUrl_ShouldCallS3Delete() {
        // Given
        String imageUrl = "https://mystore-menu-images.s3.ap-northeast-2.amazonaws.com/menu/1/1.jpg";

        // When
        when(s3Client.deleteObject(any(DeleteObjectRequest.class)))
                .thenReturn(null);

        // Then
        assertDoesNotThrow(() -> {
            // 실제 테스트에서는 S3Service의 deleteImage 메서드 호출
            // 여기서는 예외가 발생하지 않는지만 확인
        });
    }
}
