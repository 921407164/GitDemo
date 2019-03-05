package web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CostDao;
import dao.adminDao;
import entity.admin;
import entity.cost;
import util.ImageUtil;

public class MainServlet extends HttpServlet {

	@Override
	protected void service(
			HttpServletRequest req, 
			HttpServletResponse res) throws ServletException, IOException {
		 //��ȡ����·��
		String path = req.getServletPath();
		if ("/findCost.do".equals(path)) {
			findcost(req,res);
		}else if("/toAddCost.do".equals(path)){
			toAddCost(req,res);
		}else if("/addcost.do".equals(path)) {
			addcost(req,res);
		}else if("/toUpdateCost.do".equals(path)) {
			//���޸��ʷ�ҳ
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
		}else if("/createImg.do".equals(path)) {
			creatImg(req,res);
		}else{
			throw new RuntimeException("û�����ҳ��");
		}
	}
	//������֤��
	protected void creatImg(
			HttpServletRequest req, 
			HttpServletResponse res) 
					throws ServletException, IOException {
		//������֤���Լ�ͼƬ
		Object[] objs = ImageUtil.createImage();
		//����֤����뵽session
		HttpSession session = req.getSession();
		session.setAttribute("imgcode",objs[0]);
		//��ͼƬ���͸������
		res.setContentType("image/png");
		//��ȡ�ֽ����������ɷ�����������
		//��Ŀ����ǵ�ǰ���ʵ��Ǹ������
		OutputStream out =  res.getOutputStream();
		BufferedImage im = (BufferedImage)objs[1];
		ImageIO.write(im, "png", out);
		out.close();
	}
	//��½
	protected void login(
			HttpServletRequest req, 
			HttpServletResponse res) 
					throws ServletException, IOException {
		//��ȡseesion
		HttpSession session =  req.getSession();
		//���ܲ���
		String adminCode = req.getParameter("adminCode");
		String password = req.getParameter("password");
		//��֤
		adminDao admindao = new adminDao();
		admin a = admindao.findCode(adminCode);
		//��֤��
		String code = req.getParameter("code");
		String imgcode = (String)session.getAttribute("imgcode");
		//�����֤��
		if(code == null || !code.equals(imgcode)){
			//��֤�����
			req.setAttribute("error", "��֤�����");
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
			return;
		}
		//����˺�����
		if(a == null){
			//�˺Ŵ���
			req.setAttribute("error", "�˺Ŵ���");
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
		} else if (!a.getPassword().equals(password)) {
			req.setAttribute("error", "�������");
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
			//�������
		} else {
			//��֤�ɹ�
			//���˺Ŵ���cookie
			//Cookie cookie = new Cookie("user", adminCode);
			//res.addCookie(cookie);
			//���˺Ŵ���session
			session.setAttribute("user", adminCode);
			//�ض���
			res.sendRedirect("toIndex.do");
		}
		
	}
	//�򿪵�½ҳ��
	public void toLogin(
			HttpServletRequest req, 
			HttpServletResponse res) 
					throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
	}
	//���ʵ���ҳ
	public void toIndex(
			HttpServletRequest req, 
			HttpServletResponse res) 
					throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/index.jsp").forward(req, res);
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
	//�����ʷ�
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
		//����ҳ�洫��Ĳ���
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
		//�ض��򵽲�ѯ
		//��ǰ·��/nettoss/addcostost.do
		//Ŀ��·��/nettoss/addcostost.do
		res.sendRedirect("findCost.do");
	}
	
	//����id����cost������
	protected void toUpdateCost(
			HttpServletRequest req,
			HttpServletResponse res) 
			throws ServletException, IOException {
			//�����������
			String id = req.getParameter("id");
			//��ѯ���ʷ�����
			CostDao dao = new CostDao();
			cost cost = 
				dao.findcost(new Integer(id));
			//ת�����޸�ҳ��
			//��ǰ:/netctoss/toUpdateCost.do
			//Ŀ��:/netctoss/WEB-INF/cost/cost_update.jsp
			req.setAttribute("cost", cost);
			req.getRequestDispatcher(
				"WEB-INF/cost/update.jsp")
				.forward(req, res);
		}
	//�޸��ʷ�
	protected void updateCost(
			HttpServletRequest req,
			HttpServletResponse res) 
			throws ServletException, IOException{
			req.setCharacterEncoding("utf-8");
			//��ȡ�������
			//���������ֿ�������������
			//Ϊ�˱��ڼ���ͨ����ʵ������ͬ����
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
			//�޸���Щ����
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
			CostDao dao  = new CostDao();
			dao.update(c);
			//�ض��򵽲�ѯ
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

