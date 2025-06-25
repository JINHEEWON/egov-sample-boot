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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SampleServiceImpl implements SampleService {

    private final SampleRepository sampleRepository;

    @Override
    @Transactional
    public Sample insertSample(Sample sample) {
        sample.setUseYn("Y");
        return sampleRepository.save(sample);
    }

    @Override
    @Transactional
    public Sample updateSample(Long id, Sample sample) {
        Sample existingSample = selectSample(id);
        existingSample.setName(sample.getName());
        existingSample.setDescription(sample.getDescription());
        existingSample.setUseYn(sample.getUseYn());
        existingSample.setRegUser(sample.getRegUser());
        return sampleRepository.save(existingSample);
    }

    @Override
    @Transactional
    public void deleteSample(Long id) {
        Sample sample = selectSample(id);
        sample.setUseYn("N");
        sampleRepository.save(sample);
    }

    @Override
    public Sample selectSample(Long id) {
        return sampleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sample ID: " + id));
    }

    @Override
    public Page<Sample> selectSampleList(SampleSearchDto searchDto) {
        return sampleRepository.findAll(
                SampleSpecification.searchWith(searchDto),
                searchDto.getPageable()
        );
    }
} 