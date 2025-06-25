package org.egovframe.sample.repository;

import org.egovframe.sample.domain.Sample;
import org.egovframe.sample.dto.SampleSearchDto;
import org.springframework.data.jpa.domain.Specification;

public class SampleSpecification {

    public static Specification<Sample> searchWith(SampleSearchDto searchDto) {
        return (root, query, builder) -> {
            if (!searchDto.hasSearch()) {
                return null;
            }

            if (searchDto.getSearchUseYn() != null && !searchDto.getSearchUseYn().isEmpty()) {
                return builder.equal(root.get("useYn"), searchDto.getSearchUseYn());
            }

            if (searchDto.getSearchCondition() != null && !searchDto.getSearchCondition().isEmpty()) {
                switch (searchDto.getSearchCondition()) {
                    case "0":  // ID
                        return builder.like(root.get("id").as(String.class), "%" + searchDto.getSearchKeyword() + "%");
                    case "1":  // Name
                        return builder.like(root.get("name"), "%" + searchDto.getSearchKeyword() + "%");
                    default:
                        return null;
                }
            }

            return null;
        };
    }
} 