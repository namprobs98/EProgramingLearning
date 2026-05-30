import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { getCourses, getLanguages } from '../api/courseApi';

export default function CoursesPage() {
  const { t } = useTranslation();
  const [languages, setLanguages] = useState([]);
  const [courses, setCourses] = useState([]);
  const [selectedLang, setSelectedLang] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    getLanguages()
      .then(({ data }) => setLanguages(data.data || []))
      .catch(() => {});
  }, []);

  useEffect(() => {
    setLoading(true);
    setError(null);
    getCourses(selectedLang)
      .then(({ data }) => setCourses(data.data || []))
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  }, [selectedLang]);

  return (
    <div>
      <h1 className="page-title">{t('courses.title')}</h1>
      <div className="filter-bar">
        <button
          type="button"
          className={`filter-btn ${selectedLang === null ? 'active' : ''}`}
          onClick={() => setSelectedLang(null)}
        >
          {t('courses.filterAll')}
        </button>
        {languages.map((lang) => (
          <button
            key={lang.id}
            type="button"
            className={`filter-btn ${selectedLang === lang.id ? 'active' : ''}`}
            onClick={() => setSelectedLang(lang.id)}
          >
            {lang.name}
          </button>
        ))}
      </div>
      {loading && <p>{t('common.loading')}</p>}
      {error && <div className="alert alert-error">{error}</div>}
      {!loading && !error && courses.length === 0 && (
        <p style={{ color: 'var(--text-muted)' }}>{t('courses.empty')}</p>
      )}
      <div className="course-grid">
        {courses.map((course) => (
          <article key={course.id} className="card course-card">
            <p className="meta">{course.languageName}</p>
            <h3>{course.title}</h3>
            <p style={{ color: 'var(--text-muted)', fontSize: '0.9rem', marginBottom: '1rem' }}>
              {course.summary}
            </p>
            <Link to={`/courses/${course.id}`} className="btn btn-outline btn-sm">
              {t('courses.viewCourse')}
            </Link>
          </article>
        ))}
      </div>
    </div>
  );
}
