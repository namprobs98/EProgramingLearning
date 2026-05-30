import axiosClient from './axiosClient';

export const register = (data) =>
  axiosClient.post('/api/v1/auth/register', data);

export const login = (data) =>
  axiosClient.post('/api/v1/auth/login', data);

export const verifyEmail = (token) =>
  axiosClient.get('/api/v1/auth/verify', { params: { token } });
