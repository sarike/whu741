package net.rusb.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rusb.dao.ArticleDao;
import net.rusb.dao.MessageDao;
import net.rusb.model.Article;
import net.rusb.model.Message;
import net.rusb.utils.Pager;

public class ViewArticleServlet extends WhuLayoutServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected org.apache.velocity.Template handleRequest(HttpServletRequest request, 
			HttpServletResponse response, org.apache.velocity.context.Context ctx) {
		String articleId = request.getParameter("articleid");
		ArticleDao aDao = new ArticleDao();
		Article article = aDao.getArticleById(articleId, true);
		MessageDao mDao = new MessageDao();
		Pager<Message> messagePager = mDao.listByProperty(MessageDao.ARTICLE_ID, articleId, 1);
		ctx.put("article", article);
		ctx.put("messagePager", messagePager);
		ctx.put(PAGE_TITLE, " | "+article.getArticleTitle());
		ctx.put(KEY_WORDS, " | "+article.getArticleTitle()+","+article.getArticleTags());
		return getTemplate("articleDetails.vm");
	}
}
