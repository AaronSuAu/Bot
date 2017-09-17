package edu.unsw.comp9323.bot.dto;

import java.io.Serializable;
import java.sql.Date;

public class ReminderInfoDto implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String receiver;
	private Date date;
	private String content;
	private String title;
	private String owner;
	
	public ReminderInfoDto(){
		super();
	}
	public ReminderInfoDto(String id, String receiver, Date date, String content, String title, String owner) {
		super();
		this.id = id;
		this.receiver = receiver;
		this.date = date;
		this.content = content;
		this.title = title;
		this.owner = owner;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	@Override
	public String toString() {
		return "ReminderInfoDto [id=" + id + ", receiver=" + receiver + ", date=" + date + ", content=" + content
				+ ", title=" + title + ", owner=" + owner + "]";
	}
	
}
