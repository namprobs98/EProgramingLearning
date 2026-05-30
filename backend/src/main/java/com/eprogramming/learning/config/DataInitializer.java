package com.eprogramming.learning.config;

import com.eprogramming.learning.entity.Course;
import com.eprogramming.learning.entity.Language;
import com.eprogramming.learning.entity.Lesson;
import com.eprogramming.learning.repository.CourseRepository;
import com.eprogramming.learning.repository.LanguageRepository;
import com.eprogramming.learning.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final LanguageRepository languageRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (courseRepository.count() > 0) {
            return;
        }

        Language java = languageRepository.findByCode("java").orElse(null);
        if (java == null) {
            return;
        }

        Course course = Course.builder()
                .language(java)
                .titleVi("Java cơ bản cho người mới bắt đầu")
                .titleEn("Java Basics for Beginners")
                .summaryVi("Khóa học giới thiệu cú pháp Java, biến, vòng lặp và hàm.")
                .summaryEn("Introduction to Java syntax, variables, loops, and functions.")
                .published(true)
                .build();
        courseRepository.save(course);

        lessonRepository.save(Lesson.builder()
                .course(course)
                .titleVi("Giới thiệu Java")
                .titleEn("Introduction to Java")
                .contentVi("Java là ngôn ngữ lập trình hướng đối tượng, đa nền tảng.")
                .contentEn("Java is an object-oriented, cross-platform programming language.")
                .codeTemplate("public class Main {\n    public static void main(String[] args) {\n        System.out.println(\"Hello\");\n    }\n}")
                .sequence(1)
                .build());

        lessonRepository.save(Lesson.builder()
                .course(course)
                .titleVi("Biến và kiểu dữ liệu")
                .titleEn("Variables and Data Types")
                .contentVi("Khai báo biến với int, double, String và boolean.")
                .contentEn("Declare variables with int, double, String, and boolean.")
                .codeTemplate("int age = 20;\nString name = \"Student\";\n")
                .sequence(2)
                .build());
    }
}
