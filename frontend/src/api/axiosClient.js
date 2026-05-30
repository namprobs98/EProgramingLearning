import axios from 'axios';
import i18n from '../i18n';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

const axiosClient = axios.create({
  baseURL: API_URL,
  headers: { 'Content-Type': 'application/json' },
});

axiosClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  config.headers['Accept-Language'] = i18n.language || 'vi';
  return config;
});

axiosClient.interceptors.response.use(
  (response) => response,
  (error) => {
    const message =
      error.response?.data?.message ||
      error.message ||
      'Request failed';
    return Promise.reject(new Error(message));
  }
);

export default axiosClient;
