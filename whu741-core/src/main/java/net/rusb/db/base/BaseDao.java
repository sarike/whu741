package net.rusb.db.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import net.rusb.utils.Pager;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class BaseDao<T> {

//	private static String url = null;
	private QueryRunner qr = new QueryRunner();
	
//	static{
//		String dbName = Configs.get("dbname");
//		String userName =  Configs.get("dbusername");
//		String userPasswd =  Configs.get("dbpassword");
//		String dburl =  Configs.get("dburl");
//		url = "jdbc:mysql://" + dburl + "/"+dbName+"?user="+userName+"&password="+userPasswd;
//	}
	
	public Connection getConn(){
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
	public boolean update(String sql,Object... params){
		Connection conn = getConn();
		try {
			qr.update(conn, sql, params);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @param sql
	 */
	@Deprecated
	public void update(String sql){
		Connection conn = getConn();
		try {
			qr.update(conn, sql);
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
	
	public int updateAndGetId(String sql,Object... params){
		Connection conn = getConn();
		int generateKey = -1;
		try {
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			qr.fillStatement(ps, params);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs != null&&rs.next()) {  
				generateKey=rs.getInt(1);
			}  
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
		return generateKey;
	}
	
	public List<T> list(String sql,ResultSetHandler<List<T>> rsh){
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
	public List<T> list(String sql,ResultSetHandler<List<T>> rsh,Object... params){
		List<T> list = null;
		Connection conn = getConn();
		try {
			list = qr.query(conn, sql, rsh,params);
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
	public Pager<T> listByPage(String sql,ResultSetHandler<List<T>> rsh,int pageIndex,int pageSize,Object... params){
		Pager<T> pager = new Pager<T>(pageSize,pageIndex);
		int beginIndex = (pageIndex-1)*pageSize;
		String countSql = "select count(*) totalCount "+sql.substring(sql.indexOf("from"));
		long totalCount = (Long)getValue(countSql, "totalcount", params);
		System.out.println("totalCount:======"+totalCount);
		pager.setRecodeCount(totalCount);
		sql += " limit "+beginIndex+","+pageSize;
		System.out.println("2222"+sql);
		pager.setVoList(list(sql, rsh,params));
		return pager;
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
//	public Pager<T> listByPage(String sql,ResultSetHandler<List<T>> rsh,int pageIndex,int pageSize){
//		Pager<T> pager = new Pager<T>(pageSize,pageIndex);
//		int beginIndex = (pageIndex-1)*pageSize;
//		sql += " limit "+beginIndex+","+pageSize;
//		pager.setVoList(list(sql, rsh));
//		return pager;
//	}
	
	public T get(String sql,Class<T> type,Object... params){
		T bean = null;
		Connection conn = getConn();
		try {
			bean = qr.query(conn, sql, new BeanHandler<T>(type),params);
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
	public Object getValue(String sql,String property,Object... params){
		Object value = null;
		Connection conn = getConn();
		try {
			value = qr.query(conn, sql, new ScalarHandler(property),params);
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
		return value;
	}
	public List<Object> getColumn(String sql, String comlumnName ,Object...params){
		List<Object> colList ;
		Connection conn = getConn();
		try {
			colList = qr.query(conn, sql, new ColumnListHandler(comlumnName) ,params);
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
		return colList;
	}
}
