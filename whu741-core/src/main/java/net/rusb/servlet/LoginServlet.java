package net.rusb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rusb.dao.UserDao;
import net.rusb.model.User;

public class LoginServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserDao uDao = new UserDao();
		String username = req.getParameter("username").trim();
		String password = req.getParameter("password").trim();
		User user = uDao.validUser(username,password);
		
		if(user!=null){
			req.getSession(true).setAttribute("user", user);
			resp.sendRedirect("./index.rusb");
		}else{
			req.setAttribute("error", "登录失败！");
			RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
			dispatcher.forward(req, resp);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
