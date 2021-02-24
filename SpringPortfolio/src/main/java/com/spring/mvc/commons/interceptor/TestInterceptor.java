package com.spring.mvc.commons.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//인터셉터 클래스를 만드려면 HandlerInterceptorAdapter 클래스를 상속
public class TestInterceptor extends HandlerInterceptorAdapter {

	
	//preHandle은 특정 컨트롤러에 진입하기 전에 공통처리할 내용을 작성.
	@Override //pre + ctrl enter 오버라이딩 - 매개값의 형태는 같아야함(매개값을 함부로 수정할 수 없음)
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	
		System.out.println("테스트 인터셉터의 preHandle이 작동!!");
		
		
		return true; //true가 리턴되면 컨트롤러로 진입, false가 리턴되면 진입 실패
	}
	
	//PostHandle은 컨트롤러를 나갈때 공통처리 해야할 내용을 작성한다. post 쓰고 자동완성
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		System.out.println("테스트 인터셉터의 postHandle 작동!!");
		
		
		/*
		 * 
		 * 
		 * //test2요청에 대해서만 Hell World라는 모델 데이터를 보냄.
			@GetMapping("/test2")
			public String test2(Model model) {
			model.addAttribute("data" , "Hello World!");
		
			return "test/inter-test";
			}
	
		 * 
		 * 
		 */
		
		
		
		//InterceptorController Model에 담겨있는 EL data를 가져옴.
		Object data = modelAndView.getModel().get("data"); 
		/*
		 *  "data"라는 Model 객체를 가져와본다. 그러나  최초로 test1에 진입할때는
		 *  당연히 "data"라는 모델 객체가 없으니, 	if(data != null) { 이하 코드를 실행하지 않고 null값이 data에 들어간다.
		 *  
		 *  그리고 inner-test.jsp에서 test2로 이동을 누르면 다시 preHandle이 작동되고 그 후 postHandle이 뚫고 나올때 콘솔이 찍히는데,
		 *  이때는 test2메서드는 Model 객체가 있으므로 if(data != null) { 이하 코드를 실행을 하고
		 *  그 후 다시 "data"라는 세션객체를 만든 후, /test1으로 sendRedirect를 하기 때문에, test1 메서드가 다시 한번 작동해서
		 *  총 6번의 콘솔 출력결과가 찍히는것이다.
		 *  
		 * 
		 */
		
		if(data != null) {
			//매개값에 session을 담을 수 없으므로, requst를 통해서 세션을 담겠다.
			request.getSession().setAttribute("data", data); //세션에 담긴 "data"라는 모델 객체를 바인딩하고
			response.sendRedirect("/test1");  // test1으로 sendRedirect 되었다. 그러면 test1으로 이동
			
			
			
		}
	}
	
	
	
}
