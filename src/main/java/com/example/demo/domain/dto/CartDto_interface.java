package com.example.demo.domain.dto;

import java.time.LocalDateTime;

public interface CartDto_interface {

    Long getcartno();
    Long getprodcode();
    String getprodauthor();
    String getprodtype();
    String getprodname();
    LocalDateTime getprodtime();
    Long getprodprice();

//    private String[] prodtags;

    String getprodcontext();
    String[] getprodexplains();
    String[] getprodkeywords();
    String getprodpath();

    String getprodimages();

    String[] getProdimagepaths();

    int getcartcount();


//    public void setPropertiesFromString(String propertiesString) {
//        this.prodimages = propertiesString.split(",");
//    }
//
//    public String getPropertiesAsString() {
//        return String.join(",", this.prodimages);
//    }
}
