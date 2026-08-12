package com.example.spotiMusic.service.iservice;

import com.example.spotiMusic.dto.request.LoginRequest;
import com.example.spotiMusic.dto.request.RegisterRequest;
import com.example.spotiMusic.dto.response.LoginResponse;
import com.example.spotiMusic.dto.response.RegisterResponse;

public interface IAuthService {
    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}