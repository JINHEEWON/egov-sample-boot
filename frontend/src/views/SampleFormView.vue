<template>
  <div class="sample-form">
    <h1>{{ isEdit ? '샘플 수정' : '샘플 등록' }}</h1>

    <form @submit.prevent="handleSubmit" class="form">
      <div class="form-group">
        <label for="name">이름 *</label>
        <input
          id="name"
          v-model="form.name"
          type="text"
          required
          maxlength="100"
          :class="{ 'error': errors.name }"
        />
        <span v-if="errors.name" class="error-message">{{ errors.name }}</span>
      </div>

      <div class="form-group">
        <label for="description">설명</label>
        <textarea
          id="description"
          v-model="form.description"
          rows="4"
          maxlength="4000"
          :class="{ 'error': errors.description }"
        ></textarea>
        <span v-if="errors.description" class="error-message">{{ errors.description }}</span>
      </div>

      <div class="form-group">
        <label for="useYn">사용여부 *</label>
        <select
          id="useYn"
          v-model="form.useYn"
          required
          :class="{ 'error': errors.useYn }"
        >
          <option value="Y">사용</option>
          <option value="N">미사용</option>
        </select>
        <span v-if="errors.useYn" class="error-message">{{ errors.useYn }}</span>
      </div>

      <div class="form-group">
        <label for="regUser">등록자</label>
        <input
          id="regUser"
          v-model="form.regUser"
          type="text"
          maxlength="100"
          :class="{ 'error': errors.regUser }"
        />
        <span v-if="errors.regUser" class="error-message">{{ errors.regUser }}</span>
      </div>

      <div class="button-group">
        <button type="submit" :disabled="loading">
          {{ isEdit ? '수정' : '등록' }}
        </button>
        <button type="button" @click="goBack" :disabled="loading">
          취소
        </button>
      </div>
    </form>

    <div v-if="loading" class="loading">처리중...</div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useSampleStore } from '@/stores/sample';
import type { Sample } from '@/types/sample';

const route = useRoute();
const router = useRouter();
const sampleStore = useSampleStore();

const loading = ref(false);
const errors = ref<Record<string, string>>({});

const isEdit = computed(() => !!route.params.id);

const form = ref<Sample>({
  id: undefined,
  name: '',
  description: '',
  useYn: 'Y',
  regUser: '',
  createdAt: null,
  updatedAt: null
});

async function loadSample() {
  if (isEdit.value) {
    loading.value = true;
    try {
      const sample = await sampleStore.fetchSample(Number(route.params.id));
      if (sample) {
        form.value = { ...sample };
      }
    } catch (error) {
      console.error('Error loading sample:', error);
    } finally {
      loading.value = false;
    }
  }
}

async function handleSubmit() {
  loading.value = true;
  errors.value = {};

  try {
    if (isEdit.value) {
      await sampleStore.updateSample(form.value);
    } else {
      await sampleStore.createSample(form.value);
    }
    router.push('/samples');
  } catch (error: any) {
    if (error.response?.data?.errors) {
      errors.value = error.response.data.errors;
    } else {
      console.error('Error submitting form:', error);
    }
  } finally {
    loading.value = false;
  }
}

function goBack() {
  router.push('/samples');
}

onMounted(() => {
  loadSample();
});
</script>

<style scoped>
.sample-form {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

label {
  font-weight: bold;
}

input, textarea, select {
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
}

input.error, textarea.error, select.error {
  border-color: red;
}

.error-message {
  color: red;
  font-size: 14px;
}

.button-group {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
}

button {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  background-color: #4CAF50;
  color: white;
}

button[type="button"] {
  background-color: #9e9e9e;
}

button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.loading {
  text-align: center;
  margin-top: 20px;
  color: #666;
}
</style> 