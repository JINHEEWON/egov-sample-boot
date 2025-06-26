package org.egovframe.sample.service.impl;

import lombok.RequiredArgsConstructor;
import org.egovframe.sample.domain.Sample;
import org.egovframe.sample.dto.SampleSearchDto;
import org.egovframe.sample.repository.SampleRepository;
import org.egovframe.sample.repository.SampleSpecification;
import org.egovframe.sample.service.SampleService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class SampleServiceImpl implements SampleService {

    private final SampleRepository sampleRepository;

    @Override
    public Sample insertSample(Sample sample) {
        return sampleRepository.save(sample);
    }

    @Override
    public Sample updateSample(Long id, Sample sample) {
        Sample existingSample = sampleRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Sample not found with id: " + id));
        
        existingSample.setName(sample.getName());
        existingSample.setDescription(sample.getDescription());
        existingSample.setUseYn(sample.getUseYn());
        existingSample.setRegUser(sample.getRegUser());
        
        return sampleRepository.save(existingSample);
    }

    @Override
    public void deleteSample(Long id) {
        Sample sample = sampleRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Sample not found with id: " + id));
            
        // 실제 프로젝트에서는 여기에 권한 체크 로직 추가
        // if (!hasDeletePermission(sample)) {
        //     throw new AccessDeniedException("No permission to delete this sample");
        // }
        
        sampleRepository.delete(sample);
    }

    @Override
    @Transactional(readOnly = true)
    public Sample selectSample(Long id) {
        return sampleRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Sample not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Sample> selectSampleList(SampleSearchDto searchDto) {
        return sampleRepository.findAll(
            SampleSpecification.searchWith(searchDto),
            searchDto.getPageable()
        );
    }
} 