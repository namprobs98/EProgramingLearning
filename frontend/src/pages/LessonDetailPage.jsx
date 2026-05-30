import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { getLesson } from '../api/courseApi';

export default function LessonDetailPage() {
  const { t } = useTranslation();
  const { id } = useParams();
  const [lesson, setLesson] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    getLesson(id)
      .then(({ data }) => setLesson(data.data))
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <p>{t('common.loading')}</p>;
  if (error) return <div className="alert alert-error">{error}</div>;
  if (!lesson) return null;

  return (
    <article className="lesson-detail">
      <Link to={`/courses/${lesson.courseId}`} style={{ fontSize: '0.9rem' }}>
        ← {t('courses.viewCourse')}
      </Link>
      <h1 className="page-title" style={{ marginTop: '1rem' }}>
        {lesson.title}
      </h1>
      <div className="content card">{lesson.content}</div>
      {lesson.codeTemplate && (
        <>
          <h3>{t('courses.codeTemplate')}</h3>
          <pre className="code-block">{lesson.codeTemplate}</pre>
        </>
      )}
    </article>
  );
}
