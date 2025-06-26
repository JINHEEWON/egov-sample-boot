import axios from 'axios';
import type { Sample } from '@/types/sample';
import type { Page } from '@/types/common';

const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true
});

// Add request interceptor to include JWT token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Add response interceptor to handle 401 errors
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export interface SampleSearchParams {
  searchCondition?: string;
  searchKeyword?: string;
  searchUseYn?: string;
  page?: number;
  size?: number;
}

const sampleApi = {
  getSamples: async (
    searchCondition?: string,
    searchKeyword?: string,
    searchUseYn?: string,
    page: number = 0,
    size: number = 10
  ) => {
    const response = await api.get('/samples', {
      params: {
        searchCondition,
        searchKeyword,
        searchUseYn,
        page,
        size,
      },
    });
    return response.data;
  },

  getSample: async (id: number): Promise<Sample> => {
    const response = await api.get(`/samples/${id}`);
    return response.data;
  },

  createSample: async (sample: Sample): Promise<Sample> => {
    const response = await api.post('/samples', sample);
    return response.data;
  },

  updateSample: async (sample: Sample): Promise<Sample> => {
    const response = await api.put(`/samples/${sample.id}`, sample);
    return response.data;
  },

  deleteSample: async (id: number): Promise<void> => {
    await api.delete(`/samples/${id}`);
  }
};

export default sampleApi; 