import { useEffect, useState } from 'react';
import { Link, useSearchParams } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { verifyEmail } from '../api/authApi';

export default function VerifyEmailPage() {
  const { t } = useTranslation();
  const [searchParams] = useSearchParams();
  const token = searchParams.get('token');
  const [status, setStatus] = useState('loading');
  const [message, setMessage] = useState('');

  useEffect(() => {
    if (!token) {
      setStatus('error');
      setMessage(t('auth.verifyFail'));
      return;
    }

    verifyEmail(token)
      .then(({ data }) => {
        setStatus('success');
        setMessage(data.message || t('auth.verifySuccess'));
      })
      .catch((err) => {
        setStatus('error');
        setMessage(err.message || t('auth.verifyFail'));
      });
  }, [token, t]);

  return (
    <div className="page-center">
      <h1 className="page-title">{t('auth.verifyTitle')}</h1>
      <div className="card" style={{ textAlign: 'center' }}>
        {status === 'loading' && <p>{t('auth.verifying')}</p>}
        {status === 'success' && (
          <>
            <div className="alert alert-success">{message}</div>
            <Link to="/login" className="btn btn-primary">
              {t('nav.login')}
            </Link>
          </>
        )}
        {status === 'error' && (
          <>
            <div className="alert alert-error">{message}</div>
            <Link to="/register" className="btn btn-outline" style={{ marginTop: '1rem', display: 'inline-block' }}>
              {t('nav.register')}
            </Link>
          </>
        )}
      </div>
    </div>
  );
}
