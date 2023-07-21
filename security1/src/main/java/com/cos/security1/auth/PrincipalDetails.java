package com.cos.security1.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다. 
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어준다. 
// > 시큐리티가 자신만의 시큐리티 세션공간을 가진다.
// (Security ContextHolder)라는 키값 여기에다가 세션 정보를 저장한다.
// 오브젝트가 정의되어 있다. Authentication 타입 객체

// Authentication 안에는 User 정보가 있어야 된다. 
// User오브텍트 타입 => UserDetails 타입 객체


// Security Session 여기다가 세션 정보를 저장한다. \
// 여기 들어가는 객체가 Authentication 객체로 정해져있다.
// 또 그 안에서 UserDetails를 꺼내면 유저 오브텍트에 접근할 수 있다. 


// public class PrincipalDetails implements UserDetails
// 이렇게 상속관계로 한다. 그러면 PrincipalDetails를 UserDetails 와 연결시켜서 Authentication객체에 넣을 수 있다. 

// Authentication을 만들어서 이 객체를 넣어야 하는데 어떻게 만드나? 

// 얘는 일단 어노테이션 없음 
public class PrincipalDetails implements UserDetails{

	
	// Override하게 되면 이렇게 쭉 나온다. 
	private User user; // 콤포지션 
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	
	
	// 해당 User의 권한을 리턴하는 곳!! 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// user.getRole() // 이렇게 이걸 리턴해주는데 이걸 타입에 맞게 처리하기 위해 정의필요하다.
		
		Collection<GrantedAuthority> collect = new ArrayList<>();
		
		collect.add(new GrantedAuthority() { // 이건 람다함수였음. > Interface 이다. 

			@Override
			public String getAuthority() { 
				// TODO Auto-generated method stub
				return user.getRole();
			}
			
		}); 
	
		return collect;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() { // 계정이 만료되지 않았는지를 리턴한다. true를 리턴하면 만료되지 않음 
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { // 계정이 잠겨있지 않은지를 리턴한다. true를 리턴하면 계정이 잠겨있지 않음을 의미 
		return true;
	}

	@Override  // 너무 오래 안들어오면 ... 만료되니까 ? 
	public boolean isCredentialsNonExpired() { // 계정의 패스워드가 만료되지 않았는지를 리턴한다. (true를 리턴하면 패스워드가 만료되지 않음)
		return true;
	}

	@Override
	public boolean isEnabled() { // 
		// 이 계정이 활성화되어있니? true > 사용가능하다. 
		// 우리 사이트 !! 1년 동안 회원이 로그인을 안하면!! 휴먼 계정으로 하기로 함.
		// 현재시간 - 로그인시간 => 1년을 초과하면 return false; > 이런식으로도 할 수 있는데 당장은 하지 않을 것이다.
		// user.getLoginDate(); 이렇게 꺼내서 관리가 가능
		
		return true;
	}

}
