<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <div class="app-container">
    <header>
      <div class="header-content">
        <nav>
          <RouterLink to="/">Home</RouterLink>
          <RouterLink to="/samples">Samples</RouterLink>
        </nav>
      </div>
    </header>

    <main>
      <div class="main-content">
        <RouterView />
      </div>
    </main>
  </div>
</template>

<style>
/* 전역 스타일 */
body {
  margin: 0;
  padding: 0;
  font-family: Arial, sans-serif;
  background-color: #f5f5f5;
}
</style>

<style scoped>
.app-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

header {
  background-color: #f8f9fa;
  padding: 1rem 0;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  position: sticky;
  top: 0;
  z-index: 100;
  width: 100%;
  display: flex;
  justify-content: center;
}

.header-content {
  width: 100%;
  max-width: 1400px;
  padding: 0 2rem;
  box-sizing: border-box;
}

nav {
  display: flex;
  gap: 1rem;
}

nav a {
  color: #333;
  text-decoration: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  transition: background-color 0.2s;
}

nav a:hover {
  background-color: #e9ecef;
}

nav a.router-link-active {
  background-color: #4CAF50;
  color: white;
}

main {
  flex: 1;
  width: 100%;
  display: flex;
  justify-content: center;
}

.main-content {
  width: 100%;
  max-width: 1400px;
  margin: 2rem 0;
  padding: 0 2rem;
  box-sizing: border-box;
}
</style> 