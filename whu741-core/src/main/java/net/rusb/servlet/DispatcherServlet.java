package net.rusb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rusb.model.User;

public class DispatcherServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User curUser = (User)req.getSession().getAttribute("user");
		String targetUrl = req.getParameter("target");
		String sourceUrl = req.getRequestURL().toString();
		System.out.println("DispatcherServlet_sourceUrl:"+sourceUrl);
		if(curUser!=null){
			if(targetUrl!=null&&!targetUrl.equals("")){
				req.setAttribute("target", targetUrl);
				RequestDispatcher dis = req.getRequestDispatcher("./admin/admin.jsp");
				dis.forward(req, resp);
				
			}else{
				System.out.println("请求的路径不存在！");
			}
		}else{
			System.out.println("未登录");
			RequestDispatcher dis = req.getRequestDispatcher("./login.jsp");
			dis.forward(req, resp);
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
