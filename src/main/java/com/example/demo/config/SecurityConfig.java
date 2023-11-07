package com.example.demo.config;



//security-context.xml 설정 내용

import com.example.demo.config.auth.PrincipalDetailsOAuth2Service;
import com.example.demo.config.auth.PrincipalDetailsService;
import com.example.demo.config.auth.exceptionhandler.CustomAccessDeniedHandler;
import com.example.demo.config.auth.exceptionhandler.CustomAuthenticationEntryPoint;
import com.example.demo.config.auth.loginHandler.CustomAuthenticationFailureHandler;
import com.example.demo.config.auth.loginHandler.CustomLoginSuccessHandler;
import com.example.demo.config.auth.logoutHandler.CustomLogoutSuccessHandler;
import com.example.demo.config.auth.logoutHandler.OAuthLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@Autowired
	private DataSource dataSource;
	@Autowired
	private PrincipalDetailsService principalDetailsService;

	@Autowired
	private PrincipalDetailsOAuth2Service principalDetailsOAuth2Service;

	//----------------------------------------------------------------
	// 웹 요청 처리
	//----------------------------------------------------------------
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http.csrf().disable();
		
		http
			.authorizeRequests()
//				.antMatchers("/css/**","/js/**","/images/**").permitAll()
//				.antMatchers("/","/login","/user/join","/logout").permitAll()
//				.antMatchers("/user/**").permitAll()
//				.antMatchers("/board/list").permitAll()
//				.antMatchers("/board/read","/board/post","/board/delete","/board/update").hasAnyRole("USER","ADMIN","MEMBER")

				.antMatchers("/css/**","/js/**","/img/**").permitAll() //자원 경로
				.antMatchers("/product/index","/product/get/**","/product/list").permitAll()  //메인 페이지 및 상품상세 페이지
				.antMatchers("/user/**" ,"/logout").permitAll() //로그인 로그아웃 회원 가입
				.antMatchers("/product/set","/product/update","/product/delete/**").hasAnyRole("MEMBER","ADMIN") //상품 등록 , 상품 수정
				.antMatchers("/product/keyword/set","/product/keyword/list","/product/keyword/delete/**").hasAnyRole("ADMIN")    //키워드 등록 , 수정 , 삭제

				.antMatchers("/board/list","/board/read","/board/read/**").permitAll()
				.antMatchers("/board/reply/list","/board/reply/count","/board/getVouch/**").permitAll()
				.antMatchers("/board/post","/board/delete","/board/update").hasAnyRole("USER","ADMIN","MEMBER")
				.antMatchers("/board/reply/update","/reply/thumbsup").hasAnyRole("USER","ADMIN","MEMBER")

				.anyRequest().authenticated()									//나머지 URL은 모두 인증작업이 완료된 이후 접근가능
			.and()

			//로그인
			.formLogin()
			.loginPage("/user/login")
			.successHandler(new CustomLoginSuccessHandler())					//ROLE_USER -> user페이지 / ROLE_MEMBER -> member페이지
			.failureHandler(new CustomAuthenticationFailureHandler())

			.and()
//			//로그아웃
			.logout()
			.logoutUrl("/user/logout")
			.permitAll()
			//.addLogoutHandler(new CustomLogoutHandler())						//세션초기화
			.addLogoutHandler(new OAuthLogoutHandler())
			.logoutSuccessHandler(new CustomLogoutSuccessHandler())				//기본위치로 페이지이동

			.and()
			//예외처리
			.exceptionHandling()
			.authenticationEntryPoint(new CustomAuthenticationEntryPoint())		//인증이 필요한 자원에 접근 예외처리
			.accessDeniedHandler(new CustomAccessDeniedHandler())				//권한 실패 예외처리

			//REMEMBER ME
			.and()
				.rememberMe()
				.rememberMeParameter("remember-me")
				.tokenValiditySeconds(60*60)
				.alwaysRemember(false)
				.tokenRepository(tokenRepository())
				.userDetailsService(principalDetailsOAuth2Service)

			.and()
			//OAUTH2
				.oauth2Login()
				.userInfoEndpoint()
				.userService(principalDetailsOAuth2Service);

			
	}
	
	//----------------------------------------------------------------
	// 인증처리 함수
	//----------------------------------------------------------------
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//			.inMemoryAuthentication()
//				.withUser("user")
//				.password(passwordEncoder().encode("1234"))
//				.roles("USER");
//		auth
//			.inMemoryAuthentication()
//				.withUser("member")
//				.password(passwordEncoder().encode("1234"))
//				.roles("MEMBER");
//		auth
//			.inMemoryAuthentication()
//				.withUser("admin")
//				.password(passwordEncoder().encode("1234"))
//				.roles("ADMIN");
		
		auth.userDetailsService(principalDetailsOAuth2Service)
			.passwordEncoder(passwordEncoder());
		
	}
	
	
	//----------------------------------------------------------------
	//BEANS
	//----------------------------------------------------------------
	
	// BCryptPasswordEncoder Bean 등록
	// 패스워드 검증에 사용
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//REMEMBER ME BEAN 추가

	@Bean
	public PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		//repo.setCreateTableOnStartup(true);
		return repo;
	}


	

	
}
