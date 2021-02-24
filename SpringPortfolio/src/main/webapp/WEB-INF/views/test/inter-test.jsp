<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h2>인터셉터 테스트</h2>
	<a href="/test2">test2로이동</a>

	<p>

		#데이터: ${data}
		
		<!-- test2 일때만 model에 담겨져있는 data라는 객체가 출력되서 Hello World가 나온다. -->


		<!-- 
	
	session.getAttribute("data") 

 

	이 코드를 EL로 쓰면 ${sessionScope.data} 입니다.

	EL 내장객체는 4가지 scope를 사용할 수 있는데요.
	
	pageScope, requestScope, sessionScope, applicationScope

	4가지 영역에 setAttribute를 해두었다면 언제든 EL로 꺼내쓸수 있습니다.

	하지만 4가지 영역에 같은 이름의 데이터가 없다면

	스코프를 생략할 수 있습니다.

 

	위와 같은 경우 인터셉터에서 session에 setAttribute("data")를 했죠?

	그럼 EL로 언제든지 ${data}를 사용하여 출력할수 있는겁니다.
	 -->


	</p>


</body>
</html>