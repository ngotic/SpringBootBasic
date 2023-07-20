package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	// 
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
	
//	@GetMapping("/joinProc")
//	public @ResponseBody String joinProc() {
//		return "회원가입 완료됨!";
//	}
}
