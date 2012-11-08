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

public class ValidEmailServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = req.getParameter("action");
		String validInfo = "";
		UserDao userDao = new UserDao();
		Boolean valided = false;
		if(action!=null&&action.equals("reValid")){
			String email = req.getParameter("email");
			User user = (User)req.getSession().getAttribute("user");
			String validCode = Utils.getRandomUniqueStr(30);
			if(user!=null&&userDao.updateUserByProperty(user, new String[]{"email","validcode"}, new String[]{email,validCode})){//验证登陆
				user.setEmail(email);
				user.setValidcode(validCode);
				SendEmail se = new SendEmail();
				List<String> addressList = new ArrayList<String>();
				addressList.add(email);
				se.send("亲:"+user.getNickname()+"("+user.getUsername()+")，点击下面链接完成注册，祝你在风流倜傥741学习开心！" +
						HTMLGenerator.getBasePath(req)+"/valid?id="+user.getUserid()+"&code="+user.getValidcode(),addressList);
				validInfo="一封验证邮件已经发送到：" +
					email+" 为了能更方便地与兄弟们交流，请你及时进行验证!";;
			}else{
				resp.sendRedirect("./login.jsp");
				return ;
			}
		}else{
			String userid = req.getParameter("id");
			String validCode = req.getParameter("code");
			User user = userDao.validEmail(userid, validCode);

			
			if(user == null){
				validInfo = "对不起，验证失败或者验证信息已经过期！请在“个人信息”处重新验证";
			}else{
				if(user.isValided()){
					validInfo = "恭喜你："+user.getNickname()+" 你已经通过验证，无需重复验证！";
					valided = true;
				}
				else
					if(userDao.updateUserByProperty(user, "valided", "1")){
						user.setValided(true);
						req.getSession().setAttribute("user", user);
						validInfo = "恭喜你："+user.getNickname()+"验证成功！";
						valided = true;
					}else{
						validInfo = "验证失败，可能是系统故障！";
					}
			}

		}
		req.setAttribute("validInfo", validInfo);
		req.setAttribute("valided", valided);
		RequestDispatcher dis = req.getRequestDispatcher("/valid.jsp");
		dis.forward(req, resp);
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
