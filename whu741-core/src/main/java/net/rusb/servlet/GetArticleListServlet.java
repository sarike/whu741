package net.rusb.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rusb.dao.ArticleDao;
import net.rusb.model.Article;
import net.rusb.utils.Pager;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

public class GetArticleListServlet extends WhuLayoutServlet {
	static final long serialVersionUID = new Long(2);
	@Override
	protected Template handleRequest(HttpServletRequest request,
			HttpServletResponse response, Context ctx) {

		String pageIndex = request.getParameter("pageIndex");
		pageIndex = (pageIndex==null||pageIndex.equals(""))?"1":pageIndex;
		
		ArticleDao aDao = new ArticleDao();
		Pager<Article> articlePager = aDao.listByPropertyWithPage(ArticleDao.ALL, null, Integer.parseInt(pageIndex),null);
		
		ctx.put("articlePager", articlePager);
		return getTemplate("articleList.vm");
	}
}
