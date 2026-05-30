package com.eprogramming.learning.controller;

import com.eprogramming.learning.config.AppProperties;
import com.eprogramming.learning.dto.response.ApiResponse;
import com.eprogramming.learning.exception.ApiException;
import com.eprogramming.learning.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dev/mail")
@RequiredArgsConstructor
public class MailTestController {

    private final MailService mailService;
    private final AppProperties appProperties;

    @Value("${app.mail.allow-test-endpoint:false}")
    private boolean allowTest;

    @Value("${app.mail.mock:false}")
    private boolean mockMail;

    @PostMapping("/test")
    public ResponseEntity<ApiResponse<Void>> sendTest(@RequestParam String to) {
        if (!allowTest) {
            throw new ApiException("Mail test endpoint disabled", HttpStatus.NOT_FOUND);
        }
        if (mockMail) {
            throw new ApiException("MAIL_MOCK=true — dat MAIL_MOCK=false trong .env", HttpStatus.BAD_REQUEST);
        }
        if (!StringUtils.hasText(to)) {
            throw new ApiException("Parameter 'to' is required", HttpStatus.BAD_REQUEST);
        }
        mailService.sendVerificationEmail(to, appProperties.getFrontendUrl() + "/verify-email?token=test-token");
        return ResponseEntity.ok(ApiResponse.ok("Test email sent to " + to));
    }
}
