package com.eprogramming.learning.controller;

import com.eprogramming.learning.dto.response.ApiResponse;
import com.eprogramming.learning.dto.response.LessonResponse;
import com.eprogramming.learning.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LessonResponse>> findById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "vi") String acceptLanguage) {
        return ResponseEntity.ok(ApiResponse.ok("OK", courseService.findLessonById(id, acceptLanguage)));
    }
}
