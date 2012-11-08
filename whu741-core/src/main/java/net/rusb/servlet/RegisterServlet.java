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

public class RegisterServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = new User();
		String username = req.getParameter("username").trim();
		String password = req.getParameter("password").trim();
		String pswagain = req.getParameter("pswagain").trim();
		String nickname = req.getParameter("nickname").trim();
		String email = req.getParameter("email").trim();
		String description = req.getParameter("description");
		System.out.println(username);
		user.setUsername(username);
		user.setPassword(password);
		user.setNickname(nickname);
		user.setEmail(email);
		user.setTopicRemind(true);
		user.setMessageRemind(true);
		user.setArticleRemind(true);
		user.setDescription(description);
		user.setValided(false);
		user.setValidcode(Utils.getRandomUniqueStr(30));
		
		UserDao uDao = new UserDao();
		String regInfo = null;
		boolean isValid = false;
		if(!Utils.isEmpty(username))
		{
			if(!uDao.isExist(username)){
				if(!Utils.isEmpty(password)&&!Utils.isEmpty(pswagain)&&password.equalsIgnoreCase(pswagain)){
					if(!Utils.isEmpty(nickname)){
						if(!Utils.isEmpty(email)&&email.indexOf("@")!=-1){
							int userid = uDao.addUser(user);
							if(userid !=-1){
								SendEmail se = new SendEmail();
								List<String> addressList = new ArrayList<String>();
								addressList.add(user.getEmail());
								se.send("恭喜你:"+user.getNickname()+"("+user.getUsername()+")，" +
										"点击下面链接完成注册：" +
										HTMLGenerator.getBasePath(req)+"/valid?id="+userid+"&code="+user.getValidcode(),addressList);
								regInfo = "恭喜你:"+user.getNickname()+"("+user.getUsername()+")，" +
									"注册成功！<br/>一封验证邮件已经发送到：" +
									user.getEmail()+" 为了能更方便地与兄弟们交流，请你及时进行验证!<br/>" +
											"<font color='red'>如果您未能收到验证邮件，请登陆以后在个人信息处修改正确的邮箱并重新进行验证。</font>";
								isValid = true;
							}else{
								regInfo="注册失败，可能是系统故障！";
							}
						}else{
							regInfo="邮箱需合法且不能为空！";
						}
					}else{
						regInfo="昵称不能为空！";
					}
				}else{
					regInfo="密码不能为空且两次密码应保持一致！";
				}
			}else{
				regInfo="该用户名已经存在！";
			}
			
		}else{
			regInfo="用户名不能为空！";
		}
		if(isValid){
			req.setAttribute("user", user);
			req.setAttribute("regInfo", regInfo);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/result.jsp");
			dispatcher.forward(req, resp);
		}else{
			req.setAttribute("errorList", regInfo);
			req.setAttribute("user", user);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/register.jsp");
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
