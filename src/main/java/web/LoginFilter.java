package web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(
			ServletRequest request, 
			ServletResponse response, 
			FilterChain chain)
			throws IOException, ServletException {
		//将参数转型
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		//有几个请求不需要过滤需要将他们排除
		String[] paths = new String[] {
				"/toLogin.do",
				"/login.do",
				"/createImg.do"
		};
		//获取当前路径
		String current = req.getServletPath();
		for(String s : paths) {
			if(s.equals(current)) {
				chain.doFilter(req, res);
				return;
			}
		}
		//从session中获取账号
		HttpSession session = req.getSession();
		Object user = session.getAttribute("user");
		//判断用户是否登陆
		if(user == null) {
			//未登录重定向
			//req.getContextPath()获取项目名
			res.sendRedirect(req.getContextPath()+"/toLogin.do");
		} else {
			//已登录
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		

	}

}
