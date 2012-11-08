package net.rusb.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rusb.dao.ArticleDao;
import net.rusb.dao.MessageDao;
import net.rusb.dao.NewsDao;
import net.rusb.dao.TopicDao;
import net.rusb.model.Article;
import net.rusb.model.Message;
import net.rusb.model.Topic;
import net.rusb.model.User;
import net.rusb.utils.DateTool;
import net.rusb.utils.HTMLGenerator;
import net.rusb.utils.Utils;

public class DiscussServlet extends HttpServlet{

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			User curUser = (User)req.getSession().getAttribute("user");
			String topicId = req.getParameter("topicid");
			String articleId = req.getParameter("articleid");
			String returnUrl = null;
			RequestDispatcher dis = null;
			Object target =  null;
			boolean insertSuccess = false;
			if(curUser!=null){
				if(!curUser.isValided()){
					req.setAttribute("validInfo", "你的账号未通过验证，请先到”个人信息“进行验证");
					req.setAttribute("valided", new Boolean(false));
					dis = req.getRequestDispatcher("/valid.jsp");
					dis.forward(req, resp);
				}else{
					MessageDao mDao = new MessageDao();
					String messageContent = req.getParameter("messageContent");
					Message m = new Message();
					m.setDisUser(curUser);
					m.setMessageContent(messageContent);
					m.setSubTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateTool.getCST()));
					if(!Utils.isEmpty(topicId)){
						m.setTopicId(topicId);
						insertSuccess = mDao.addMessage(m);
						returnUrl = "./discusspri.do?topicid="+topicId;
						Topic topic = new TopicDao().getTopicById(topicId, false, false);
						target = topic;
					}
					if(!Utils.isEmpty(articleId)){
						m.setArticleId(articleId);
						insertSuccess = mDao.addMessage(m);
						String targetDir = getServletContext().getRealPath("/")+"pages";
						Article article = new ArticleDao().getArticleById(articleId, false);
						target = article;
						returnUrl = "./viewArticle.do?articleid="+articleId;
						if(article.isSinglePage()){
							//重新生成该文章的静态页面
							HTMLGenerator.generateHtmlArticlePage(article, targetDir, req);
							returnUrl = HTMLGenerator.getBasePath(req)+"/pages/"+article.getPageUrl();
						}
					}
					
					if(insertSuccess){
						NewsDao nDao = new NewsDao();
						nDao.addNews(curUser, target, NewsDao.DISCUSS_NEWS,HTMLGenerator.getBasePath(req));
					}
					
					resp.sendRedirect(returnUrl);
				}
			}else{
				System.out.println("未登录，不能提交答案！");
//				String returnUrl = req.getHeader("Referer");
//				req.setAttribute("returnUrl", returnUrl);
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