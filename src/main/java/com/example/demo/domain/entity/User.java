package com.example.demo.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    @Id
    private String username;
    private String nickname;
    private String password;
    private String role;
    private String phone;
    private String zipcode;
    private String addr1;
    private String addr2;

    // OAuth2 Added
    private String provider;
    private String providerId;

    //댓글 신뢰도
    private Long vouch;

    //profile이미지추가
    private String profileimage;

}
