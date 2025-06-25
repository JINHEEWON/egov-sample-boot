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
            <button @click="editSample(sample)">수정</button>
            <button @click="deleteSample(sample.id)">삭제</button>
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
import { useSampleStore } from '@/stores/sample';
import type { Sample } from '@/types/sample';

const sampleStore = useSampleStore();

const searchCondition = ref('');
const searchKeyword = ref('');
const searchUseYn = ref('');
const currentPage = ref(0);
const pageSize = ref(10);

const totalPages = computed(() => 
  Math.ceil(sampleStore.totalItems / pageSize.value)
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
    searchCondition.value,
    searchKeyword.value,
    searchUseYn.value,
    currentPage.value,
    pageSize.value
  );
}

async function changePage(page: number) {
  currentPage.value = page;
  await fetchSamples();
}

async function editSample(sample: Sample) {
  // TODO: 수정 기능 구현
  console.log('Edit sample:', sample);
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
  padding: 20px;
}

.search-form {
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
}

.loading {
  text-align: center;
  padding: 20px;
}

.error {
  color: red;
  padding: 10px;
  margin: 10px 0;
  border: 1px solid red;
  border-radius: 4px;
}

.sample-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

.sample-table th,
.sample-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.sample-table th {
  background-color: #f4f4f4;
}

.no-data {
  text-align: center;
  padding: 20px;
  color: #666;
}

.pagination {
  display: flex;
  justify-content: center;
  gap: 10px;
  align-items: center;
}

button {
  padding: 5px 10px;
  cursor: pointer;
}

button:disabled {
  cursor: not-allowed;
  opacity: 0.5;
}
</style> 