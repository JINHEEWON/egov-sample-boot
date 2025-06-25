import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import type { Sample } from '@/types/sample';
import type { Page } from '@/types/common';
import type { SampleSearchParams } from '@/api/sampleApi';
import sampleApi from '@/api/sampleApi';

export const useSampleStore = defineStore('sample', {
  state: () => ({
    samples: [] as Sample[],
    currentSample: null as Sample | null,
    totalItems: 0,
    loading: false,
    error: null as string | null,
  }),

  actions: {
    async fetchSamples(
      searchCondition?: string,
      searchKeyword?: string,
      searchUseYn?: string,
      page: number = 0,
      size: number = 10
    ) {
      this.loading = true;
      try {
        const response = await sampleApi.getSamples(
          searchCondition,
          searchKeyword,
          searchUseYn,
          page,
          size
        );
        this.samples = response.content;
        this.totalItems = response.totalElements;
      } catch (error) {
        this.error = '샘플 목록을 불러오는데 실패했습니다.';
        console.error('Error fetching samples:', error);
      } finally {
        this.loading = false;
      }
    },

    async fetchSample(id: number) {
      this.loading = true;
      try {
        this.currentSample = await sampleApi.getSample(id);
      } catch (error) {
        this.error = '샘플을 불러오는데 실패했습니다.';
        console.error('Error fetching sample:', error);
      } finally {
        this.loading = false;
      }
    },

    async createSample(sample: Sample) {
      this.loading = true;
      try {
        const newSample = await sampleApi.createSample(sample);
        this.samples.unshift(newSample);
      } catch (error) {
        this.error = '샘플 생성에 실패했습니다.';
        console.error('Error creating sample:', error);
      } finally {
        this.loading = false;
      }
    },

    async updateSample(sample: Sample) {
      this.loading = true;
      try {
        const updatedSample = await sampleApi.updateSample(sample);
        const index = this.samples.findIndex(s => s.id === sample.id);
        if (index !== -1) {
          this.samples[index] = updatedSample;
        }
      } catch (error) {
        this.error = '샘플 수정에 실패했습니다.';
        console.error('Error updating sample:', error);
      } finally {
        this.loading = false;
      }
    },

    async deleteSample(id: number) {
      this.loading = true;
      try {
        await sampleApi.deleteSample(id);
        this.samples = this.samples.filter(s => s.id !== id);
      } catch (error) {
        this.error = '샘플 삭제에 실패했습니다.';
        console.error('Error deleting sample:', error);
      } finally {
        this.loading = false;
      }
    },
  },
}); 