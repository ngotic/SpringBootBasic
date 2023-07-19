package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //View를 리턴하겠다
public class IndexController {
	
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
	@GetMapping("/login")
	public @ResponseBody String login() {
		return "login";
	}
	@GetMapping("/join")
	public @ResponseBody String join() {
		return "join";
	}
	@GetMapping("/joinProc")
	public @ResponseBody String joinProc() {
		return "회원가입 완료됨!";
	}
}
