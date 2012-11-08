package net.rusb.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rusb.dao.NewsDao;
import net.rusb.dao.TopicDao;
import net.rusb.model.Topic;
import net.rusb.model.User;
import net.rusb.utils.DateTool;
import net.rusb.utils.HTMLGenerator;

public class SubmitAnswerServlet extends HttpServlet{

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			User curUser = (User)req.getSession().getAttribute("user");
			String topicId = req.getParameter("topicid");
			RequestDispatcher dis = null;
			if(curUser!=null){
				TopicDao tDao = new TopicDao();
				String topicAnswer = req.getParameter("topicanswer");
				Topic topic = tDao.getTopicById(topicId, true, true);
				System.out.println(topic.getTopicName());
				topic.setTopicAnswer(topicAnswer);
				topic.setAnswerUser(curUser);
				topic.setAnswered(true);
				topic.setAnswerTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateTool.getCST()));
				tDao.updateTopic(topic);
				new NewsDao().addNews(curUser, topic, NewsDao.ANSWER_NEWS,HTMLGenerator.getBasePath(req));
				resp.sendRedirect("./index.rusb");
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