package net.rusb.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rusb.dao.ArticleDao;
import net.rusb.dao.TopicDao;
import net.rusb.model.Article;
import net.rusb.model.Topic;
import net.rusb.utils.Pager;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

public class ShowIndexServlet extends WhuLayoutServlet {
	static final long serialVersionUID = new Long(1);
	@Override
	protected Template handleRequest(HttpServletRequest request,
			HttpServletResponse response, Context ctx) {
		TopicDao tDao = new TopicDao();
		//最新的10个题目
		Pager<Topic> topicPager = tDao.listByPropertyWithPage(TopicDao.ALL, null,1,10,null);
		
		ArticleDao aDao = new ArticleDao();
		//最新的10篇文章
		Pager<Article> latestArticlePager = aDao.listByPropertyWithPage(ArticleDao.ALL, null, 1,10,null);
		
		ctx.put("topicPager", topicPager);
		ctx.put("articlePager", latestArticlePager);
		return getTemplate("index.vm");
	}
}
