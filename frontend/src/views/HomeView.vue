<script setup lang="ts">
import { RouterLink } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'

const authStore = useAuthStore()
const { isAuthenticated } = storeToRefs(authStore)
</script>

<template>
  <main>
    <div class="home">
      <h1>Welcome to eGov Sample</h1>
      
      <div v-if="isAuthenticated" class="content">
        <p>You are logged in.</p>
        <RouterLink to="/sample" class="btn-primary">Go to Samples</RouterLink>
      </div>
      
      <div v-else class="content">
        <p>Please log in to access the sample management system.</p>
        <RouterLink to="/login" class="btn-primary">Login</RouterLink>
      </div>
    </div>
  </main>
</template>

<style scoped>
.home {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
  text-align: center;
}

h1 {
  font-size: 2.5rem;
  color: #2c3e50;
  margin-bottom: 2rem;
}

.content {
  margin-top: 2rem;
}

p {
  font-size: 1.2rem;
  color: #666;
  margin-bottom: 2rem;
}

.btn-primary {
  display: inline-block;
  padding: 0.75rem 1.5rem;
  background-color: #4CAF50;
  color: white;
  text-decoration: none;
  border-radius: 4px;
  transition: background-color 0.2s;
}

.btn-primary:hover {
  background-color: #45a049;
}
</style> 