package com.example.demo.config.auth.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
//		System.out.println("인증실패! : " + authException );
//		System.out.println("인증실패! : " + authException.getLocalizedMessage() );
//		System.out.println("인증실패! : " + authException.getMessage() );
//		System.out.println("인증실패! : " + authException.getCause() );
		log.info("CustomAuthenticationEntryPoint..."+authException);
	
		request.getSession().setAttribute("msg", "[SERVER] 인증이 필요합니다.");
		request.getRequestDispatcher("/user/login").forward(request, response);
	}

}










