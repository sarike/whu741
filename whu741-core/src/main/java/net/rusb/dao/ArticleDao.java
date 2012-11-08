package net.rusb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.rusb.db.base.BaseDao;
import net.rusb.model.Article;
import net.rusb.model.User;
import net.rusb.utils.Pager;

import org.apache.commons.dbutils.ResultSetHandler;

public class ArticleDao {
	
	public static final String ALL = "all";
	
	BaseDao<Article> baseDao = new BaseDao<Article>();
	
	/**
	 * 添加一篇文章
	 * @param article 待添加的文章
	 */
	public void addArticle(Article article){
		String sql = "insert into articles(articletitle,articlecontent,pubuserid,singlepage,pageurl,classid,articletype,toped,advised,pubtime) values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {article.getArticleTitle(),article.getArticleContent(),article.getPubUser().getUserid(),article.isSinglePage()?1:0,article.getPageUrl(),article.getClassId(),article.getArticleType(),article.isToped()?1:0,article.isAdvised()?1:0,article.getPubTime()};
		baseDao.update(sql,params);
	}
	/**
	 * 添加一篇文章并返回ID
	 * @param article 待添加的文章
	 * @return 文章的ID
	 */
	public int addArticleAndGetId(Article article){
		String sql = "insert into articles(articletitle,articlecontent,pubuserid,singlepage,pageurl,classid,articletype,toped,advised,pubtime) values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {article.getArticleTitle(),article.getArticleContent(),article.getPubUser().getUserid(),article.isSinglePage()?1:0,article.getPageUrl(),article.getClassId(),article.getArticleType(),article.isToped()?1:0,article.isAdvised()?1:0,article.getPubTime()};
		return baseDao.updateAndGetId(sql, params);
	}
	/**
	 * 按照文章ID获取文章
	 * @param articleId 文章ID
	 * @param withPubUser 是否级联查询出发布人
	 * @return
	 */
	public Article getArticleById(String articleId,boolean withPubUser){
		Article article = new Article();
		String sql = "select * from articles where articleid=" + articleId;
		article = baseDao.get(sql, Article.class);
		if(withPubUser){
			sql = "select pubuserid from articles where articleid=?";
			Integer pubUserId = (Integer)baseDao.getValue(sql, "pubuserid", articleId);
			User pubUser = new UserDao().getUserById(pubUserId);
			article.setPubUser(pubUser);
		}
		return article;
	}
	/**
	 * 更新文章信息
	 * @param article
	 */
	public void updateArticle(Article article){
		String sql = "update articles set articletitle=?,articlecontent=?,singlepage=?,pageurl=?,classid=?,articletype=?,toped=?,advised=?,pubtime=? where articleid="+article.getArticleId();
		Object[] params = {article.getArticleTitle(),article.getArticleContent(),article.isSinglePage(),article.getPageUrl(),article.getClassId(),article.getArticleType(),article.isToped(),article.isAdvised(),article.getPubTime()};
		baseDao.update(sql, params);
	}
	/**
	 * 按ID删除一篇文章
	 * @param articleId
	 */
	public void deleteArticleById(String articleId){
		String sql = "delete from articles where articleid=?";
		baseDao.update(sql, articleId);
	}
	ResultSetHandler<List<Article>> articleRsh = new ResultSetHandler<List<Article>>(){
		public java.util.List<Article> handle(ResultSet rs) throws SQLException {
			List<Article> articlsList = new ArrayList<Article>();
			Article article = null;
			User pubUser = null;
			while(rs.next()){
				article = new Article();
				pubUser = new User();
				article.setArticleId(rs.getString("articleid"));
				article.setArticleTitle(rs.getString("articletitle"));
				article.setArticleType(rs.getString("articletype"));
				article.setArticleContent(rs.getString("articlecontent"));
				article.setPubTime(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(rs.getTimestamp("pubtime")));
				article.setAdvised(rs.getBoolean("advised"));
				article.setToped(rs.getBoolean("toped"));
				article.setClassId(rs.getString("classid"));
				article.setPageUrl(rs.getString("pageurl"));
				article.setSinglePage(rs.getBoolean("singlepage"));
				pubUser.setAuthority(rs.getString("authority"));
				pubUser.setUserid(rs.getInt("userid"));
				pubUser.setUsername(rs.getString("username"));
				pubUser.setPassword(rs.getString("password"));
				pubUser.setDescription(rs.getString("description"));
				pubUser.setNickname(rs.getString("nickname"));
				article.setPubUser(pubUser);
				articlsList.add(article);
			}
			return articlsList;
		};
	};
	
	/**
	 
	 /** 
	 * 按属性分页查询文章列表
	 * @param propName 属性名称
	 * @param propValue 属性值，如果属性名称为Article.ALL，则该参数无效
	 * @param pageIndex 第几页
	 * @param pageSize 页面大小
	 * @return 
	 */
	public Pager<Article> listByPropertyWithPage(String propName,String propValue,int pageIndex,int pageSize,String order){
		String sql = "select * from articles a join users u on a.pubuserid=u.userid where singlepage=0";
		
		if(propName!=null&&!propName.equals(ArticleDao.ALL)){
			sql += " and a."+propName+"=" + propValue;
		}
		if(order!=null&&!order.equals("")){
			sql += " order by a.toped desc,a.advised desc,a."+order+",a.pubtime desc";
		}else{
			sql += " order by a.toped desc,a.advised desc,a.pubtime desc";
		}
		return baseDao.listByPage(sql, articleRsh, pageIndex, pageSize);
	}
	/** 
	 * 按属性分页查询文章列表
	 * @param propName 属性名称
	 * @param propValue 属性值，如果属性名称为Article.ALL，则该参数无效
	 * @param pageIndex 第几页
	 * @return 
	 */
	public Pager<Article> listByPropertyWithPage(String propName,String propValue,int pageIndex,String order){
		return listByPropertyWithPage(propName, propValue, pageIndex, Pager.DEFAULT_PAGE_SIZE,order);
	}
	
	/**
	 * 获取单页文章列表
	 * @return
	 */
	public List<Article> getSinglePageList(){
		String sql = "select * from articles a join users u on a.pubuserid=u.userid where singlepage=1 order by a.toped desc,a.advised desc,a.pubtime desc";
		return baseDao.list(sql, articleRsh);
	}
	

	public static void main(String[] args) {
		ArticleDao ad = new ArticleDao();
		List<Article> list = ad.getSinglePageList();
		for (Article article : list) {
			System.out.println(article.getArticleTitle()+":"+article.isAdvised()+":"+article.getPubUser().getUsername());
		}
	}
}

