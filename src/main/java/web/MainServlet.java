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
		 //��ȡ����·��
		String path = req.getServletPath();
		if ("/findCost.do".equals(path)) {
			findcost(req,res);
		}else {
			throw new RuntimeException("û�����ҳ��");
		}
		
	}
	
	//��ѯ�ʷ�
	public void findcost(
			HttpServletRequest req, 
			HttpServletResponse res) 
					throws ServletException, IOException {
		//��ѯ���е��ʷ�
		CostDao dao = new CostDao();
		List<cost> list = dao.findAll();
		
		//������ת����jsp
		req.setAttribute("costs", list);
		req.getRequestDispatcher("WEB-INF/cost/find.jsp").forward(req, res);
	}
	
}
