package com.cos.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

// 이렇게 붙이고나서 SecurityConfig로 가서 
@Service 
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	// 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수다. 
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("getClientRegistration : "+ userRequest.getClientRegistration()); // 이런게 있다.
		System.out.println("getAccessToken : "+ userRequest.getAccessToken().getTokenValue()); // 이런게 있다.
		System.out.println("getAttributes : "+ super.loadUser(userRequest).getAttributes()); // 이런게 있다.
		return super.loadUser(userRequest);
	}
	
	// 여기는 코드를 받는게 아니라 코드를 통해 액세스토큰을 받고 사용자 프로필 정보도 받은 상태이다.
	
	// 요게 accessToken이다. 
	
	// ya29.a0AbVbY6Ny3erGwn8Jc7DxJPKLW6e9mFvggMwFVw0Lxzvk0lB2dbEqqWFd04RbUexnGRE2q_vHUTtwjmoPhS5-xj6m9o_SX7NyPSVVJ83CeaSEvMdxj0-JLoSZMYjwcCHcrtYldvQnAEWYSadpjDzcRmVEDMOeCwaCgYKAYYSARMSFQFWKvPl2lzJibrz1X6fVNg1EgwIng0165
	
	
	//getClientRegistration : ClientRegistration{registrationId='google', clientId='494348329764-k3r1mh1l9ds1pptug84n7q2cu9b5bm0u.apps.googleusercontent.com', clientSecret='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', clientAuthenticationMethod=org.springframework.security.oauth2.core.ClientAuthenticationMethod@4fcef9d3, authorizationGrantType=org.springframework.security.oauth2.core.AuthorizationGrantType@5da5e9f3, redirectUri='{baseUrl}/{action}/oauth2/code/{registrationId}', scopes=[email, profile], providerDetails=org.springframework.security.oauth2.client.registration.ClientRegistration$ProviderDetails@3160e7e7, clientName='Google'}
	//getAccessToken : org.springframework.security.oauth2.core.OAuth2AccessToken@f3f0f557
	
	// 여기 sub라는 것은 pk 같은 느낌이다. 
	// name, given_name, family_name, picture, email, email_varified, locale 
	//getAttributes : {sub=102717622804956166665, name=hello world, given_name=hello, family_name=world, picture=https://lh3.googleusercontent.com/a/AAcHTtfPJz364pj19k5l2Pj0pwrqfFdGGOZSc3TrH-tknNPD=s96-c, email=ngotic2888@gmail.com, email_verified=true, locale=ko}
	
}

// 이제 가입을 할 때 google_102717622804956166665
// password는 암호화해서 넣는다. > 어차피 구글 로그인으로 로그인 되는 회원이다. 
// email은 그대로 넣는다. ngotic2888@gmail.com 이대로 회원가입을 진행한다. 
// role은 ROLE_USER를 넣을 것이다. 이러면 얘가 일반적인 사용자인지 아닌지 모른다. 

// 사실상 getAttributes 정보로 강제 회원가입을 시키는 것이다.
