package net.rusb.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.rusb.db.base.BaseDao;
import net.rusb.model.Article;
import net.rusb.model.News;
import net.rusb.model.Topic;
import net.rusb.model.User;
import net.rusb.utils.DateTool;
import net.rusb.utils.Pager;
import net.rusb.utils.SendEmail;

import org.apache.commons.dbutils.handlers.BeanListHandler;

public class NewsDao {
	
	public static final int DISCUSS_NEWS = 0;
	public static final int TOPIC_NEWS = 1;
	public static final int ARTICLE_NEWS = 2;
	public static final int ANSWER_NEWS = 4;
	
	BaseDao<News> baseDao = new BaseDao<News>();
	/**
	 * 增加一条新的动态
	 * @param user 动态的发起人
	 * @param target 针对的目标
	 * @param newsType 动态类型
	 */
	public void addNews(User user,Object target,int newsType,String webBasePath){
		String newsContent = null;
		String newsUrl = null;
		News news = new News();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		List<String> addressList = null;
		if(newsType==DISCUSS_NEWS){
			if(target instanceof Topic){
				Topic t = (Topic)target;
				newsUrl = "./discusspri.do?topicid=" + t.getTopicId();
				newsContent = "#"+user.getNickname()+"("+user.getUsername()+")# 在" + sdf.format(DateTool.getCST()) + " 激情地讨论了题目：("+t.getTopicName()+")！";
			}
			if(target instanceof Article){
				Article t = (Article)target;
				newsUrl = "./viewArticle.do?articleid=" + t.getArticleId();
				newsContent = "#"+user.getNickname()+"("+user.getUsername()+")# 在" + sdf.format(DateTool.getCST()) + " 评论了文章：("+t.getArticleTitle()+") ！";
			}
			addressList = new UserDao().getEmailAddrList(false, false, true);
		}
		if(newsType==TOPIC_NEWS){
			Topic t = (Topic)target;
			newsUrl = "./discusspri.do?topicid=" + t.getTopicId();
			newsContent = "#"+user.getNickname()+"("+user.getUsername()+")# 在" + sdf.format(new Date()) + " 倾情奉献了题目："+t.getTopicName();
			addressList = new UserDao().getEmailAddrList(false, true, false);
		}
		if(newsType==ARTICLE_NEWS){
			Article t = (Article)target;
			newsUrl = "./viewArticle.do?articleid=" + t.getArticleId();
			newsContent = "#"+user.getNickname()+"("+user.getUsername()+")# 在" + sdf.format(new Date()) + " 发表了文章：("+t.getArticleTitle()+") ！";
			addressList = new UserDao().getEmailAddrList(true, false, false);
		}
		if(newsType==ANSWER_NEWS){
			Topic t = (Topic)target;
			newsUrl = "./discusspri.do?topicid=" + t.getTopicId();
			newsContent = "#"+user.getNickname()+"("+user.getUsername()+")# 在" + sdf.format(new Date()) + " 犀利地解答了题目："+t.getTopicName();
		}
		news.setNewsContent(newsContent);
		news.setNewsUrl(newsUrl);
		SendEmail se = new SendEmail();
		if(addressList!=null&&addressList.size()>0){
			se.send(newsContent+"  连接地址："+webBasePath+newsUrl.substring(1), addressList);
			System.out.println(newsContent+"  连接地址："+webBasePath+newsUrl.substring(1));
		}else{
			System.out.println("邮件列表为空……");
		}
		addNews(news);
	}
	
	public void addNews(News news){
		String sql = "insert into news(newscontent,newsurl) values(?,?)";
		Object[] params = {news.getNewsContent(),news.getNewsUrl()};
		baseDao.update(sql, params);
	}
	
	public Pager<News> listWithPage(int pageIndex, int pageSize){
		String sql = "select * from news order by newsid desc";
		return baseDao.listByPage(sql, new BeanListHandler<News>(News.class), pageIndex, pageSize);
	}
	public Pager<News> listWithPage(int pageIndex){
		String sql = "select * from news order by newsid desc";
		return baseDao.listByPage(sql, new BeanListHandler<News>(News.class), pageIndex, Pager.DEFAULT_PAGE_SIZE);
	}
	public static void main(String[] args) {
//		News n = new News();
		NewsDao nd = new NewsDao();
//		n.setNewsContent("11111111111");
//		nd.addNews(n);
//		
//		n = new News();
//		n.setNewsContent("2222222222");
//		nd.addNews(n);
		Pager<News> p = nd.listWithPage(1, 10);
		for (News n : p.getVoList()) {
			System.out.println(n.getNewsContent());
		}
	}
}
