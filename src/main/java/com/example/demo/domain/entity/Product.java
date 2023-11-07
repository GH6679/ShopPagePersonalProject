package com.example.demo.domain.entity;

import com.example.demo.domain.entity.converters.ProductStringArrayConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {

    @Id
    @GeneratedValue
    private Long prodcode;
    private String prodauthor;
    private String prodtype;
    private String prodname;
    private LocalDateTime prodtime;
    private String prodcontext;

    private String prodpath;
    @Convert(converter = ProductStringArrayConverter.class)
    private String[] prodimages;

    //승인?




}