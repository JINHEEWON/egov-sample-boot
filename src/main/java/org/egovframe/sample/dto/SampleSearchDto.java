package org.egovframe.sample.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SampleSearchDto {

    private String searchCondition = "";
    private String searchKeyword = "";
    private String searchUseYn = "";

    @Builder.Default
    private int page = 0;

    @Builder.Default
    private int size = 10;

    public Pageable getPageable() {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
    }

    public boolean hasSearch() {
        return (searchCondition != null && !searchCondition.isEmpty()) ||
               (searchKeyword != null && !searchKeyword.isEmpty()) ||
               (searchUseYn != null && !searchUseYn.isEmpty());
    }
} 