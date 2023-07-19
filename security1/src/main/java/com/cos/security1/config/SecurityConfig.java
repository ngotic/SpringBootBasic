package com.cos.security1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 >> 기본 스프링 필터체인에 등록이 된다. 
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override 
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		// 권한 통제 관련이다. 
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated() // 이쪽으로 들어오면 인증이 필요해~
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // 둘중하나 사지고 있으면 OK 
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 어드맨은 한개만 
			.anyRequest().permitAll()  // 다른 요청은 permitAll
			.and()
			.formLogin()
			.loginPage("/login");
	}
	
}
