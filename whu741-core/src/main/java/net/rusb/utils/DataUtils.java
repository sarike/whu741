package net.rusb.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import net.rusb.db.base.DataBaseManager;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;


public class DataUtils {
	
//	private static String url = null;
	private static QueryRunner qr = new QueryRunner();
	
//	static{
//		String dbName = Configs.get("dbname");
//		String userName =  Configs.get("dbusername");
//		String userPasswd =  Configs.get("dbpassword");
//		String dburl =  Configs.get("dburl");
//		url = "jdbc:mysql://" + dburl + "/"+dbName+"?user="+userName+"&password="+userPasswd;
//	}
	
	public static Connection getConn(){
//		Connection cn = null;
//		try {
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
//			cn = DriverManager.getConnection(url);
//			return cn;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null; 
//		} 
		try {
			return DataBaseManager.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static void update(String sql,Object... params){
		Connection conn = getConn();
		try {
			qr.update(conn, sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static <T> List<T> list(String sql,ResultSetHandler<List<T>> rsh){
		List<T> list = null;
		Connection conn = getConn();
		try {
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * 分页查询
	 * @param <T>
	 * @param sql
	 * @param rsh
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static <T> Pager<T> listByPage(String sql,ResultSetHandler<List<T>> rsh,int pageIndex,int pageSize){
		Pager<T> pager = new Pager<T>(pageSize,pageIndex);
		int beginIndex = (pageIndex-1)*pageSize;
		sql += " limit "+beginIndex+","+pageSize;
		pager.setVoList(list(sql, rsh));
		return pager;
	}
	/**
	 * 分页查询,使用默认的页面大小
	 * @param <T>
	 * @param sql
	 * @param rsh
	 * @param pageIndex
	 * @return
	 */
	public static <T> Pager<T> listByPage(String sql,ResultSetHandler<List<T>> rsh,int pageIndex){
		return listByPage(sql, rsh, pageIndex, Pager.DEFAULT_PAGE_SIZE);
	}
	public static <T> T get(String sql,ResultSetHandler<T> rsh,Object... params){
		T bean = null;
		Connection conn = getConn();
		try {
			bean = qr.query(conn, sql, rsh,params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bean;
	}
}
