import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

export default function HomePage() {
  const { t } = useTranslation();

  return (
    <section className="hero">
      <h1>{t('home.heroTitle')}</h1>
      <p>{t('home.heroDesc')}</p>
      <Link to="/courses" className="btn btn-primary">
        {t('home.browseCourses')}
      </Link>
    </section>
  );
}
