package net.rusb.servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rusb.dao.ArticleDao;
import net.rusb.dao.NewsDao;
import net.rusb.model.Article;
import net.rusb.model.User;
import net.rusb.utils.DateTool;
import net.rusb.utils.HTMLGenerator;
import net.rusb.utils.Utils;

public class PubArticleServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User curUser = (User)req.getSession().getAttribute("user");
		if(curUser!=null){
			String action = req.getParameter("action");
			String articleTitle = req.getParameter("articleTitle");
			String articleContent = req.getParameter("articleContent");
			String articleType = req.getParameter("articleType");
			String articleClass = req.getParameter("articleClass");
			String singlePage = req.getParameter("singlePage");
			String pageUrl = req.getParameter("pageUrl");
			String toped = req.getParameter("toped");
			String advised = req.getParameter("advised");
			String pubTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateTool.getCST());
			
			Article article = new Article();
			article.setArticleContent(articleContent);
			article.setArticleTitle(articleTitle);
			article.setArticleType(articleType);
			article.setClassId(articleClass);
			article.setSinglePage((singlePage!=null&&singlePage.equals("1"))?true:false);
			article.setToped((toped!=null&&toped.equals("1"))?true:false);
			article.setAdvised((advised!=null&&advised.equals("1"))?true:false);
			article.setPageUrl(pageUrl);
			article.setPubUser(curUser);
			article.setPubTime(pubTime);
			String articleId = null;
			ArticleDao articleDao = new ArticleDao();
			String returnUrl = "./../index.rusb";
			if(action!=null&&action.equals("add")){
				articleId = articleDao.addArticleAndGetId(article)+"";
				article.setArticleId(articleId);
				new NewsDao().addNews(curUser, article, NewsDao.ARTICLE_NEWS,HTMLGenerator.getBasePath(req));//生成最新动态
			}else if(action!=null&&action.equals("alter")){
				articleId = req.getParameter("articleId");
				article.setArticleId(articleId);
				articleDao.updateArticle(article);
				returnUrl = "./../articlelist.do";
			}
			System.out.println("articleId:"+articleId);
			if(article.isSinglePage()){
				article.setArticleId(articleId);
				String targetDir = getServletContext().getRealPath("/")+"pages";
				HTMLGenerator.generateHtmlArticlePage(article, targetDir, req);
			}
			resp.sendRedirect(returnUrl);
		}else{
			resp.sendRedirect("./../login.jsp");
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
