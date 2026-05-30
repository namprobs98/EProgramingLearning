package com.eprogramming.learning.controller;

import com.eprogramming.learning.dto.response.ApiResponse;
import com.eprogramming.learning.dto.response.CourseResponse;
import com.eprogramming.learning.dto.response.LessonResponse;
import com.eprogramming.learning.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> findAll(
            @RequestHeader(value = "Accept-Language", defaultValue = "vi") String acceptLanguage,
            @RequestParam(required = false) Long languageId) {

        List<CourseResponse> courses = languageId != null
                ? courseService.findByLanguage(languageId, acceptLanguage)
                : courseService.findAllPublished(acceptLanguage);

        return ResponseEntity.ok(ApiResponse.ok("OK", courses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> findById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "vi") String acceptLanguage) {
        return ResponseEntity.ok(ApiResponse.ok("OK", courseService.findById(id, acceptLanguage)));
    }

    @GetMapping("/{id}/lessons")
    public ResponseEntity<ApiResponse<List<LessonResponse>>> findLessons(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "vi") String acceptLanguage) {
        return ResponseEntity.ok(ApiResponse.ok("OK", courseService.findLessonsByCourse(id, acceptLanguage)));
    }
}
