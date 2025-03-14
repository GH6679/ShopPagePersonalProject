package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long cartno;
    private Long prodcode;
    private String prodauthor;
    private String prodtype;
    private String prodname;
    private LocalDateTime prodtime;
    private Long prodprice;

//    private String[] prodtags;

    private String prodcontext;
    private String[] prodexplains;
    private String[] prodkeywords;
    private String prodpath;

    private String[] prodimages;

    private String[] Prodimagepaths;

    private int cartcount;


}
