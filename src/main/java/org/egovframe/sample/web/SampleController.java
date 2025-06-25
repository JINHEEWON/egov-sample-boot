package org.egovframe.sample.web;

import lombok.RequiredArgsConstructor;
import org.egovframe.sample.domain.Sample;
import org.egovframe.sample.dto.SampleSearchDto;
import org.egovframe.sample.service.SampleService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/samples")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    @PostMapping
    public ResponseEntity<Sample> insertSample(@RequestBody Sample sample) {
        return ResponseEntity.ok(sampleService.insertSample(sample));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sample> updateSample(@PathVariable Long id, @RequestBody Sample sample) {
        return ResponseEntity.ok(sampleService.updateSample(id, sample));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSample(@PathVariable Long id) {
        sampleService.deleteSample(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sample> selectSample(@PathVariable Long id) {
        return ResponseEntity.ok(sampleService.selectSample(id));
    }

    @GetMapping
    public ResponseEntity<Page<Sample>> selectSampleList(SampleSearchDto searchDto) {
        return ResponseEntity.ok(sampleService.selectSampleList(searchDto));
    }
} 