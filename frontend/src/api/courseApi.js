import axiosClient from './axiosClient';

export const getLanguages = () =>
  axiosClient.get('/api/v1/languages');

export const getCourses = (languageId) =>
  axiosClient.get('/api/v1/courses', {
    params: languageId ? { languageId } : {},
  });

export const getCourse = (id) =>
  axiosClient.get(`/api/v1/courses/${id}`);

export const getLessons = (courseId) =>
  axiosClient.get(`/api/v1/courses/${courseId}/lessons`);

export const getLesson = (id) =>
  axiosClient.get(`/api/v1/lessons/${id}`);
