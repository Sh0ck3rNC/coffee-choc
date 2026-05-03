package com.miage2026.coffeechoc.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .status("success")
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .status("success")
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
    }
}