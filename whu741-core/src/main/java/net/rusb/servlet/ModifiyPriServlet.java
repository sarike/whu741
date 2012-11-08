package net.rusb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rusb.dao.TopicDao;
import net.rusb.model.Topic;
import net.rusb.model.User;

public class ModifiyPriServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String topicId = req.getParameter("topicId");
		TopicDao tDao = new TopicDao();
		Topic t = tDao.getTopicById(topicId, true, false);
		User curUser = (User)req.getSession().getAttribute("user");
		if(curUser!=null){
			if(curUser.getUserid()==t.getPubUser().getUserid()){
				req.setAttribute("topic", t);
				req.setAttribute("action", "alert");
				RequestDispatcher dis = req.getRequestDispatcher("/admin/pubtopic.jsp");
				dis.forward(req, resp);	
			}else{
				System.out.println("您无权修改此题目！");
				RequestDispatcher dis = req.getRequestDispatcher("/index.rusb");
				dis.forward(req, resp);
			}
		}else{
			System.out.println("您未登录");
			RequestDispatcher dis = req.getRequestDispatcher("/login.jsp");
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
