package com.example.spotiMusic.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email must be in a valid format.")
    private String email;

    @NotBlank(message = "Password cannot be blank.")
    private String password;
}