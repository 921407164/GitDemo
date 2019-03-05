<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<img src="images/logo.png" alt="logo" class="left"/>
	<!-- EL默认从四个隐含对象中取值：
		page,request.session,application 
		他也有能力从cookie中取值，语法：
		cookie.name.value-->
	<%-- <span>当前登陆:${cookie.user.value}</span>--%>
	
	<span>当前登陆：${user}</span>
    <a onclick="location.href='toLogin.do';">退出</a>