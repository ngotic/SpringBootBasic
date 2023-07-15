package com.test.mapper;

import java.util.List;

import com.test.domain.BoardDTO;

public interface ThymeleafMapper {

	int getNum();

	String getTxt();

	BoardDTO getDTO();

	List<String> getNames();
	
	List<BoardDTO> getList();

}
