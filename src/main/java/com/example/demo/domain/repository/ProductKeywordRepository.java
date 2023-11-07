package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Product;
import com.example.demo.domain.entity.ProductKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductKeywordRepository extends JpaRepository<ProductKeyword, Integer> {
    @Query(value = "SELECT * FROM bookdb.product_keyword", nativeQuery = true)
    List<ProductKeyword> getByProductKeywordLists();


}
