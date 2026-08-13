package com.example.spotiMusic.service;

import com.example.spotiMusic.dto.request.LoginRequest;
import com.example.spotiMusic.dto.request.RegisterRequest;
import com.example.spotiMusic.dto.response.LoginResponse;
import com.example.spotiMusic.dto.response.RegisterResponse;
import com.example.spotiMusic.entity.UserEntity;
import com.example.spotiMusic.enums.Role;
import com.example.spotiMusic.exception.EmailAlreadyExistsException;
import com.example.spotiMusic.exception.InvalidCredentialsException;
import com.example.spotiMusic.repository.UserRepository;
import com.example.spotiMusic.security.JwtService;
import com.example.spotiMusic.service.iservice.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        // 1. Check if email is already in use
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("This email address is already in use.");
        }

        // 2. Create entity and set default values
        UserEntity userEntity = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER) // Default role
                .active(true)
                .build();

        // 3. Save to database
        UserEntity savedUserEntity = userRepository.save(userEntity);

        // 4. Convert to Response DTO
        return RegisterResponse.builder()
                .id(savedUserEntity.getId())
                .firstName(savedUserEntity.getFirstName())
                .lastName(savedUserEntity.getLastName())
                .email(savedUserEntity.getEmail())
                .role(savedUserEntity.getRole())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // 1. Find user by email, throw 401 if not found
        UserEntity userEntity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password."));

        // 2. Verify password with PasswordEncoder, throw 401 if incorrect
        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        // 3. Generate JWT if credentials are correct
        String jwtToken = jwtService.generateToken(userEntity.getEmail());

        // 4. Return response in the required format
        return LoginResponse.builder()
                .accessToken(jwtToken)
                .tokenType("Bearer")
                .build();
    }
}