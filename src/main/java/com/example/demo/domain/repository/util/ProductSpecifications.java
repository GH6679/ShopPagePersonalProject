package com.example.demo.domain.repository.util;

import com.example.demo.domain.entity.Product;
import com.example.demo.domain.entity.converters.ProductStringArrayConverter;
import org.springframework.data.jpa.domain.Specification;


import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


public class ProductSpecifications {

    public static Specification<Product> productContainsAllKeywordsAndTag(String keywordA ,String keywordB,List<String> keywords) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 문자열 A에 대한 'like' 조건을 생성하여 predicates 리스트에 추가
            if (keywordA != null && !keywordA.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("prodtype")),
                        "%" + keywordA.toLowerCase() + "%")
                );
            }

            // 문자열 B에 대한 'like' 조건을 생성하여 predicates 리스트에 추가
            if (keywordB != null && !keywordB.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("prodname")),
                        "%" + keywordB.toLowerCase() + "%")
                );
            }

            // 각 키워드에 대한 'like' 조건을 생성하여 predicates 리스트에 추가
            for (int i = 0; i < keywords.size(); i++) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("prodtags").as(String.class)),
                        "%" + keywords.get(i).toLowerCase() + "%")
                );
            }




            // 모든 Predicate를 AND 연산으로 결합
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
