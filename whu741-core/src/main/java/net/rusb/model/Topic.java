package net.rusb.model;

public class Topic {
	private User pubUser;
	private User answerUser;
	private Class topicClass;
	private int topicId;
	private String topicName;
	private String topicContent;
	private String topicAnswer;
	private String topicTags;
	private String pubTime;
	private String showTime;
	private String answerTime;
	private boolean answered;
	public boolean isAnswered() {
		return answered;
	}
	public void setAnswered(boolean answered) {
		this.answered = answered;
	}
	public String getShowTime() {
		return showTime;
	}
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getTopicContent() {
		return topicContent;
	}
	public void setTopicContent(String topicContent) {
		this.topicContent = topicContent;
	}
	public String getTopicAnswer() {
		return topicAnswer;
	}
	public void setTopicAnswer(String topicAnswer) {
		this.topicAnswer = topicAnswer;
	}
	public String getPubTime() {
		return pubTime;
	}
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}
	public User getPubUser() {
		return pubUser;
	}
	public void setPubUser(User pubUser) {
		this.pubUser = pubUser;
	}
	public int getTopicId() {
		return topicId;
	}
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
	public User getAnswerUser() {
		return answerUser;
	}
	public void setAnswerUser(User answerUser) {
		this.answerUser = answerUser;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TopicName:"+this.topicName+";TopicShowTime:"+this.showTime+";TopicPubTime:"+this.pubTime+"answer:"+this.topicAnswer;
	}
	public String getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(String answerTime) {
		this.answerTime = answerTime;
	}
	public Class getTopicClass() {
		return topicClass;
	}
	public void setTopicClass(Class topicClass) {
		this.topicClass = topicClass;
	}
	public String getTopicTags() {
		return topicTags;
	}
	public void setTopicTags(String topicTags) {
		this.topicTags = topicTags;
	}
	
}
