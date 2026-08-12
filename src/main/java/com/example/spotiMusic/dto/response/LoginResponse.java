package com.example.spotiMusic.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String message;
    private Long userId;
    private String email;
}