package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


// 이걸 컨트롤러로 인식을 못한다. 
@Controller
public class MyBatisController {
	//http://localhost:8092/ex01.do
	@GetMapping("/ex01.do")
	public String ex01() {
		return "ex01";
	}
}
