package net.rusb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.rusb.db.base.BaseDao;
import net.rusb.model.Class;
import net.rusb.model.Topic;
import net.rusb.model.User;
import net.rusb.utils.Pager;

import org.apache.commons.dbutils.ResultSetHandler;

public class TopicDao {
	
	public final static String ALL = "all";
	public final static String USER_ID = "userid";
	public final static String TAG = "topictags";
	public final static String CLASS_ID = "classid";
	
	BaseDao<Topic> baseDao = new BaseDao<Topic>();
	
	
	/**
	 * 添加一个题目
	 * @param topic 待增加的题目
	 * @param userid 发布人ID
	 */
	public void addTopic(Topic topic){
		String sql = "insert into topics(topicname,topiccontent,pubtime,userid,answered,classid,topictags) values(?,?,?,?,?,?,?,?)";
		Object[] params = {topic.getTopicName(),topic.getTopicContent(),topic.getPubTime(),topic.getPubUser().getUserid(),0,topic.getTopicClass().getClassId(),topic.getTopicTags()};
		baseDao.update(sql, params);
	}
	/**
	 * 添加一个题目，同时获取它的ID
	 * @param topic
	 * @param userid
	 * @return
	 */
	public int addTopicAndGetId(Topic topic,Integer userid){
		String sql = "insert into topics(topicname,topiccontent,pubtime,userid,answered,classid,topictags) values(?,?,?,?,?,?,?)";
		Object[] params = {topic.getTopicName(),topic.getTopicContent(),topic.getPubTime(),userid,0,topic.getTopicClass().getClassId(),topic.getTopicTags()};
		int generateId = baseDao.updateAndGetId(sql,params);
		if(generateId!=-1){
			new ClassDao().upTopicCount(topic.getTopicClass().getClassId());
		}
		return generateId;
	}
	/**
	 * 按id删除一个题目
	 * 
	 * @param topicId 题目ID
	 */
	public void deleteById(String topicId){
		String sql = "delete from topics where topicid=?";
		baseDao.update(sql, topicId);
	}
	/**
	 * 更新一个题目
	 * @param topic 待更新的题目
	 */
	public void updateTopic(Topic topic){
		String sql = "update topics t set t.topicname=?, t.topiccontent=?, t.topicanswer=?,t.answered=?,t.answeruserid=?,t.answertime=?,t.classid=?,t.topictags=? where topicid=?";
		Object[] params = {topic.getTopicName(),topic.getTopicContent(),topic.getTopicAnswer(),(topic.isAnswered()?1:0),topic.getAnswerUser().getUserid(),topic.getAnswerTime(),topic.getTopicClass().getClassId(),topic.getTopicTags(),topic.getTopicId()};
		System.out.println(sql);
		baseDao.update(sql, params);
	}

	/**
	 * 按题目ID获取题目信息
	 * @param topicId 题目ID
	 * @param withPubUser 是否级联查询出题目的发布人
	 * @param withAnswerUser 是否级联查询出题目的解答人
	 * @return
	 */
	public Topic getTopicById(String topicId,boolean withPubUser,boolean withAnswerUser){
		String sql = "select * from topics where topicid=?";
		Topic topic = baseDao.get(sql, Topic.class, topicId);
		UserDao uDao =  new UserDao();
		if(withPubUser){
			sql = "select userid from topics where topicid=?";
			Integer pubUserId = (Integer)baseDao.getValue(sql, "userid", topicId);
			User pubUser = uDao.getUserById(pubUserId);
			topic.setPubUser(pubUser);
		}
		if(withAnswerUser){
			sql = "select answeruserid from topics where topicid=?";
			Integer answerUserId = (Integer)baseDao.getValue(sql, "answeruserid", topicId);
			User answerUser = uDao.getUserById(answerUserId);
			topic.setAnswerUser(answerUser);
		}
		ClassDao cDao = new ClassDao();
		sql = "select classid from topics where topicid=?";
		Integer classid = (Integer)baseDao.getValue(sql, "classid", topicId);
		Class topicClass = cDao.getClassById(classid);
		topic.setTopicClass(topicClass);
		return topic;
	}
	/**
	 * 按属性查询题目
	 * @param property 属性名称
	 * @param value 属性值
	 * @param order 属性值
	 * @return 返回符合要求的题目列表
	 */
	public List<Topic> listByProperty(String property,String value,String order){
		String sql = "select t.*,c.*,u.username puname,u.nickname pnickname,u.userid puserid,u.description pdes "+
		",au.username auname,au.nickname anickname,au.userid auserid,au.description ades "+
		"from topics t left join users u on t.userid=u.userid left join users au on t.answeruserid=au.userid "+
		"left join classes c on t.classid=c.classid where 1=1";
		if(property!=null&&!property.equals(ALL)){
			if(property!=null&&property.equals(USER_ID))
				sql += " and t.userid="+value;
			if(property!=null&&property.equals(CLASS_ID))
				sql += " and t.classid="+value;
			else{
				sql += " and t."+property+" like '%"+value+"%'";
			}
		}
		if(order!=null&&!order.equals("")){
			sql += " order by t." + order+",t.pubtime desc";
		}else{
			sql += " order by t.pubtime desc";
		}
		
		return baseDao.list(sql, topicRsh);
	}
	/**
	 * 按照属性查询并分页
	 * @param property 属性名称
	 * @param value 属性值
	 * @param pageIndex 第几页
	 * @param pageSize 页面大小
	 * @return 返回封装页面信息的Pager
	 */
	public Pager<Topic> listByPropertyWithPage(String property,String value,int pageIndex,int pageSize, String order){
		String sql = "select t.*,c.*,u.username puname,u.nickname pnickname,u.userid puserid,u.description pdes "+
		",au.username auname,au.nickname anickname,au.userid auserid,au.description ades "+
		"from topics t left join users u on t.userid=u.userid left join users au on t.answeruserid=au.userid "+
		"left join classes c on t.classid=c.classid where 1=1";
		if(property!=null&&!property.equals(ALL)){
			if(property!=null&&property.equals(USER_ID))
				sql += " and t.userid="+value;
			if(property!=null&&property.equals(CLASS_ID))
				sql += " and t.classid="+value;
			else{
				sql += " and t."+property+" like '%"+value+"%'";
			}
		}
		if(order!=null&&!order.equals("")){
			sql += " order by t." + order+",t.pubtime desc";
		}else{
			sql += " order by t.pubtime desc";
		}
		System.out.println("=========="+sql);
		return baseDao.listByPage(sql, topicRsh, pageIndex, pageSize);
	}
	public Pager<Topic> listByPropertyWithPage(String property,String value,int pageIndex,String order){
		return listByPropertyWithPage(property,value,pageIndex,Pager.DEFAULT_PAGE_SIZE,order);
	}
	public List<Topic> listByKewWords(String keyWords){
		String[] kewWords = keyWords.split(" ");
		String sql = "select t.*,c.*,u.username puname,u.nickname pnickname,u.userid puserid,u.description pdes "+
		",au.username auname,au.nickname anickname,au.userid auserid,au.description ades "+
		"from topics t left join users u on t.userid=u.userid left join users au on t.answeruserid=au.userid "+
		"left join classes c on t.classid=c.classid where 1=2";
		for (String keyWord : kewWords) {
			sql += " or t.topicname like '%"+keyWord+"%'";
			sql += " or t.topictags like '%"+keyWord+"%'";
		}
		sql += " order by t.pubtime desc";
		return baseDao.list(sql, topicRsh);
	}
	public Pager<Topic> listByKewWordsWithPage(String keyWords,int pageIndex,int pageSize){
		String[] kewWords = keyWords.split(" ");
		String sql = "select t.*,c.*,u.username puname,u.nickname pnickname,u.userid puserid,u.description pdes "+
		",au.username auname,au.nickname anickname,au.userid auserid,au.description ades "+
		"from topics t left join users u on t.userid=u.userid left join users au on t.answeruserid=au.userid "+
		"left join classes c on t.classid=c.classid where 1=2";
		for (String keyWord : kewWords) {
			sql += " or t.topicname like '%"+keyWord+"%'";
			sql += " or t.topictags like '%"+keyWord+"%'";
		}
		sql += " order by t.pubtime desc";
		return baseDao.listByPage(sql, topicRsh, pageIndex, pageSize);
	}
	public Pager<Topic> listByKewWordsWithPage(String keyWords,int pageIndex){
		return listByKewWordsWithPage(keyWords, pageIndex, Pager.DEFAULT_PAGE_SIZE);
	}
	
	ResultSetHandler<List<Topic>> topicRsh = new ResultSetHandler<List<Topic>>(){
		public List<Topic> handle(ResultSet rs) throws SQLException {
			Topic t = null;
			User user = null;
			List<Topic> topicList = new ArrayList<Topic>();
			try {
				while(rs.next()){
					t = new Topic();
					t.setTopicId(rs.getInt("topicid"));
					t.setPubTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(rs.getTimestamp("pubtime")));
//					t.setShowTime(new SimpleDateFormat("yyyy/MM/dd").format(rs.getTimestamp("showtime")));
					t.setTopicAnswer(rs.getString("topicanswer"));
					t.setTopicContent(rs.getString("topiccontent"));
					t.setTopicName(rs.getString("topicname"));
					t.setTopicTags(rs.getString("topictags"));
					String answered = rs.getString("answered");
					t.setAnswered(answered.equals("1")?true:false);
					
					user = new User();
					user.setUsername(rs.getString("puname"));
					user.setNickname(rs.getString("pnickname"));
					user.setUserid(rs.getInt("puserid"));
					user.setDescription(rs.getString("pdes"));
					
					t.setPubUser(user);
					user = new User();
					user.setUsername(rs.getString("auname"));
					user.setNickname(rs.getString("anickname"));
					user.setUserid(rs.getInt("auserid"));
					user.setDescription(rs.getString("ades"));
					t.setAnswerUser(user);
					
					Class topicClass = new Class();
					topicClass.setClassId(rs.getInt("classid"));
					topicClass.setClassName(rs.getString("classname"));
					t.setTopicClass(topicClass);
					
					topicList.add(t);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			return topicList;
		};
	};
}
