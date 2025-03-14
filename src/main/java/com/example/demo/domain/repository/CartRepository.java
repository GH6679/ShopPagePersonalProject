package com.example.demo.domain.repository;

import com.example.demo.domain.dto.CartDto_interface;
import com.example.demo.domain.entity.Cart;
import com.example.demo.domain.entity.Product;
import com.example.demo.domain.entity.ProductKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query(value = "SELECT * FROM cm_shopdb.cart C WHERE C.prodcode = :prodcode AND C.username = :username" , nativeQuery = true)
    List<Cart> findByCartProd(@Param("prodcode") Long prodcode, @Param("username")String username);

    @Query(value = "SELECT P.*,C.cartcount,C.cartno FROM cm_shopdb.cart C inner join cm_shopdb.product P on C.prodcode = P.prodcode WHERE C.prodcode = :prodcode AND C.username = :username" , nativeQuery = true)
    List<CartDto_interface> findByCartProdOne(@Param("prodcode") Long prodcode, @Param("username")String username);

    @Query(value = "SELECT * FROM cm_shopdb.cart C WHERE C.username = :username" , nativeQuery = true)
    List<Cart> findByUserCartList(@Param("username")String username);

    @Query(value = "SELECT SUM(P.prodprice * C.cartcount) FROM cm_shopdb.cart C inner join cm_shopdb.product P on C.prodcode = P.prodcode where C.username = :username", nativeQuery = true)
    Long sumByCartPrice(@Param("username") String username);

    @Query(value = "SELECT P.*,C.cartcount,C.cartno FROM cm_shopdb.cart C inner join cm_shopdb.product P on C.prodcode = P.prodcode where C.username = :username", nativeQuery = true)
    List<CartDto_interface> findByCartLists(@Param("username")String username);

}
