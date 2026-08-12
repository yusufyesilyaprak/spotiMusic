package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.request.LoginRequest;
import com.example.spotiMusic.dto.request.RegisterRequest;
import com.example.spotiMusic.dto.response.LoginResponse;
import com.example.spotiMusic.dto.response.RegisterResponse;
import com.example.spotiMusic.entity.User;
import com.example.spotiMusic.enums.Role;
import com.example.spotiMusic.exception.EmailAlreadyExistsException;
import com.example.spotiMusic.exception.InvalidCredentialsException;
import com.example.spotiMusic.repository.UserRepository;
import com.example.spotiMusic.service.iservice.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("This email address is already in use.");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .active(true)
                .build();

        User savedUser = userRepository.save(user);

        return RegisterResponse.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        return LoginResponse.builder()
                .message("Login successful")
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }
}