package com.test.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.domain.BoardDTO;
import com.test.mapper.ThymeleafMapper;

@Controller
public class ThymeleafController {
	
	@Autowired
	private ThymeleafMapper mapper;
	
	@GetMapping("/m1")
	public void m1() {
		
		// 요청 메소드의 반환값 > void > m1.jsp 호출 
		// 요청 메소드의 반환값 > void > m1.html 호출
		System.out.println("m1");
	}
	
	/*
	@GetMapping("/m1")
	public void m1() {
		System.out.println("m1");
	}*/
	
	
	// 페이지 요청
	// 1. 동적 페이지("m2.html") > 요청 메소드를 통해서 >>  http://localhost:8092/m2 이거는 template 에 있는거 
	// 2. 정적 페이지("m2.html") >                         http://localhost:8092/m2.html > 이거는 static 에 있는거
	// 
	@GetMapping("/m2")
	public String m2() {
		//System.out.println("m2"); 
		return "m2";
	}
	
	@GetMapping("/m3")
	public String m3(Model model) {
		int num = mapper.getNum();
		String txt = mapper.getTxt();
		BoardDTO dto = mapper.getDTO();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("dog","강아지");
		map.put("cat","고양이");
		
		model.addAttribute("num", num);
		model.addAttribute("txt", txt);
		model.addAttribute("now", Calendar.getInstance());
		model.addAttribute("dto", dto);
		model.addAttribute("map", map);
		
		return "m3";
	}
	
	@GetMapping("/m4")
	public String m4(Model model) {
		int a = 10;
		int b = 3;
		model.addAttribute("a", a);
		model.addAttribute("b", b);
		return "m4";
	}
	
	@GetMapping("/m5")
	public String m5(Model model) {
		model.addAttribute("name", "age");
		model.addAttribute("size", 30);
		model.addAttribute("color", "cornflowerblue");
		return "m5";
	}
	
	@GetMapping("/m6")
	public String m6(Model model) {
		String name = mapper.getTxt();
		BoardDTO dto = mapper.getDTO();
		String txt = "안녕하세요. <i>홍길동</i> 입니다.";
		int num = 100;
		
		List<String> names = mapper.getNames();
		
		model.addAttribute("name", name);
		model.addAttribute("dto", dto);
		model.addAttribute("txt", txt);
		model.addAttribute("num", num);
		model.addAttribute("names", names);
		
		return "m6";
	}
	@GetMapping("/m7")
	public String m7(Model model) {
		int num1= 1234567;
		double num2 = 12345.6789;
		Calendar now = Calendar.getInstance();
		model.addAttribute("num1",num1);
		model.addAttribute("num2",num2);
		model.addAttribute("now",now);
		
		return "m7";
	}
	
	@GetMapping("/m8")
	public String m8(Model model) {
		int seq = 10;
		String mode = "add";
		model.addAttribute("seq",seq);
		model.addAttribute("mode",mode);
		return "m8";
	}
	
	@GetMapping("/m9")
	public String m9(Model model) {
		int num1 = 10;
		int num2 = 5;
		String mode = "add";
		model.addAttribute("num1", num1);
		model.addAttribute("num2", num2);
		model.addAttribute("mode", mode);
		return "m9";
	}
	
	@GetMapping("/m10")
	public String m10(Model model) {
		List<String> names = mapper.getNames();
		List<BoardDTO> list = mapper.getList();
		model.addAttribute("names", names);
		model.addAttribute("list", list);
		return "m10";
	}
	
	@GetMapping("/m11")
	public String m11(HttpSession session) {
		session.setAttribute("id", "hong");
		session.invalidate();
		return "m11";
	}

	@GetMapping("/m12")
	public String m12() {
		return "m12";
	}
}
