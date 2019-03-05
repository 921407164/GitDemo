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
		//������ת��
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		//�м���������Ҫ������Ҫ�������ų�
		String[] paths = new String[] {
				"/toLogin.do",
				"/login.do",
				"/createImg.do"
		};
		//��ȡ��ǰ·��
		String current = req.getServletPath();
		for(String s : paths) {
			if(s.equals(current)) {
				chain.doFilter(req, res);
				return;
			}
		}
		//��session�л�ȡ�˺�
		HttpSession session = req.getSession();
		Object user = session.getAttribute("user");
		//�ж��û��Ƿ��½
		if(user == null) {
			//δ��¼�ض���
			//req.getContextPath()��ȡ��Ŀ��
			res.sendRedirect(req.getContextPath()+"/toLogin.do");
		} else {
			//�ѵ�¼
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		

	}

}
