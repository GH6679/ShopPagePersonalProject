package com.example.demo.domain.entity.converters;

import javax.persistence.AttributeConverter;

public class ProductStringArrayConverter implements AttributeConverter<String[],String> {
    @Override
    public String convertToDatabaseColumn(String[] attribute) {
        //"," 를 경계로 사용해 String[] 배열을 db에 String 으로 저장
        return String.join(",", attribute);
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        //"," 를 경계로 잘라내어 String 을 String[] 배열로 변경
        return dbData.split(",");
    }
}
