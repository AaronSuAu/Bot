package edu.unsw.comp9323.bot.model;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class Resource {
	private long id;
	private long ass_id;
	private String title;
	private String path;
	private String author;
	private Date uploadtime;
	private long class_id;

	public Resource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Resource(long id, long ass_id, String title, String path, String author, Date uploadtime, long class_id) {
		super();
		this.id = id;
		this.ass_id = ass_id;
		this.title = title;
		this.path = path;
		this.author = author;
		this.uploadtime = uploadtime;
		this.class_id = class_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAss_id() {
		return ass_id;
	}

	public void setAss_id(long ass_id) {
		this.ass_id = ass_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	public long getClass_id() {
		return class_id;
	}

	public void setClass_id(long class_id) {
		this.class_id = class_id;
	}

}
