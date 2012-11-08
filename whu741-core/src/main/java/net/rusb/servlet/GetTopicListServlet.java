package net.rusb.servlet;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import net.rusb.dao.TopicDao;
import net.rusb.model.Topic;
import net.rusb.model.User;
import net.rusb.utils.Pager;

public class GetTopicListServlet extends WhuLayoutServlet{
	static final long serialVersionUID = new Long(1);
	@Override
	protected Template handleRequest(HttpServletRequest request,
			HttpServletResponse response, Context ctx) {
		User curUser = (User)request.getSession().getAttribute("user");
		String type = request.getParameter("type");
		String pageIndex = request.getParameter("pageIndex");
		pageIndex = (pageIndex==null||pageIndex.equals(""))?"1":pageIndex;
		Pager<Topic> topicPager = new Pager<Topic>();
		TopicDao tDao = new TopicDao();
		if(type!=null&&type.equals("personal"))
		{
			//个人的题目列表，对应菜单“我发布的题目”
			topicPager = tDao.listByPropertyWithPage(TopicDao.USER_ID, curUser.getUserid().toString(), Integer.parseInt(pageIndex),null);
		}
		else if(type!=null&&type.equals("other"))
		{	
			//查看其它用户的题目列表
			String userid = request.getParameter("userid");
			topicPager = tDao.listByPropertyWithPage(TopicDao.USER_ID, userid, Integer.parseInt(pageIndex),null);
		}
		else if(type!=null&&type.equals("class"))
		{
			String classId = request.getParameter("classid");
			topicPager = tDao.listByPropertyWithPage(TopicDao.CLASS_ID,classId,Integer.parseInt(pageIndex),null);
		}
		else if(type!=null&&type.equals("tag"))
		{
			String tag = request.getParameter("tag");
			String encodeTag = null;
			try {
				encodeTag = new String(tag.getBytes("ISO-8859-1"),"UTF8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			topicPager = tDao.listByPropertyWithPage(TopicDao.TAG,encodeTag,Integer.parseInt(pageIndex),null);
		}
		else if(type!=null&&type.equals("keywords"))
		{
			String keywords = request.getParameter("keywords");
			String encodeKeywords = null;
			try {
				encodeKeywords = new String(keywords.getBytes("ISO-8859-1"),"UTF8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			topicPager = tDao.listByKewWordsWithPage(encodeKeywords,Integer.parseInt(pageIndex));
		}
		else 
		{
			topicPager = tDao.listByPropertyWithPage(TopicDao.ALL, null, Integer.parseInt(pageIndex),null);
		}
		
		System.out.println(topicPager.getVoList().size());
		ctx.put("topicPager", topicPager);
		
		return getTemplate("topicList.vm");
	}
}
