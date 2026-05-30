package com.eprogramming.learning.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(name = "title_vi", nullable = false)
    private String titleVi;

    @Column(name = "title_en", nullable = false)
    private String titleEn;

    @Column(name = "summary_vi", columnDefinition = "TEXT")
    private String summaryVi;

    @Column(name = "summary_en", columnDefinition = "TEXT")
    private String summaryEn;

    @Column(name = "is_published", nullable = false)
    @Builder.Default
    private boolean published = false;

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
