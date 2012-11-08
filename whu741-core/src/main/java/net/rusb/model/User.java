package net.rusb.model;

public class User {
	private Integer userid;
	private String username;
	private String password;
	private String nickname;
	private String description;
	private String authority;
	private String lastLoginTime;
	private String email;
	private boolean articleRemind;
	private boolean topicRemind;
	private boolean messageRemind;
	/**
	 * 是否通过邮箱验证
	 */
	private boolean valided;
	/**
	 * 邮箱验证码
	 */
	private String validcode;
	

	public boolean isArticleRemind() {
		return articleRemind;
	}
	public void setArticleRemind(boolean articleRemind) {
		this.articleRemind = articleRemind;
	}
	public boolean isTopicRemind() {
		return topicRemind;
	}
	public void setTopicRemind(boolean topicRemind) {
		this.topicRemind = topicRemind;
	}
	public boolean isMessageRemind() {
		return messageRemind;
	}
	public void setMessageRemind(boolean messageRemind) {
		this.messageRemind = messageRemind;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public boolean isValided() {
		return valided;
	}
	public void setValided(boolean valided) {
		this.valided = valided;
	}
	public String getValidcode() {
		return validcode;
	}
	public void setValidcode(String validcode) {
		this.validcode = validcode;
	}

}
