package com.spring.mvc.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.mvc.user.model.UserVO;
import com.spring.mvc.user.repository.IUserMapper;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserMapper mapper;
	
	//회원가입을 처리하는 메서드
	@Override
	public void register(UserVO user) {
		
		//회원 비밀번호를 암호화 인코딩 객체 생성
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		/*
		 *  암호화 되기 전 패스워드 UserVO의 user.getPassword()
		 
		 	const pw = $("#password").val(); //146번 라인
			console.log("pw: " + pw);
		 
		 	//Json형태로 객체만들기
			const user = { //자바스크립트의 객체 은 자바스크립트의 표기법을 따른다.
				account: id, //KEY : VALUE 키(왼쪽) 값은 VO의 멤버변수와 같도록한다.
				password: pw,
				name: name,
				phoneNum : phoneNum
			};
		 */
		System.out.println("암호화 전 패스워드" + user.getPassword());  //이 getPassword()는 이미 JSON객체로 만들어졌을때  회원가입창에서 input id="password"에서 등록된 값이다.
		
		//비밀번호를 암호화하여 다시 user객체에 저장.
		//encode메서드는 리턴값이 String. 암호화되기전 패스워드를,
		//encoder.으로 접근해서 String securePw에 저장.
		String securePw = encoder.encode(user.getPassword()); //입력된 패스워드를 encode() 메서드에 매개로 받도록 설정.
		user.setPassword(securePw); // 그리고 그 암호화된 코드를 다시 setter로 작업해서 저장.
		System.out.println("암호화 후: " + securePw);
		
		mapper.register(user); //이 명령을 실행하면 db에 가게된다

	}
	
	@Override
	public Integer checkId(String account) {
		
		return mapper.checkId(account);
	}
	
	@Override
	public UserVO findid(UserVO user) {
		//System.out.println("유저정보 : " + user);
		return mapper.findid(user);
	}
	
	@Override
	public void delete(String account) {
		mapper.delete(account);
		
	}
	
	@Override
	public UserVO selectOne(String account) {
		
		return mapper.selectOne(account);
	}
	

	@Override
	public List<UserVO> selectAll() {
		
		return mapper.selectAll();
	}

	//회원 수정 기능
	@Override
	public void memberupdate(UserVO uservo) {
	
	mapper.memberupdate(uservo);

}
	
	
	
	@Override
	public void keepLogin(String sessionId, Date limitDate, String account) {
		
		//xml파일에게 데이터를 전달할때는 각각으로 주지말고,
		//Map같은것으로 포장해서 한번에 전달해야 한다고 한다.
		
		Map<String,Object> datas = new HashMap<>();
		datas.put("sessionId", sessionId);
		datas.put("limitDate", limitDate);
		datas.put("account", account);
		
		mapper.keepLogin(datas);
		
	}
	
	@Override
	public UserVO getUserWithSessionId(String sessionId) {
		
		return mapper.getUserWithSessionId(sessionId);
	}

	
	
}
