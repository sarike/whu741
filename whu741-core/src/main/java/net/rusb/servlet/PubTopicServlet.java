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
import net.rusb.model.Class;
import net.rusb.model.Topic;
import net.rusb.model.User;
import net.rusb.utils.DateTool;
import net.rusb.utils.HTMLGenerator;
import net.rusb.utils.Utils;

public class PubTopicServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User curUser = (User)req.getSession().getAttribute("user");
		String action = req.getParameter("action");
		String topicName = req.getParameter("topicname");
		String topicContent = req.getParameter("topiccontent");
//		String showTime = req.getParameter("showtime");
		String topicId = req.getParameter("topicId");
		String topicTags = req.getParameter("tags");
		String classId = req.getParameter("classes");
		Class topicClass = new Class();
		topicClass.setClassId(Integer.parseInt(classId));
		
		TopicDao td = new TopicDao();
		Topic topic = new Topic();
		if(!Utils.isEmpty(topicId)){
			topic = td.getTopicById(topicId, true, true);
		}
		if(topic.getAnswerUser()==null){
			User user = new User();
			user.setUserid(0);
			topic.setAnswerUser(user);
		}
//		Date date = new Date();
		Date date = DateTool.getCST();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String pubTime = sdf.format(date);
		
		topic.setTopicClass(topicClass);
		topic.setTopicTags(topicTags);
		topic.setTopicName(topicName);
		topic.setTopicContent(topicContent);
		topic.setPubTime(pubTime);
//		topic.setShowTime(showTime);

		//验证表单内容
		if(Utils.isEmpty(topicName)||Utils.isEmpty(topicContent)){
			req.setAttribute("error", "题目名称，题目内容均为必填项！");
			req.setAttribute("action", action);
			req.setAttribute("topic", topic);
			RequestDispatcher dis = req.getRequestDispatcher("/admin/pubtopic.jsp");
			dis.forward(req, resp);
		}else{
			if(action!=null&&action.equals("add")){//添加新的题目
				Integer userid = ((User)req.getSession().getAttribute("user")).getUserid();//当前用户（发布人）ID
				int id = td.addTopicAndGetId(topic, userid);//插入一条新的topics纪录并返回id
				topic.setTopicId(id);
				new NewsDao().addNews(curUser, topic, NewsDao.TOPIC_NEWS,HTMLGenerator.getBasePath(req));//生成最新动态
				resp.sendRedirect("./index.rusb");
			}else if(action!=null&&action.equals("alert")){//修改已有的题目
				try {
					td.updateTopic(topic);
					resp.sendRedirect("./index.rusb");
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					resp.sendRedirect("./index.rusb");
				}
			}			
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
