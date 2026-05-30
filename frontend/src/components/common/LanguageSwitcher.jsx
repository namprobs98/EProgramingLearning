import { useTranslation } from 'react-i18next';

export default function LanguageSwitcher() {
  const { i18n, t } = useTranslation();

  const change = (lng) => {
    i18n.changeLanguage(lng);
    localStorage.setItem('lang', lng);
    window.location.reload();
  };

  return (
    <div className="lang-switcher">
      <span className="lang-label">{t('common.language')}:</span>
      <button
        type="button"
        className={`lang-btn ${i18n.language === 'vi' ? 'active' : ''}`}
        onClick={() => change('vi')}
      >
        VI
      </button>
      <button
        type="button"
        className={`lang-btn ${i18n.language === 'en' ? 'active' : ''}`}
        onClick={() => change('en')}
      >
        EN
      </button>
    </div>
  );
}
