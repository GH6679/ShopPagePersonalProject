package com.example.demo.domain.dto;

import com.example.demo.domain.entity.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserDto {
	@NotBlank(message = "이메일주소를 입력하세요")
	@Email(message = "올바른 이메일 주소를 입력하세요")
	private String username;

	@NotBlank(message = "계정명을 입력하세요")
	private String nickname;

	@NotBlank(message = "패스워드를 입력하세요")
//	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String password;

	@NotBlank(message = "패스워드를 다시 입력하세요")
//	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String repassword;
	private String role;
	private String phone;
	private String zipcode;
	private String addr1;
	private String addr2;

	//OAUTH2
	private String provider;
	private String providerId;

	private Long vouch;

	public static User dtoToEntity(UserDto dto){
		User user = User.builder()
				.username(dto.getUsername())
				.nickname(dto.getNickname())
				.password(dto.getPassword())
				.role(dto.getRole())
				.phone(dto.getPhone())
				.zipcode(dto.getZipcode())
				.addr1(dto.getAddr1())
				.addr2(dto.getAddr2())
				.build();
		return user;
	}

	private String profileimage;

}
