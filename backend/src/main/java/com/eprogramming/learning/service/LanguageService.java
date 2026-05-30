package com.eprogramming.learning.service;

import com.eprogramming.learning.dto.response.LanguageResponse;
import com.eprogramming.learning.entity.Language;
import com.eprogramming.learning.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    public List<LanguageResponse> findAll() {
        return languageRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private LanguageResponse toResponse(Language language) {
        return LanguageResponse.builder()
                .id(language.getId())
                .name(language.getName())
                .code(language.getCode())
                .build();
    }
}
