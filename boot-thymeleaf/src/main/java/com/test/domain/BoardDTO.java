package com.test.domain;

import lombok.Data;

@Data
public class BoardDTO {
	private String seq;
	private String id;
	private String subject;
	private String content;
	private String regdate;
}
