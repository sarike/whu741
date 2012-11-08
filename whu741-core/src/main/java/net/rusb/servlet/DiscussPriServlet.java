package net.rusb.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import net.rusb.dao.MessageDao;
import net.rusb.dao.TopicDao;
import net.rusb.model.Message;
import net.rusb.model.Topic;
import net.rusb.utils.Pager;
import net.rusb.utils.Utils;

public class DiscussPriServlet extends WhuLayoutServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Template handleRequest(HttpServletRequest request,
			HttpServletResponse response, Context ctx) {
		String topicId = request.getParameter("topicid");
		if(!Utils.isEmpty(topicId)){
			TopicDao tDao = new TopicDao();
			Topic topic = tDao.getTopicById(topicId,true,true);
			MessageDao mDao = new MessageDao();
			Pager<Message> messagePager = mDao.listByProperty(MessageDao.TOPIC_ID, topicId, 1);
			ctx.put("topic", topic);
			ctx.put("messagePager", messagePager);
			ctx.put(PAGE_TITLE, " | "+topic.getTopicName());
			ctx.put(KEY_WORDS, topic.getTopicName()+","+topic.getTopicTags());
		}
		return getTemplate("topicDeatils.vm");
	}
}
