package web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CostDao;
import dao.adminDao;
import entity.admin;
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
		}else if("/toAddCost.do".equals(path)){
			toAddCost(req,res);
		}else if("/addcost.do".equals(path)) {
			addcost(req,res);
		}else if("/toUpdateCost.do".equals(path)) {
			//打开修改资费页
			toUpdateCost(req, res);
		}else if("/updateCost.do".equals(path)) {
			updateCost(req, res);
		}else if("/toLogin.do".equals(path)){
			toLogin(req,res);
		}else if("/toIndex.do".equals(path)) {
			toIndex(req,res);
		}else if("/login.do".equals(path)) {
			login(req,res);
		} else if("/delete.do".equals(path)) {
			delete(req,res);
		}else{
			throw new RuntimeException("没有这个页面");
		}
	}
	//登陆
	protected void login(
			HttpServletRequest req, 
			HttpServletResponse res) 
					throws ServletException, IOException {
		//接受参数
		String adminCode = req.getParameter("adminCode");
		String password = req.getParameter("password");
		//验证
		adminDao admindao = new adminDao();
		admin a = admindao.findCode(adminCode);
		if(a == null){
			//账号错误
			req.setAttribute("error", "账号错误");
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
		} else if (!a.getPassword().equals(password)) {
			req.setAttribute("error", "密码错误");
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
			//密码错误
		} else {
			//验证成功
			res.sendRedirect("toIndex.do");
		}
		
	}
	//打开登陆页面
	public void toLogin(
			HttpServletRequest req, 
			HttpServletResponse res) 
					throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
	}
	//访问到主页
	public void toIndex(
			HttpServletRequest req, 
			HttpServletResponse res) 
					throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/index.jsp").forward(req, res);
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
	//增加资费
	public void toAddCost(
			HttpServletRequest req, 
			HttpServletResponse res) 
					throws ServletException, IOException {

		req.getRequestDispatcher("WEB-INF/cost/add.jsp").forward(req, res);
	}
	
	public void addcost(
			HttpServletRequest req, 
			HttpServletResponse res) 
					throws ServletException, IOException {
		//接受页面传入的参数
		req.setCharacterEncoding("utf-8");
		String name = req.getParameter("name");
		String Type = req.getParameter("Type");
		String baseDuration = req.getParameter("baseDuration");
		String baseCost = req.getParameter("baseCost");
		String unitCost = req.getParameter("unitCost");
		String descr = req.getParameter("descr");
		cost  cost = new cost();
		cost.setName(name);
		cost.setCostType(Type);
		if(baseDuration != null && baseDuration.length()>0) {
			cost.setBaseDuration(Integer.valueOf(baseDuration));
		}
		if(baseCost != null && baseCost.length()>0) {
			cost.setBaseCost(Double.valueOf(baseCost));
		}
		if(unitCost != null && unitCost.length()>0) {
			cost.setUnitCost(Double.valueOf(unitCost));
		}
		cost.setDescr(descr);
		CostDao costDao = new CostDao();
		costDao.save(cost);
		//重定向到查询
		//当前路径/nettoss/addcostost.do
		//目标路径/nettoss/addcostost.do
		res.sendRedirect("findCost.do");
	}
	
	//根据id查找cost的请求
	protected void toUpdateCost(
			HttpServletRequest req,
			HttpServletResponse res) 
			throws ServletException, IOException {
			//接收请求参数
			String id = req.getParameter("id");
			//查询该资费数据
			CostDao dao = new CostDao();
			cost cost = 
				dao.findcost(new Integer(id));
			//转发到修改页面
			//当前:/netctoss/toUpdateCost.do
			//目标:/netctoss/WEB-INF/cost/cost_update.jsp
			req.setAttribute("cost", cost);
			req.getRequestDispatcher(
				"WEB-INF/cost/update.jsp")
				.forward(req, res);
		}
	//修改资费
	protected void updateCost(
			HttpServletRequest req,
			HttpServletResponse res) 
			throws ServletException, IOException{
			req.setCharacterEncoding("utf-8");
			//获取请求参数
			//参数的名字可以任意命名，
			//为了便于记忆通常和实体属性同名。
			String costId = 
				req.getParameter("costId");
			String name = 
				req.getParameter("name");
			String baseDuration = 
				req.getParameter("baseDuration");
			String baseCost =
				req.getParameter("baseCost");
			String unitCost = 
				req.getParameter("unitCost");
			String descr = 
				req.getParameter("descr");
			String costType = 
				req.getParameter("costType");
			//修改这些数据
			cost c = new cost();
			c.setCostID(new Integer(costId));
			c.setName(name);
			c.setDescr(descr);
			c.setCostType(costType);
			if(baseDuration != null
				&& !baseDuration.equals("")) {
				c.setBaseDuration(
					new Integer(baseDuration));
			}
			if(baseCost != null
				&& !baseCost.equals("")) {
				c.setBaseCost(
					new Double(baseCost));
			}
			if(unitCost != null
				&& !unitCost.equals("")) {
				c.setUnitCost(
					new Double(unitCost));
			}
			CostDao dao = new CostDao();
			dao.update(c);
			//重定向到查询
			res.sendRedirect("findCost.do");
		}
	
	protected void delete(
			HttpServletRequest req,
			HttpServletResponse res) 
			throws ServletException, IOException{
		String id = req.getParameter("id");
		//int id = Integer.valueOf(req.getParameter("cost_Id"));
		CostDao dao = new CostDao();
		dao.delete(new Integer(id));
		res.sendRedirect("findCost.do");
		
	}
}

