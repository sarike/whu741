package net.rusb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.rusb.db.base.BaseDao;
import net.rusb.model.Message;
import net.rusb.model.User;
import net.rusb.utils.Pager;

import org.apache.commons.dbutils.ResultSetHandler;


public class MessageDao {
	
	public final static String ALL = "all";
	public final static String TOPIC_ID = "topicid";
	public final static String ARTICLE_ID = "articleid";
	
	BaseDao<Message> baseDao = new BaseDao<Message>();
	
	public boolean addMessage(Message m){
		String sql = "insert into messages(messageContent,userid,topicid,articleid,subtime,score) values(?,?,?,?,?,?)";
		Object[] params = {m.getMessageContent(),m.getDisUser().getUserid(),m.getTopicId(),m.getArticleId(),m.getSubTime(),0};
		return baseDao.update(sql,params);
	}
	public int upScore(Long messageId){
		String sql = "update messages set score=score+1 where messageid=?";
		baseDao.update(sql,messageId);
		String sqlValue = "select score from messages where messageid=?";
		return (Integer)baseDao.getValue(sqlValue, "score",messageId);
	}
	ResultSetHandler<List<Message>> messageListRsh = new ResultSetHandler<List<Message>>() {
		
		public List<Message> handle(ResultSet rs) throws SQLException {
			// TODO Auto-generated method stub
			List<Message> list = new ArrayList<Message>();
			Message m = null;
			User user = null;
			while(rs.next()){
				m = new Message();
				m.setMessageId(rs.getLong("messageid"));
				m.setMessageContent(rs.getString("messagecontent"));
				m.setSubTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("subtime")));
				m.setScore(rs.getInt("score"));
				user = new User();
				user.setUsername(rs.getString("username"));
				user.setNickname(rs.getString("nickname"));
				user.setUserid(rs.getInt("userid"));
				user.setDescription(rs.getString("description"));
				m.setDisUser(user);
				list.add(m);
			}
			return list;
		}
	};
	
	public Pager<Message> listByProperty(String propName, String propValue, int pageIndex, int pageSize ,Object... params){
		String sql = "select * from messages m join users u on m.userid=u.userid where 1=1";
		if(propName!=null&&!propName.equals(ALL)){
			if(propName.equals(TOPIC_ID)){
				sql += " and m.topicid="+ propValue;
			}else if(propName.equals(ARTICLE_ID)){
				sql += " and m.articleid="+ propValue;
			}else{
				sql += " and m."+ propName +" like '%"+ propValue +"%'";
			}
		}
		sql += " order by m.score desc, m.subtime desc";
		System.out.println("ListByTopicId:"+sql);
		return baseDao.listByPage(sql, messageListRsh, pageIndex, pageSize,params);
	}
	public Pager<Message> listByProperty(String propName, String propValue, int pageIndex, Object... params){
		return listByProperty(propName, propValue, pageIndex, Pager.DEFAULT_PAGE_SIZE, params);
	}
	public void deleteByTopicId(String topicId){
		String sql = "delete from messages where topicid=?";
		baseDao.update(sql,topicId);
	}
	public void deleteByArticleId(String articleId){
		String sql = "delete from messages where articleid=?";
		baseDao.update(sql,articleId);
	}
}
