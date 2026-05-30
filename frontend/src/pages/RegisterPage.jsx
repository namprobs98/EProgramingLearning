import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { register } from '../api/authApi';

export default function RegisterPage() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [form, setForm] = useState({ name: '', email: '', password: '' });
  const [message, setMessage] = useState(null);
  const [verifyLink, setVerifyLink] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setMessage(null);
    setVerifyLink(null);
    try {
      const { data } = await register(form);
      setMessage(data.message || t('auth.registerSuccess'));
      if (data.data?.verificationLink) {
        setVerifyLink(data.data.verificationLink);
      } else {
        setTimeout(() => navigate('/login'), 3000);
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page-center">
      <h1 className="page-title">{t('auth.registerTitle')}</h1>
      {message && <div className="alert alert-success">{message}</div>}
      {verifyLink && (
        <div className="card" style={{ marginBottom: '1rem', wordBreak: 'break-all' }}>
          <p style={{ marginBottom: '0.5rem', color: 'var(--text-muted)', fontSize: '0.9rem' }}>
            {t('auth.verifyLinkDev')}
          </p>
          <a href={verifyLink}>{verifyLink}</a>
        </div>
      )}
      {error && <div className="alert alert-error">{error}</div>}
      <form onSubmit={handleSubmit} className="card">
        <div className="form-group">
          <label htmlFor="name">{t('auth.name')}</label>
          <input id="name" name="name" value={form.name} onChange={handleChange} required />
        </div>
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
            minLength={6}
            value={form.password}
            onChange={handleChange}
            required
          />
        </div>
        <button type="submit" className="btn btn-primary" style={{ width: '100%' }} disabled={loading}>
          {loading ? t('common.loading') : t('auth.registerBtn')}
        </button>
      </form>
      <p style={{ marginTop: '1rem', textAlign: 'center', color: 'var(--text-muted)' }}>
        {t('auth.hasAccount')} <Link to="/login">{t('nav.login')}</Link>
      </p>
    </div>
  );
}
