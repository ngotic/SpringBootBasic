package com.cos.security1.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
public class User {
	
	@Id //PK 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;
	private String role; // ROLE_USER, ROLE_ADMIN // 근데 여러개 넣을 수 있으면 좋은데...
	
	// private Timestamp loginDate; // 이런 칼럼이 있으면 로그인할 때마다 날짜를 찎는데 > 적지마라 
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
