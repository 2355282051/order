package com.boka.user.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.Date;

public class Reply {

	@Id
	private String id;
	//评论编码
	@Transient
	private String commentId;
	//内容
	private String content;
	//回复人
	private User fromUser;
	//创建时间
	private Date createDate;
	//回复对象
	private User toUser;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public User getToUser() {
		return toUser;
	}
	public void setToUser(User toUser) {
		this.toUser = toUser;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
