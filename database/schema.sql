-- =============================================================================
-- E-Programming Learning Platform — MySQL DDL
-- Charset: utf8mb4 (Vietnamese + English UI and content)
-- =============================================================================

CREATE DATABASE IF NOT EXISTS eprogramming_learning
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE eprogramming_learning;

-- -----------------------------------------------------------------------------
-- users
-- -----------------------------------------------------------------------------
CREATE TABLE users (
  id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  name          VARCHAR(150)    NOT NULL,
  email         VARCHAR(255)    NOT NULL,
  password      VARCHAR(255)    NOT NULL COMMENT 'BCrypt hash',
  role          ENUM('STUDENT', 'INSTRUCTOR', 'ADMIN') NOT NULL DEFAULT 'STUDENT',
  is_active     TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '0 = pending email verification',
  created_at    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_users_email (email),
  KEY idx_users_is_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- email_verification_tokens
-- -----------------------------------------------------------------------------
CREATE TABLE email_verification_tokens (
  id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  token         VARCHAR(36)     NOT NULL COMMENT 'UUID string',
  user_id       BIGINT UNSIGNED NOT NULL,
  expiry_date   DATETIME        NOT NULL,
  created_at    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_evt_token (token),
  KEY idx_evt_user_id (user_id),
  KEY idx_evt_expiry_date (expiry_date),
  CONSTRAINT fk_evt_user
    FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- languages (programming languages: Java, Python, JavaScript, ...)
-- -----------------------------------------------------------------------------
CREATE TABLE languages (
  id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  name          VARCHAR(100)    NOT NULL COMMENT 'Display name, e.g. Java',
  code          VARCHAR(50)     NOT NULL COMMENT 'Slug, e.g. java',
  created_at    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_languages_code (code),
  UNIQUE KEY uk_languages_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- courses
-- -----------------------------------------------------------------------------
CREATE TABLE courses (
  id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  language_id   BIGINT UNSIGNED NOT NULL,
  title_vi      VARCHAR(255)    NOT NULL,
  title_en      VARCHAR(255)    NOT NULL,
  summary_vi    TEXT            NULL,
  summary_en    TEXT            NULL,
  is_published  TINYINT(1)      NOT NULL DEFAULT 0,
  created_at    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY idx_courses_language_id (language_id),
  KEY idx_courses_is_published (is_published),
  CONSTRAINT fk_courses_language
    FOREIGN KEY (language_id) REFERENCES languages (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- lessons
-- -----------------------------------------------------------------------------
CREATE TABLE lessons (
  id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  course_id       BIGINT UNSIGNED NOT NULL,
  title_vi        VARCHAR(255)    NOT NULL,
  title_en        VARCHAR(255)    NOT NULL,
  content_vi      MEDIUMTEXT      NULL,
  content_en      MEDIUMTEXT      NULL,
  code_template   MEDIUMTEXT      NULL COMMENT 'Starter code for exercises',
  sequence        INT UNSIGNED    NOT NULL DEFAULT 1 COMMENT 'Order within course',
  created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_lessons_course_sequence (course_id, sequence),
  KEY idx_lessons_course_id (course_id),
  CONSTRAINT fk_lessons_course
    FOREIGN KEY (course_id) REFERENCES courses (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- Optional seed: programming languages (remove if you prefer empty DB)
-- -----------------------------------------------------------------------------
INSERT INTO languages (name, code) VALUES
  ('Java',       'java'),
  ('Python',     'python'),
  ('JavaScript', 'javascript'),
  ('C++',        'cpp');
