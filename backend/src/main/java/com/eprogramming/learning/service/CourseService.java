package com.eprogramming.learning.service;

import com.eprogramming.learning.dto.response.CourseResponse;
import com.eprogramming.learning.dto.response.LessonResponse;
import com.eprogramming.learning.entity.Course;
import com.eprogramming.learning.entity.Lesson;
import com.eprogramming.learning.exception.ApiException;
import com.eprogramming.learning.repository.CourseRepository;
import com.eprogramming.learning.repository.LessonRepository;
import com.eprogramming.learning.util.LocaleUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    public List<CourseResponse> findAllPublished(String acceptLanguage) {
        return courseRepository.findAllPublishedWithLanguage().stream()
                .map(c -> toCourseResponse(c, acceptLanguage))
                .toList();
    }

    public List<CourseResponse> findByLanguage(Long languageId, String acceptLanguage) {
        return courseRepository.findByLanguageIdAndPublishedTrue(languageId).stream()
                .map(c -> toCourseResponse(c, acceptLanguage))
                .toList();
    }

    public CourseResponse findById(Long id, String acceptLanguage) {
        Course course = courseRepository.findPublishedByIdWithLanguage(id)
                .orElseThrow(() -> new ApiException("Course not found", HttpStatus.NOT_FOUND));
        return toCourseResponse(course, acceptLanguage);
    }

    public List<LessonResponse> findLessonsByCourse(Long courseId, String acceptLanguage) {
        if (courseRepository.findPublishedByIdWithLanguage(courseId).isEmpty()) {
            throw new ApiException("Course not found", HttpStatus.NOT_FOUND);
        }
        return lessonRepository.findByCourseIdPublished(courseId).stream()
                .map(l -> toLessonResponse(l, acceptLanguage, false))
                .toList();
    }

    public LessonResponse findLessonById(Long id, String acceptLanguage) {
        Lesson lesson = lessonRepository.findPublishedById(id)
                .orElseThrow(() -> new ApiException("Lesson not found", HttpStatus.NOT_FOUND));
        return toLessonResponse(lesson, acceptLanguage, true);
    }

    private CourseResponse toCourseResponse(Course course, String acceptLanguage) {
        boolean en = LocaleUtils.isEnglish(acceptLanguage);
        return CourseResponse.builder()
                .id(course.getId())
                .languageId(course.getLanguage().getId())
                .languageName(course.getLanguage().getName())
                .languageCode(course.getLanguage().getCode())
                .title(en ? course.getTitleEn() : course.getTitleVi())
                .summary(en ? course.getSummaryEn() : course.getSummaryVi())
                .build();
    }

    private LessonResponse toLessonResponse(Lesson lesson, String acceptLanguage, boolean includeContent) {
        boolean en = LocaleUtils.isEnglish(acceptLanguage);
        return LessonResponse.builder()
                .id(lesson.getId())
                .courseId(lesson.getCourse().getId())
                .title(en ? lesson.getTitleEn() : lesson.getTitleVi())
                .content(includeContent ? (en ? lesson.getContentEn() : lesson.getContentVi()) : null)
                .codeTemplate(includeContent ? lesson.getCodeTemplate() : null)
                .sequence(lesson.getSequence())
                .build();
    }
}
