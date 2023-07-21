package com.cos.security1.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

// 시큐리티 설정에서 loginProcessingUrl("/login");

// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행된다. > 규칙이다. 

@Service  // 얘는 서비스 로직이 확실함  > IoC로 등록되어 있어서 
public class PrincipalDetailsServce implements UserDetailsService{ // 그래서 UserDetailsService이걸 상속받아야함 

	@Autowired 
	private UserRepository userRepository;
	
	// 시큐리티 session(>내부 Authentication(>내부 UserDetails) ) 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//                                여기 username은 뷰단 input 태그에서 username으로 받아와야 여기랑 맞다. 
						// JPA 규칙으로 정의하면 됨
		
		System.out.println(username);
		User userEntity = userRepository.findByUsername(username);
		System.out.println(">>"+userEntity);
		
		if(userEntity != null) {
			return new PrincipalDetails(userEntity); 
			// 얘가 리턴이 되면 UserDetails이랑 매칭이 된다. > 그리고 그게 Authentication 내부에 들어간다.
			// 그리고 Authentication을 Security Session에 넣어준다. > 이렇게 로그인이 완료가 된다. 
		}
		return null;
	} 
	

}
