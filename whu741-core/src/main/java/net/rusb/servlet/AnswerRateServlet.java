package net.rusb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rusb.dao.MessageDao;
import net.rusb.model.User;

public class AnswerRateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User curUser = (User)req.getSession().getAttribute("user");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.append("{");
		if(curUser!=null){
			String disscussId = req.getParameter("msgId");
			boolean uped = false;
			String cookieName = "upScore"+disscussId;
			Cookie[] cookies = req.getCookies();
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(cookieName)&&cookie.getValue().equals("uped"))
					uped = true;
			}
			if(!uped){
				MessageDao msgDao = new MessageDao();
				int newScore = msgDao.upScore(Long.parseLong(disscussId));
				Cookie upScore = new Cookie(cookieName, "uped");
				upScore.setMaxAge(3600*10);
				resp.addCookie(upScore);
				out.append("\"note\":\"success\",");
				out.append("\"messageid\":\""+disscussId+"\",");
				out.append("\"score\":\""+newScore+"\"");
			}else{
				out.append("\"note\":\"uped\",");
			}
		}else{
			out.append("\"note\":\"nolog\"");
		}
		out.append("}");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
