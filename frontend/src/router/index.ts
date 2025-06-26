import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/samples',
    name: 'sample-list',
    component: () => import('../views/SampleView.vue')
  },
  {
    path: '/samples/new',
    name: 'sample-create',
    component: () => import('../views/SampleFormView.vue')
  },
  {
    path: '/samples/:id/edit',
    name: 'sample-edit',
    component: () => import('../views/SampleFormView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router 