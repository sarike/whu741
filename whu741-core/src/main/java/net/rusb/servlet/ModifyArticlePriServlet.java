package net.rusb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rusb.dao.ArticleDao;
import net.rusb.model.Article;
import net.rusb.model.User;

public class ModifyArticlePriServlet extends HttpServlet {
	
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
			String articleId = req.getParameter("articleid");
			ArticleDao aDao = new ArticleDao();
			Article article = aDao.getArticleById(articleId, true);
			req.setAttribute("article", article);
			req.setAttribute("action", "alter");
			RequestDispatcher dis = req.getRequestDispatcher("./admin/pubarticles.jsp");
			dis.forward(req, resp);
			
		}else{
			resp.sendRedirect("./login.jsp");
		}
	}
}
