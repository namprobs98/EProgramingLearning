package com.eprogramming.learning.service;

import com.eprogramming.learning.config.AppProperties;
import com.eprogramming.learning.dto.request.LoginRequest;
import com.eprogramming.learning.dto.request.RegisterRequest;
import com.eprogramming.learning.dto.response.AuthResponse;
import com.eprogramming.learning.dto.response.RegisterResponse;
import com.eprogramming.learning.entity.EmailVerificationToken;
import com.eprogramming.learning.entity.Role;
import com.eprogramming.learning.entity.User;
import com.eprogramming.learning.exception.ApiException;
import com.eprogramming.learning.repository.EmailVerificationTokenRepository;
import com.eprogramming.learning.repository.UserRepository;
import com.eprogramming.learning.security.JwtService;
import com.eprogramming.learning.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final EmailVerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AppProperties appProperties;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${app.mail.mock:false}")
    private boolean mockMail;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail().trim().toLowerCase())) {
            throw new ApiException("Email is already registered", HttpStatus.CONFLICT);
        }

        User user = User.builder()
                .name(request.getName().trim())
                .email(request.getEmail().trim().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .active(false)
                .build();
        userRepository.save(user);

        tokenRepository.deleteByUser(user);

        String tokenValue = UUID.randomUUID().toString();
        Instant expiry = Instant.now().plusSeconds(appProperties.getVerification().getExpiryMinutes() * 60L);

        EmailVerificationToken token = EmailVerificationToken.builder()
                .token(tokenValue)
                .user(user)
                .expiryDate(expiry)
                .build();
        tokenRepository.save(token);

        String link = appProperties.getFrontendUrl() + "/verify-email?token=" + tokenValue;
        mailService.sendVerificationEmail(user.getEmail(), link);

        if (mockMail) {
            return RegisterResponse.builder().verificationLink(link).build();
        }
        return RegisterResponse.builder().build();
    }

    @Transactional
    public void verifyEmail(String tokenValue) {
        EmailVerificationToken token = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new ApiException("Invalid verification token", HttpStatus.BAD_REQUEST));

        if (token.getExpiryDate().isBefore(Instant.now())) {
            tokenRepository.delete(token);
            throw new ApiException("Verification token has expired", HttpStatus.BAD_REQUEST);
        }

        User user = token.getUser();
        user.setActive(true);
        userRepository.save(user);
        tokenRepository.delete(token);
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail().trim().toLowerCase(),
                        request.getPassword()));

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtService.generateToken(principal);

        return AuthResponse.builder()
                .token(jwt)
                .type("Bearer")
                .userId(principal.getId())
                .name(userRepository.findById(principal.getId()).map(User::getName).orElse(""))
                .email(principal.getEmail())
                .role(principal.getRole())
                .build();
    }
}
