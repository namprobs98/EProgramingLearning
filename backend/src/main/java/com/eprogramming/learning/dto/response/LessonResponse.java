package com.eprogramming.learning.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponse {
    private Long id;
    private Long courseId;
    private String title;
    private String content;
    private String codeTemplate;
    private Integer sequence;
}
