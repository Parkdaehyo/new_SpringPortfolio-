package com.spring.mvc.commons.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.spring.mvc.user.model.UserVO;
import com.spring.mvc.user.service.IUserService;

public class AutoLoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private IUserService service;
	
	//preHandle 컨트롤러 진입하기 전에 실행되는 인터셉터
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		//1. 자동 로그인 쿠키가 있는지 여부 확인
		
		// - 쿠키(loginCookie)의 존재 여부 확인 
		/* 
		 * WebUtils 객체 해석: request 요청정보에서, "loginCookie" 쿠키가 있다면 가지고 오고(get),
		 * 없다면 null값을 리턴한다. 
		 */
		Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
		
		if(loginCookie != null) { //자동로그인을 했다면~
			
			//2. DB에서 쿠키값과 일치하는 세션아이디를 가진 회원의 정보를 조회
			//getUserWithSessionId(sessionId);: 세션 아이디 추출하는것. 쿠키에서 그 세션 아이디를 가져오겠다.
			//getValue() 메서드: 쿠키값을 가져온다.
			
			/*
			 *  Cookie loginCookie = new Cookie("loginCookie" , session.getId()); 
			 * "loginCookie"에는 브라우저의 sessionId가 저장되어있다.
			 * loginCookie.getValue()은 그 값을 가져오는 것이다.
			 * 
			 * <select id="getUserWithSessionId" resultMap="UserMap">
	
				SELECT * FROM new_user
				WHERE session_id =#{sessionId}
	
			   </select>
			 * 
			 */
			
			
			UserVO user = service.getUserWithSessionId(loginCookie.getValue()); // 쿠키에서 그 세션 아이디를 가져오겠다.
			if(user != null) { //null이 아니라면 == 정상적으로 회원의 정보가 들어 왔다면, getUserWithSessionId에서 해당 회원의 조회문을 가져왔다. 
				
				//기존에는 loginCookie로 세션 id를 저장을 했다가 다시 로그인을 했으니, 다시 재설정해주는 작업인것 같다. 
				session.setAttribute("login", user); //이것을 쓰기 위해서 라인 25번 처럼 작성.
				
			}
			
		}
		
		
		return true;
	}
	
	
	
	
	
	
	
}
