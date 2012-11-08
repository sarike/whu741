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

public class SubmitAnswerPriServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User curUser = (User)req.getSession().getAttribute("user");
		String topicId = req.getParameter("topicid");
		RequestDispatcher dis = null;
		if(curUser!=null){
			TopicDao tDao = new TopicDao();
			Topic topic = tDao.getTopicById(topicId,true,true);
			System.out.println(topic.toString());
			req.setAttribute("topic", topic);
			dis = req.getRequestDispatcher("/admin/submitanswer.jsp");
			dis.forward(req, resp);
		}else{
			System.out.println("未登录，不能提交答案！");
			dis = req.getRequestDispatcher("/login.jsp");
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
