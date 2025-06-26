<template>
  <div class="sample-view">
    <h1>샘플 목록</h1>
    
    <!-- 검색 폼 -->
    <div class="search-form">
      <select v-model="searchCondition">
        <option value="">전체</option>
        <option value="name">이름</option>
        <option value="description">설명</option>
      </select>
      <input type="text" v-model="searchKeyword" placeholder="검색어를 입력하세요" />
      <select v-model="searchUseYn">
        <option value="">전체</option>
        <option value="Y">사용</option>
        <option value="N">미사용</option>
      </select>
      <button @click="search">검색</button>
      <button @click="goToCreate" class="create-button">등록</button>
    </div>

    <!-- 로딩 표시 -->
    <div v-if="sampleStore.loading" class="loading">
      Loading...
    </div>

    <!-- 에러 메시지 -->
    <div v-if="sampleStore.error" class="error">
      {{ sampleStore.error }}
    </div>

    <!-- 샘플 목록 -->
    <table v-if="sampleStore.samples.length > 0" class="sample-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>이름</th>
          <th>설명</th>
          <th>사용여부</th>
          <th>등록자</th>
          <th>등록일시</th>
          <th>수정일시</th>
          <th>작업</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="sample in sampleStore.samples" :key="sample.id">
          <td>{{ sample.id }}</td>
          <td>{{ sample.name }}</td>
          <td>{{ sample.description }}</td>
          <td>{{ sample.useYn }}</td>
          <td>{{ sample.regUser }}</td>
          <td>{{ formatDate(sample.createdAt) }}</td>
          <td>{{ formatDate(sample.updatedAt) }}</td>
          <td>
            <button @click="goToEdit(sample.id)" class="edit-button">수정</button>
            <button @click="deleteSample(sample.id)" class="delete-button">삭제</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 데이터가 없을 때 -->
    <div v-else-if="!sampleStore.loading" class="no-data">
      데이터가 없습니다.
    </div>

    <!-- 페이지네이션 -->
    <div class="pagination">
      <button 
        :disabled="currentPage === 0" 
        @click="changePage(currentPage - 1)"
      >
        이전
      </button>
      <span>{{ currentPage + 1 }} / {{ totalPages }}</span>
      <button 
        :disabled="currentPage >= totalPages - 1" 
        @click="changePage(currentPage + 1)"
      >
        다음
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useSampleStore } from '@/stores/sample';
import type { Sample } from '@/types/sample';

const router = useRouter();
const sampleStore = useSampleStore();

const searchCondition = ref('');
const searchKeyword = ref('');
const searchUseYn = ref('');
const currentPage = ref(0);
const pageSize = ref(10);

const totalPages = computed(() => 
  Math.ceil((sampleStore.totalItems || 0) / pageSize.value)
);

function formatDate(date: string | null) {
  if (!date) return '';
  return new Date(date).toLocaleString();
}

async function search() {
  currentPage.value = 0;
  await fetchSamples();
}

async function fetchSamples() {
  await sampleStore.fetchSamples(
    searchCondition.value || '',
    searchKeyword.value || '',
    searchUseYn.value || '',
    currentPage.value,
    pageSize.value
  );
}

async function changePage(page: number) {
  currentPage.value = page;
  await fetchSamples();
}

function goToCreate() {
  router.push('/samples/new');
}

function goToEdit(id: number) {
  router.push(`/samples/${id}/edit`);
}

async function deleteSample(id: number) {
  if (confirm('정말 삭제하시겠습니까?')) {
    await sampleStore.deleteSample(id);
    await fetchSamples();
  }
}

onMounted(() => {
  fetchSamples();
});
</script>

<style scoped>
.sample-view {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  padding: 2rem;
  width: 100%;
  box-sizing: border-box;
}

h1 {
  margin-top: 0;
  margin-bottom: 2rem;
  color: #333;
}

.search-form {
  margin-bottom: 2rem;
  display: flex;
  gap: 1rem;
  align-items: center;
  flex-wrap: wrap;
  background-color: #f8f9fa;
  padding: 1.5rem;
  border-radius: 8px;
}

.search-form select,
.search-form input {
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.search-form select {
  min-width: 120px;
  background-color: white;
}

.search-form input {
  min-width: 250px;
  flex: 1;
}

.search-form button {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  background-color: #4CAF50;
  color: white;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.2s;
  min-width: 80px;
}

.search-form button:hover {
  background-color: #45a049;
}

.search-form .create-button {
  background-color: #007bff;
}

.search-form .create-button:hover {
  background-color: #0056b3;
}

.loading {
  text-align: center;
  padding: 2rem;
  color: #666;
}

.error {
  color: #dc3545;
  padding: 1rem;
  margin: 1rem 0;
  border: 1px solid #dc3545;
  border-radius: 4px;
  background-color: #f8d7da;
}

.sample-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 2rem;
}

.sample-table th,
.sample-table td {
  padding: 1rem;
  text-align: left;
  border-bottom: 1px solid #eee;
}

.sample-table th {
  background-color: #f8f9fa;
  font-weight: 600;
}

.sample-table th:nth-child(1) { width: 5%; }  /* ID */
.sample-table th:nth-child(2) { width: 15%; } /* 이름 */
.sample-table th:nth-child(3) { width: 25%; } /* 설명 */
.sample-table th:nth-child(4) { width: 8%; }  /* 사용여부 */
.sample-table th:nth-child(5) { width: 10%; } /* 등록자 */
.sample-table th:nth-child(6) { width: 12%; } /* 등록일시 */
.sample-table th:nth-child(7) { width: 12%; } /* 수정일시 */
.sample-table th:nth-child(8) { width: 13%; } /* 작업 */

.sample-table tr:hover {
  background-color: #f8f9fa;
}

.sample-table td {
  white-space: normal;
  word-break: break-word;
}

.sample-table button {
  margin: 0 0.25rem;
}

.sample-table .edit-button {
  background-color: #007bff;
  color: white;
}

.sample-table .edit-button:hover {
  background-color: #0056b3;
}

.sample-table .delete-button {
  background-color: #dc3545;
  color: white;
}

.sample-table .delete-button:hover {
  background-color: #c82333;
}

.no-data {
  text-align: center;
  padding: 2rem;
  color: #666;
  background-color: #f8f9fa;
  border-radius: 4px;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem;
  margin-top: 2rem;
}

.pagination button {
  padding: 0.5rem 1rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: white;
  color: #333;
  cursor: pointer;
  font-size: 14px;
  min-width: 80px;
  transition: all 0.2s;
}

.pagination button:hover:not(:disabled) {
  background-color: #f8f9fa;
  border-color: #adb5bd;
}

.pagination button:disabled {
  background-color: #e9ecef;
  color: #adb5bd;
  cursor: not-allowed;
}

.pagination span {
  font-size: 14px;
  color: #495057;
}
</style> 