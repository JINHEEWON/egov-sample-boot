import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import axios from 'axios'

const api = axios.create({
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true
});

export interface LoginResponse {
  token: string;
  user: {
    username: string;
    roles: string[];
  };
}

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string | null>(localStorage.getItem('token'))
  const user = ref<any | null>(null)

  const isAuthenticated = computed(() => !!token.value)

  function setToken(newToken: string | null) {
    token.value = newToken
    if (newToken) {
      localStorage.setItem('token', newToken)
    } else {
      localStorage.removeItem('token')
    }
  }

  function setUser(newUser: any) {
    user.value = newUser
  }

  async function login(username: string, password: string) {
    try {
      const response = await api.post<LoginResponse>('/api/auth/login', {
        username,
        password,
      })

      const { token: newToken, user: userData } = response.data
      setToken(newToken)
      setUser(userData)
      return true
    } catch (error) {
      console.error('Login error:', error)
      return false
    }
  }

  function logout() {
    setToken(null)
    setUser(null)
  }

  // Initialize user data from token if it exists
  async function initializeAuth() {
    const storedToken = localStorage.getItem('token')
    if (storedToken) {
      try {
        const response = await api.get('/api/auth/me', {
          headers: {
            Authorization: `Bearer ${storedToken}`
          }
        })
        setUser(response.data)
      } catch (error) {
        console.error('Failed to initialize auth:', error)
        logout()
      }
    }
  }

  return {
    token,
    user,
    isAuthenticated,
    login,
    logout,
    setUser,
    initializeAuth
  }
}) 