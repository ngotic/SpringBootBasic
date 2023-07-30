package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller //View를 리턴하겠다
public class IndexController { 
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@GetMapping("/test/login")
	public @ResponseBody String testLogin(Authentication authentication, //DI 의존성 주입
			//@AuthenticationPrincipal UserDetails userDetails) { 
			@AuthenticationPrincipal PrincipalDetails userDetails) {
		System.out.println("/test/login ==============");
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); 
		// 리턴 타입 오브젝트라 > 이걸 다운 캐스팅으로 받아서 처리하고 
		
		// 그다음에 getUser로 처리한다. 
		System.out.println("authentication : "+ principalDetails.getUser());// 리턴 타입이 오브젝트다.
		System.out.println("userDetails : "+ userDetails.getUser());// 이렇게 저장하고 테스트 해본다.  
		return "세션 정보 확인하기";
	}
	
	@GetMapping("/test/oauth/login")
	public @ResponseBody String testOAuthLogin(
			Authentication authentication,
			@AuthenticationPrincipal OAuth2User oauth) {
		System.out.println("/test/oauth/login ==============");
		OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal(); 
		// 리턴 타입 오브젝트라 > 이걸 다운 캐스팅으로 받아서 처리하고 
		
		// 그다음에 getUser로 처리한다. 
		System.out.println("authentication : "+ oauth2User.getAttributes());// 리턴 타입이 오브젝트다.
		System.out.println("oauth2User :" + oauth.getAttributes());
		return "OAuth 세션 정보 확인하기";
	}
	
	
	//localhost:8080/
	//localhost:8080
	@GetMapping({"","/"})
	public String index() {
		//머스테치 기본폴더 src/main/resources/
		// 뷰리졸버 설정 : /templates/ (prefix), .mustache (suffix) > 생략가능 
		return "index";
		//suffix가 저러니...
		//"src/main/resources/template/index.mustache" 이렇게 찾는다.
	}
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		return "user";
	}
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join") // 이게 실제 회원가입 처리
	public String join(User user) {
		System.out.println(user);
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		userRepository.save(user); // 이렇게 하면 회원가입이 잘되는데 비밀번호가 1234 들어간다. 
		// => 시큐리티로 로그인을 할 수 없다. 이유는 패스워드가 암호화가 안되었기 때문이다. 
		return "redirect:/loginForm";
	}
	
	// 만약에 info에서 권한 걸려서 못들어가져서 로그인 페이지로 빠지고 로그인하면 다시 info로 redirect하는 흐름이다. 
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/info") // 그냥 가진다. 이 주소를 시큐리티 설정에서 아무런 권한 처리를 안해서 그렇다.
	public @ResponseBody String info() {
		return "개인정보";
	}
	// 저 Secured를 거니까 로그인 페이지로 빠진다. 얘는 특정 메서드에 간단하게 걸고싶다면 얘로 걸면된다.

	// @PreAuthorize는 이 data라는 메서드가 실행되기 직전에 실행된다. > 얘는 hasRole을 사용해서 권한을 표시한다. 
	//@Secured("ROLE_ADMIN")
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	// @PostAuthorize > 근데 이건 쓸일이 별로 없다. 거의 preAuthorize만 쓴다. 라는 것도 있다. 얘는 함수가 끝나고 난 뒤에 뭘 하는거다. 
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "데이터정보";
	}
//	@GetMapping("/joinProc")
//	public @ResponseBody String joinProc() {
//		return "회원가입 완료됨!";
//	}
}


// 정리하면 
// 1. 글로벌로 걸기
// 2. 글로벌로 안걸었다면 애너테이션 기반으로 권한 걸기 
