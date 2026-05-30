package com.eprogramming.learning.controller;

import com.eprogramming.learning.dto.response.ApiResponse;
import com.eprogramming.learning.dto.response.LanguageResponse;
import com.eprogramming.learning.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<LanguageResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("OK", languageService.findAll()));
    }
}
