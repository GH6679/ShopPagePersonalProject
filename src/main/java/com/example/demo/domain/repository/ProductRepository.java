package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {

    @Query(value = "SELECT * FROM cm_shopdb.product p WHERE p.prodtype LIKE %:prodtype% AND p.prodtags IN :prodtags", nativeQuery = true)
    List<Product> findByProductLists(@Param("prodtype") String prodtype,@Param("prodtags") List<String> prodtags);

    @Query("SELECT p FROM Product p WHERE CONCAT(p.prodname, p.prodtags, p.prodtype) LIKE %:key%")
    List<Product> findBySearchProduct(@Param("key") String key);

    @Query("SELECT p FROM Product p WHERE CONCAT(p.prodtags) LIKE %:key%")
    List<Product> findBySearchTag(@Param("key") String key);

    @Query(value = "SELECT P.* FROM cm_shopdb.cart C inner join cm_shopdb.product P on C.prodcode = P.prodcode where C.username = :username", nativeQuery = true)
    List<Product> findByCartLists(@Param("username")String username);


}
