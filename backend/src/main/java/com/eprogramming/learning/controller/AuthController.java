package com.eprogramming.learning.controller;

import com.eprogramming.learning.dto.request.LoginRequest;
import com.eprogramming.learning.dto.request.RegisterRequest;
import com.eprogramming.learning.dto.response.ApiResponse;
import com.eprogramming.learning.dto.response.AuthResponse;
import com.eprogramming.learning.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(ApiResponse.ok(
                "Registration successful. Please check your email to activate your account."));
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<Void>> verify(@RequestParam String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok(ApiResponse.ok("Email verified successfully. You can now log in."));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.ok("Login successful", response));
    }
}
