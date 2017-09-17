package edu.unsw.comp9323.bot.model;

import java.io.Serializable;
import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class Reminder implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Date date;
	private String content;
	private String title;
	private String owner;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Reminder() {
		super();
		// TODO Auto-generated constructor stub
	}
//	public Reminder(String id, Date date, String content, String title, String owner) {
//		super();
//		this.id = id;
//		this.date = date;
//		this.content = content;
//		this.title = title;
//		this.owner = owner;
//	}
	

}
