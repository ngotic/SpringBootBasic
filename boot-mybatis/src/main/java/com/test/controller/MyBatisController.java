package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.mapper.MyBatisMapper;


// 이걸 컨트롤러로 인식을 못한다. 
@Controller
public class MyBatisController {
	//http://localhost:8092/ex01.do
	
	@Autowired 
	private MyBatisMapper mapper;
	
	@GetMapping("/ex01.do")
	public String ex01(Model model) {
		String time = mapper.time();
		model.addAttribute("time", time);
		return "ex01";
	}
}
