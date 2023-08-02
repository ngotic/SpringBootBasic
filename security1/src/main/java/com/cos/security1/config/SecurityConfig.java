package com.cos.security1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.security1.config.oauth.PrincipalOauth2UserService;


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

//prePostEnabled 이걸 또 달면 @preAuthorize라는 어노테이션을 활성화한다. 
//이게 있는데...  Secured 어노테이션 활성화를 시켜준다. 얘가.
//뿐만 아니라 @postAuthorize도 활성화시킨다. 

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled= true, 
							prePostEnabled=true) 
public class SecurityConfig {
	
	@Autowired 
	private PrincipalOauth2UserService PrincipalDetailsService;
	
//	@Bean
//	BCryptPasswordEncoder encodePwd() {
//		return new BCryptPasswordEncoder();
//	} 
	
	@Bean 
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/user/**").authenticated() 
		// 이쪽으로 들어오면 인증이 필요해~ > authenticated 붙여서 여기는 인증만 되면 들어가고 밑에 두줄은 아무나 못들어간다.
		
		                       // 어드민도, 관리자도 둘다 OK 
		.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // 둘중하나 사지고 있으면 OK 
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 어드맨은 한개만 
		.anyRequest().permitAll()  // 다른 요청은 permitAll
		.and()
		.formLogin() // 로그인 관련 ~ 페이지에 대한 형식을 정의한다. 
		.loginPage("/loginForm") // 기본적으론 formLogin이 있어서 loginPage로 이동한다. 
		// .usernameParameter("username2") // 만약d에 input 태그의 name 부분 받는 username을 바꾸려면 여기다가 설정해준다. 
		.loginProcessingUrl("/login") // login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
		.defaultSuccessUrl("/")      // login이 성공하면 main 페이지로 보내도록 URL을 설정한다. 
		.and()
		.oauth2Login() //formLogin()과 유사한 방식이다. 그래서 이것은 뒤에 .loginPage("/loginForm"); 를 쓴다. 
		.loginPage("/loginForm")
		.userInfoEndpoint()
		.userService(PrincipalDetailsService);
		// 이렇게 까지 로그인 페이지를 기존 로그인 페이지와 동일하게 해야 한다.
		// 아래 두줄까지 추가해야한다. > 여기서 loadUser라는 함수에서 후처리가 된다. 
		
		// 그래서 우리는 Controller에서 컨트롤러에 /login 유알엘을 처리하는 메서드를 안만들어도 된다.
		
		// 시큐리티가 로그인 로직의 일을 낚아채서 일을 하지만  
		// 근데 이 때 우리가 해야할 일이 있다. 
		// 구글 로그인이 완료되면 세션에 정보가 저장이 안되어 있다.  
		// 그래서 뒤의 후처리가 필요하다.  > 이건 다음시간에 하기
		
		// 구글 로그인이 완료된 뒤의 후처리가 필요한데 Tip 코드 X ( 액세스 토큰 + 사용자 프로필 정보 O )
		// 근데 이걸 바로 받아주기 때문에 Oauthclient라는 라이브러리를 쓰면 편하다. 
		
		
	
		return http.build();
	}
	
}






