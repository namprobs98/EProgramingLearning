package com.eprogramming.learning.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Long id;
    private Long languageId;
    private String languageName;
    private String languageCode;
    private String title;
    private String summary;
}
