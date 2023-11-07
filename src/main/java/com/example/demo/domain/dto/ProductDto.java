package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long prodcode;
    private String prodauthor;
    private String prodtype;
    private String prodname;
    private LocalDateTime prodtime;

    private String prodcontext;
    private String[] prodexplains;
    private String[] prodkeywords;
    private String prodpath;
    private String[] prodimages;

    private String[] Prodimagepaths;
}

   