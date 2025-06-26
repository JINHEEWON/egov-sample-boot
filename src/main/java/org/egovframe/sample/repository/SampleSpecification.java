package org.egovframe.sample.repository;

import org.egovframe.sample.domain.Sample;
import org.egovframe.sample.dto.SampleSearchDto;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class SampleSpecification {

    public static Specification<Sample> searchWith(SampleSearchDto searchDto) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 검색어와 검색 조건이 있는 경우
            if (searchDto.getSearchKeyword() != null && !searchDto.getSearchKeyword().trim().isEmpty()) {
                String keyword = "%" + searchDto.getSearchKeyword().trim() + "%";
                
                if (searchDto.getSearchCondition() != null && !searchDto.getSearchCondition().isEmpty()) {
                    switch (searchDto.getSearchCondition()) {
                        case "name":
                            predicates.add(builder.like(root.get("name"), keyword));
                            break;
                        case "description":
                            predicates.add(builder.like(root.get("description"), keyword));
                            break;
                        default:
                            // 검색 조건이 지정되지 않은 경우 전체 검색
                            predicates.add(builder.or(
                                builder.like(root.get("name"), keyword),
                                builder.like(root.get("description"), keyword)
                            ));
                    }
                } else {
                    // 검색 조건이 없는 경우 전체 검색
                    predicates.add(builder.or(
                        builder.like(root.get("name"), keyword),
                        builder.like(root.get("description"), keyword)
                    ));
                }
            }

            // 사용여부 검색
            if (searchDto.getSearchUseYn() != null && !searchDto.getSearchUseYn().isEmpty()) {
                predicates.add(builder.equal(root.get("useYn"), searchDto.getSearchUseYn()));
            }

            return predicates.isEmpty() ? null : builder.and(predicates.toArray(new Predicate[0]));
        };
    }
} 