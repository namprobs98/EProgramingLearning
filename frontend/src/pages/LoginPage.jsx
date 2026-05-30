import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { login } from '../api/authApi';
import { useAuth } from '../context/AuthContext';

export default function LoginPage() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { login: authLogin } = useAuth();
  const [form, setForm] = useState({ email: '', password: '' });
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      const { data } = await login(form);
      authLogin(data.data);
      navigate('/courses');
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page-center">
      <h1 className="page-title">{t('auth.loginTitle')}</h1>
      {error && <div className="alert alert-error">{error}</div>}
      <form onSubmit={handleSubmit} className="card">
        <div className="form-group">
          <label htmlFor="email">{t('auth.email')}</label>
          <input id="email" name="email" type="email" value={form.email} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label htmlFor="password">{t('auth.password')}</label>
          <input
            id="password"
            name="password"
            type="password"
            value={form.password}
            onChange={handleChange}
            required
          />
        </div>
        <button type="submit" className="btn btn-primary" style={{ width: '100%' }} disabled={loading}>
          {loading ? t('common.loading') : t('auth.loginBtn')}
        </button>
      </form>
      <p style={{ marginTop: '1rem', textAlign: 'center', color: 'var(--text-muted)' }}>
        {t('auth.noAccount')} <Link to="/register">{t('nav.register')}</Link>
      </p>
    </div>
  );
}
