package com.example.demo.domain.entity;

import com.example.demo.domain.entity.converters.ProductStringArrayConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long prodcode;
    private String prodauthor;
    private String prodtype;
    private String prodname;
    private LocalDateTime prodtime;
    private Long prodprice;
    @Convert(converter = ProductStringArrayConverter.class)
    private String[] prodtags;

    private String prodcontext;
    private String prodpath;
    @Convert(converter = ProductStringArrayConverter.class)
    private String[] prodimages;

    //승인?





}