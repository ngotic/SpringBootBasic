package com.test.mybatis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.mapper.MyBatisMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class MapperTest {
	@Autowired 
	private MyBatisMapper mapper;
	
	@Test
	public void testMapper() {
		log.info(mapper.time());
	}
	
}
