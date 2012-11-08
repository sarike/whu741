package net.rusb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rusb.dao.UserDao;
import net.rusb.model.User;
import net.rusb.utils.HTMLGenerator;
import net.rusb.utils.SendEmail;
import net.rusb.utils.Utils;

public class UpdateUserInfoServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = (User)req.getSession().getAttribute("user");
		String nickname = req.getParameter("nickname").trim();
		String email = req.getParameter("email").trim();
		String articleRemind = req.getParameter("articleRemind")==null?"0":"1";
		String topicRemind = req.getParameter("topicRemind")==null?"0":"1";
		String messageRemind = req.getParameter("messageRemind")==null?"0":"1";
		String description = req.getParameter("description");
		String[] props = {"nickname","email","articleremind","topicremind","messageremind","description"};
		String[] values = {nickname,email,articleRemind,topicRemind,messageRemind,description};
		UserDao uDao = new UserDao();
		List<String> errorList = new ArrayList<String>();
		if(!Utils.isEmpty(nickname)){
			if(!Utils.isEmpty(email)&&email.indexOf("@")!=-1){
				uDao.updateUserByProperty(user, props, values);
				user.setNickname(nickname);
				user.setEmail(email);
				user.setDescription(description);
				user.setArticleRemind((articleRemind!=null&&articleRemind.equals("1"))?true:false);
				user.setTopicRemind((topicRemind!=null&&topicRemind.equals("1"))?true:false);
				user.setMessageRemind((messageRemind!=null&&messageRemind.equals("1"))?true:false);
				errorList.add("更新成功！");
			}else{
				errorList.add("邮箱需合法且不能为空！");
			}
		}else{
			errorList.add("昵称不能为空！");
		}
		req.setAttribute("errorList", errorList);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/userInfo.jsp");
		dispatcher.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
