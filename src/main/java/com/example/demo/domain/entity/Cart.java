package com.example.demo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long cartno;

    @ManyToOne
    @JoinColumn(name = "username",foreignKey = @ForeignKey(name = "FK_cart_user_username",
            foreignKeyDefinition = "FOREIGN KEY (username) REFERENCES user(username) ON DELETE CASCADE ON UPDATE CASCADE") ) //FK설정
    private User user;

    @ManyToOne
    @JoinColumn(name = "prodcode",foreignKey = @ForeignKey(name = "FK_cart_product_prodcode",
            foreignKeyDefinition = "FOREIGN KEY (prodcode) REFERENCES product(prodcode) ON DELETE CASCADE ON UPDATE CASCADE") ) //FK설정
    private Product product;

    private int cartcount;

    private LocalDateTime cartintime;



}
