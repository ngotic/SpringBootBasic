package com.cos.security1.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {
	
	@Id //PK 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;
	private String role; // ROLE_USER, ROLE_ADMIN // 근데 여러개 넣을 수 있으면 좋은데...
	
	// private Timestamp loginDate; // 이런 칼럼이 있으면 로그인할 때마다 날짜를 찎는데 > 적지마라 
	private String provider;   // 여기엔 google
	private String providerId; // 숫자같은거 넣는다.  
	
	@CreationTimestamp
	private Timestamp createDate;

	@Builder
	public User(String username, String password, String email,
			String role, String provider, String providerId, Timestamp createDate) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
		this.createDate = createDate;
	}	
	
}
