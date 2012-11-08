package net.rusb.dao;


import java.util.List;

import net.rusb.db.base.BaseDao;
import net.rusb.model.Class;
import net.rusb.model.User;

import org.apache.commons.dbutils.handlers.BeanListHandler;

public class ClassDao {
	BaseDao<Class> baseDao = new BaseDao<Class>();
	public Class getClassById(Integer classId){
		String sql = "select * from classes where classid=?";
		return baseDao.get(sql, Class.class,classId);
	}
	public List<Class> getClasses(){
		String sql = "select * from classes order by topiccount desc";
		return baseDao.list(sql, new BeanListHandler<Class>(Class.class));
	}
	public boolean upTopicCount(Integer classId){
		String sql = "update classes set topiccount=topiccount+1 where classid=?";
		return baseDao.update(sql, classId);
	}
}
