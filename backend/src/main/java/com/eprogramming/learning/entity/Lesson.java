package com.eprogramming.learning.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "title_vi", nullable = false)
    private String titleVi;

    @Column(name = "title_en", nullable = false)
    private String titleEn;

    @Column(name = "content_vi", columnDefinition = "MEDIUMTEXT")
    private String contentVi;

    @Column(name = "content_en", columnDefinition = "MEDIUMTEXT")
    private String contentEn;

    @Column(name = "code_template", columnDefinition = "MEDIUMTEXT")
    private String codeTemplate;

    @Column(nullable = false)
    private Integer sequence;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }
}
