package org.egovframe.sample.service;

import org.egovframe.sample.domain.Sample;
import org.egovframe.sample.dto.SampleSearchDto;
import org.springframework.data.domain.Page;

public interface SampleService {
    Sample insertSample(Sample sample);
    Sample updateSample(Long id, Sample sample);
    void deleteSample(Long id);
    Sample selectSample(Long id);
    Page<Sample> selectSampleList(SampleSearchDto searchDto);
} 