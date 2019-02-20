package web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CostDao;
import entity.cost;

public class MainServlet extends HttpServlet {

	@Override
	protected void service(
			HttpServletRequest req, 
			HttpServletResponse res) throws ServletException, IOException {
		 //获取访问路径
		String path = req.getServletPath();
		if ("/findCost.do".equals(path)) {
			findcost(req,res);
		}else {
			throw new RuntimeException("没有这个页面");
		}
		
	}
	
	//查询资费
	public void findcost(
			HttpServletRequest req, 
			HttpServletResponse res) 
					throws ServletException, IOException {
		//查询所有的资费
		CostDao dao = new CostDao();
		List<cost> list = dao.findAll();
		
		//将请求转发到jsp
		req.setAttribute("costs", list);
		req.getRequestDispatcher("WEB-INF/cost/find.jsp").forward(req, res);
	}
	
}
