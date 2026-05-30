package com.eprogramming.learning.repository;

import com.eprogramming.learning.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c JOIN FETCH c.language WHERE c.published = true ORDER BY c.id")
    List<Course> findAllPublishedWithLanguage();

    @Query("SELECT c FROM Course c JOIN FETCH c.language WHERE c.id = :id AND c.published = true")
    Optional<Course> findPublishedByIdWithLanguage(Long id);

    @Query("SELECT c FROM Course c JOIN FETCH c.language WHERE c.language.id = :languageId AND c.published = true ORDER BY c.id")
    List<Course> findByLanguageIdAndPublishedTrue(Long languageId);
}
