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

public class DeleteTopicServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User curUser = (User)req.getSession().getAttribute("user");
		if(curUser!=null){
			TopicDao tDao = new TopicDao();
			String topicId = req.getParameter("topicId");
			Topic t = tDao.getTopicById(topicId, true, false);
			if(t.getPubUser().getUserid()==t.getPubUser().getUserid()){
				tDao.deleteById(topicId);
				resp.sendRedirect("./topiclist.do?type=personal");
			}else{
				System.out.println("您无权删除此题目！");
				resp.sendRedirect("./topiclist.do?type=personal");
			}
			
		}else{
			System.out.println("未登录！");
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
