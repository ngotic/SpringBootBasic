package com.cos.security1.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;

@Configuration // IoC로 등록해주려고 
public class WebMvcConfig implements WebMvcConfigurer{
	
	// 이렇게 하면 재설정하는 것이다. 
	// 1. 인코딩, 내가 너한테 떤질 데이터는 html파일, 그리고 그 html파일은 UTF=8, 
	// classpath: 이것은 우리 프로젝트 경로이다. 
	// Suffix에 .html을 붙여서 인식하게 한다. 
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		MustacheViewResolver resolver = new MustacheViewResolver();
		resolver.setCharset("UTF-8");
		resolver.setContentType("text/html; charset=UTF-8");
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		
		registry.viewResolver(resolver); // 뷰 리졸버 등록 
	}

}
