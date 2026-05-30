import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { getCourse, getLessons } from '../api/courseApi';

export default function CourseDetailPage() {
  const { t } = useTranslation();
  const { id } = useParams();
  const [course, setCourse] = useState(null);
  const [lessons, setLessons] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    Promise.all([getCourse(id), getLessons(id)])
      .then(([courseRes, lessonsRes]) => {
        setCourse(courseRes.data.data);
        setLessons(lessonsRes.data.data || []);
      })
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <p>{t('common.loading')}</p>;
  if (error) return <div className="alert alert-error">{error}</div>;
  if (!course) return null;

  return (
    <div>
      <p className="meta" style={{ color: 'var(--text-muted)', marginBottom: '0.5rem' }}>
        {course.languageName}
      </p>
      <h1 className="page-title">{course.title}</h1>
      <p style={{ marginBottom: '2rem', color: 'var(--text-muted)' }}>{course.summary}</p>
      <h2>{t('courses.lessonList')}</h2>
      <ul className="lesson-list card" style={{ padding: '0 1.25rem' }}>
        {lessons.map((lesson) => (
          <li key={lesson.id}>
            <Link to={`/lessons/${lesson.id}`}>
              {lesson.sequence}. {lesson.title}
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}
