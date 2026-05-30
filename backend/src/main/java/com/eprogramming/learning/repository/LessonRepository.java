package com.eprogramming.learning.repository;

import com.eprogramming.learning.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("SELECT l FROM Lesson l JOIN FETCH l.course c WHERE c.id = :courseId AND c.published = true ORDER BY l.sequence")
    List<Lesson> findByCourseIdPublished(Long courseId);

    @Query("SELECT l FROM Lesson l JOIN FETCH l.course c WHERE l.id = :id AND c.published = true")
    Optional<Lesson> findPublishedById(Long id);
}
