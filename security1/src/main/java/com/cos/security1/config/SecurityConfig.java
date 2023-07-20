package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


// https://blog.naver.com/PostView.naver?blogId=h850415&logNo=222755455272&parentCategoryNo=&categoryNo=37&viewDate=&isShowPopularPosts=true&from=search
// 현재 WebSecurityConfigurerAdapter는 deprecated 되었다. 


//이게 security-config.xml 역할이다.
/*
@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 >> 기본 스프링 필터체인에 등록이 된다. 
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	// @Bean 어노테이션을 적으면 해당 메서드의 리턴되는 오브젝트를 IoC로 등록시켜준다. > 이거를 다른 Controller에서 쓰면 된다.
	@Bean
	BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	} 
	
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
			.loginPage("/loginForm");
	} // 이 방식은 configure 메서드를 오버라이딩하여 설정하는 방법이다.
	
}
*/

// 위의 방식은 deprecated ! 

// ★★★ > 권장 방식 FilterChain을 Bean으로 등록하는 방식을 사용해야 한다.

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	} 
	
	@Bean 
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/user/**").authenticated() // 이쪽으로 들어오면 인증이 필요해~ > authenticated 붙여서 
		.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // 둘중하나 사지고 있으면 OK 
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 어드맨은 한개만 
		.anyRequest().permitAll()  // 다른 요청은 permitAll
		.and()
		.formLogin()
		.loginPage("/loginForm")
		// .usernameParameter("username2") // 만약d에 input 태그의 name 부분 받는 username을 바꾸려면 여기다가 설정해준다. 
		.loginProcessingUrl("/login") // login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
		.defaultSuccessUrl("/");      // login이 성공하면 main 페이지로 보내도록 URL을 설정한다. 
		// 그래서 우리는 Controller에서 컨트롤러에 /login 유알엘을 처리하는 메서드를 안만들어도 된다.
		
		// 시큐리티가 로그인 로직의 일을 낚아채서 일을 하지만  
		// 근데 이 때 우리가 해야할 일이 있다. 
		
		return http.build();
	}
	
}






