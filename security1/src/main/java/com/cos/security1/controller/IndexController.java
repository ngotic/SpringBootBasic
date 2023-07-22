package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller //View를 리턴하겠다
public class IndexController { 
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
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
	public @ResponseBody String user() {
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
