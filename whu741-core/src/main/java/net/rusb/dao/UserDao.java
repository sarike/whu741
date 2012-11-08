package net.rusb.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.rusb.db.base.BaseDao;
import net.rusb.model.User;
import net.rusb.utils.DateTool;
import net.rusb.utils.Pager;

import org.apache.commons.dbutils.handlers.BeanListHandler;

public class UserDao {
	
	BaseDao<User> baseDao = new BaseDao<User>();
	/**
	 * 添加一个用户
	 * @param user
	 */
	public int addUser(User user){
		String sql = "insert into users(username," +
				"password," +
				"nickname," +
				"description," +
				"authority," +
				"email," +
				"articleremind," +
				"topicremind," +
				"messageremind," +
				"valided," +
				"validcode" +
				") values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {user.getUsername(),
				user.getPassword(),
				user.getNickname(),
				user.getDescription(),
				user.getAuthority(),
				user.getEmail(),
				user.isArticleRemind()?1:0,
				user.isTopicRemind()?1:0,
				user.isMessageRemind()?1:0,
				user.isValided()?1:0,
				user.getValidcode()
		};	
		return baseDao.updateAndGetId(sql, params);
	}
	/**
	 * 通过ID获取一个User
	 * @param userId
	 * @return
	 */
	public User getUserById(Integer userId){
		String sql = "select * from users where userid=?";
		return baseDao.get(sql, User.class,userId);
	}
	/**
	 * 判断用户名是否存在
	 * @param username
	 * @return
	 */
	public boolean isExist(String username){
		String sql = "select count(*) count from users where username=?";
		long size = (Long)baseDao.getValue(sql, "count", username);
		if(size>0)
			return true;
		else
			return false;
	}
	public Pager<User> listByLoginTime(int pageIndex, int pageSize, Object... params){
			String sql = "select * from users order by lastlogintime desc";
			return baseDao.listByPage(sql, new BeanListHandler<User>(User.class), pageIndex, pageSize, params);
	}
	
	public boolean updateUserByProperty(User user,String property,String value){
		String sql = "update users set " + property + "='" + value+"' where userid=?";
		return baseDao.update(sql,user.getUserid());
	}
	public boolean updateUserByProperty(User user,String[] property,String[] value){
		if(property==null){
			return false;
		}
		String sql = "update users set ";
		for(int i =0;i<property.length;i++){
			sql = sql + property[i] + "='" + value[i]+"',";
		}
		sql = sql.substring(0, sql.length()-1);
		sql += " where userid=?";
		System.out.println(sql);
		return baseDao.update(sql,user.getUserid());
	}
	public User validUser(String username,String password){
		String sql = "select * from users where username=? and password=?";
		Object[] params = {username,password};
		User user = baseDao.get(sql, User.class, params);
		if(user!=null){
			updateUserByProperty(user, "lastlogintime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateTool.getCST()));
		}
		return user;
	}
	public User validEmail(String userid,String code){
		String sql = "select * from users where userid=? and validcode=?";
		Object[] params = {userid,code};
		User user = baseDao.get(sql, User.class, params);
		return user;
	}
	/**
	 * 获取邮件列表
	 * @return
	 */
	public List<String> getEmailAddrList(boolean articleRemind,boolean topicRemind,boolean messageRemind){
		String sql = "select distinct email from users where email!=''";
		if(articleRemind){
			sql += " and articleremind=1";
		}
		if(topicRemind){
			sql += " and topicremind=1";
		}
		if(messageRemind){
			sql += " and messageremind=1";
		}
		List<Object> objList = baseDao.getColumn(sql, "email");
		List<String> emailList = new ArrayList<String>();
		for (Object object : objList) {
			emailList.add((String)object);
		}
		return emailList;
	}
	public static void main(String[] args) {
		for (String string : new UserDao().getEmailAddrList(true,false,false)) {
			System.out.println(string);
		}
		System.out.println("-----------------------------------------------");
		for (String string : new UserDao().getEmailAddrList(false,true,false)) {
			System.out.println(string);
		}
		System.out.println("-----------------------------------------------");
		for (String string : new UserDao().getEmailAddrList(false,false,true)) {
			System.out.println(string);
		}
	}
}
