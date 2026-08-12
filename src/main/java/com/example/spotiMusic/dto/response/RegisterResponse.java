package com.example.spotiMusic.dto.response;

import com.example.spotiMusic.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}