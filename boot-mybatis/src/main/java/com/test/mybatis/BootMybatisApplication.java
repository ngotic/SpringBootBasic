package com.test.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// 스프링 부트 > 기본 스캔 대상 > 000Aplication 파일이 위치한 패키지 

// 여기다가 패키지를 지정하고 서버를 재시작한다. 
@SpringBootApplication
@ComponentScan(basePackages = {"com.test.controller"} )
@MapperScan(basePackages= {"com.test.mapper"})
public class BootMybatisApplication {
	public static void main(String[] args) {
		SpringApplication.run(BootMybatisApplication.class, args);
	}
}


