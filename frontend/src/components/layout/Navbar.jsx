import { Link, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useAuth } from '../../context/AuthContext';
import LanguageSwitcher from '../common/LanguageSwitcher';
import './Navbar.css';

export default function Navbar() {
  const { t } = useTranslation();
  const { user, isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <header className="navbar">
      <div className="navbar-inner">
        <Link to="/" className="brand">
          <span className="brand-icon">&lt;/&gt;</span>
          {t('app.name')}
        </Link>
        <nav className="nav-links">
          <Link to="/">{t('nav.home')}</Link>
          <Link to="/courses">{t('nav.courses')}</Link>
          {isAuthenticated ? (
            <>
              <span className="nav-user">{t('nav.hello', { name: user.name })}</span>
              <button type="button" className="btn-outline btn-sm" onClick={handleLogout}>
                {t('nav.logout')}
              </button>
            </>
          ) : (
            <>
              <Link to="/login">{t('nav.login')}</Link>
              <Link to="/register" className="btn-primary btn-sm nav-register">
                {t('nav.register')}
              </Link>
            </>
          )}
          <LanguageSwitcher />
        </nav>
      </div>
    </header>
  );
}
