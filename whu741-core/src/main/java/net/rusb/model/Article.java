package net.rusb.model;


public class Article {
	private String articleId;
	private String articleTitle;
	private String articleContent;
	private User pubUser;
	private Class articleClass;
	private String articleTags;
	private boolean singlePage;
	private String pageUrl;
	private String classId;
	private String articleType;
	private boolean toped;
	private boolean advised;
	private String pubTime;
	
	public String getPubTime() {
		return pubTime;
	}
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}
	public boolean isToped() {
		return toped;
	}
	public void setToped(boolean toped) {
		this.toped = toped;
	}
	public boolean isAdvised() {
		return advised;
	}
	public void setAdvised(boolean advised) {
		this.advised = advised;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getArticleContent() {
		return articleContent;
	}
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	public User getPubUser() {
		return pubUser;
	}
	public void setPubUser(User pubUser) {
		this.pubUser = pubUser;
	}
	public boolean isSinglePage() {
		return singlePage;
	}
	public void setSinglePage(boolean singlePage) {
		this.singlePage = singlePage;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getArticleType() {
		return articleType;
	}
	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}
	public Class getArticleClass() {
		return articleClass;
	}
	public void setArticleClass(Class articleClass) {
		this.articleClass = articleClass;
	}
	public String getArticleTags() {
		return articleTags;
	}
	public void setArticleTags(String articleTags) {
		this.articleTags = articleTags;
	}
}
