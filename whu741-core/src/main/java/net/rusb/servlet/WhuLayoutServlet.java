package net.rusb.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.rusb.dao.ArticleDao;
import net.rusb.dao.ClassDao;
import net.rusb.dao.NewsDao;
import net.rusb.dao.TopicDao;
import net.rusb.dao.UserDao;
import net.rusb.model.Article;
import net.rusb.model.News;
import net.rusb.model.Topic;
import net.rusb.model.User;
import net.rusb.utils.Pager;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityLayoutServlet;

public class WhuLayoutServlet extends VelocityLayoutServlet {
	static final long serialVersionUID = new Long(1);
	
	public final static String PAGE_TITLE = "pageTitle";
	public final static String KEY_WORDS = "keyWords";
	
	@Override
	protected void fillContext(Context ctx, HttpServletRequest request) {
		// TODO Auto-generated method stub
		super.fillContext(ctx, request);
		TopicDao tDao = new TopicDao();
		//最热门的10个题目
		Pager<Topic> hotestTopicPager = tDao.listByPropertyWithPage(TopicDao.ALL, null,1,15,"viewcount desc");
		//10条最新动态
		Pager<News> newsPager = new NewsDao().listWithPage(1, 10);
		
		ArticleDao aDao = new ArticleDao();
		//最热门的10篇文章
		Pager<Article> hotestArticlePager = aDao.listByPropertyWithPage(ArticleDao.ALL, null, 1,10,"viewcount desc");
		//单页文章列表
		List<Article> singlePageList = aDao.getSinglePageList();
		//最近登录的10个用户
		Pager<User> userPager = new UserDao().listByLoginTime(1,10);
		//题目分类列表
		List<net.rusb.model.Class> classList = new ClassDao().getClasses();
		
		ctx.put("hotestTopicPager", hotestTopicPager);
		ctx.put("newsPager", newsPager);
		ctx.put("hotestArticlePager", hotestArticlePager);
		ctx.put("singlePageList", singlePageList);
		ctx.put("userPager", userPager);
		ctx.put("classes", classList);
		
		//也面的标题、关键字默认设置为空的，由于fillContext方法在handleRequest方法之前执行
		//所以可以在子类中的handleRequest方法中重置这两个变量
		ctx.put(PAGE_TITLE, "");
		ctx.put(KEY_WORDS, "");
	}
}
