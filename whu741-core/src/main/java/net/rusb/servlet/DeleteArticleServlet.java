package net.rusb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rusb.dao.ArticleDao;
import net.rusb.dao.MessageDao;
import net.rusb.model.User;

public class DeleteArticleServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User curUser = (User)req.getSession().getAttribute("user");
		if(curUser!=null){
			if(curUser.getAuthority()!=null&&curUser.getAuthority().equals("1")){
				String articleId = req.getParameter("articleId");
				MessageDao mDao = new MessageDao();
				mDao.deleteByArticleId(articleId);
				ArticleDao aDao = new ArticleDao();
				aDao.deleteArticleById(articleId);
				resp.sendRedirect("./articlelist.do");
			}else{
				System.out.println("没有权限");
				resp.sendRedirect("./articlelist.do");
			}
			
		}else{
			resp.sendRedirect("./login.jsp");
		}
	}
}
