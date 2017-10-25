package edu.unsw.comp9323.bot.model;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component
public class Resource {

	private Long id;
	private String title;
	private String path;
	private String author_zid;
	private Long ass_id;
	private Long class_id;
	private Timestamp upload_time;

	public Resource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Resource(Long id, String title, String path, String author_zid, Long ass_id, Long class_id,
			Timestamp upload_time) {
		super();
		this.id = id;
		this.title = title;
		this.path = path;
		this.author_zid = author_zid;
		this.ass_id = ass_id;
		this.class_id = class_id;
		this.upload_time = upload_time;
	}

	public Timestamp getTimestamp() {
		return upload_time;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.upload_time = timestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return author_zid;
	}

	public void setAuthor(String author) {
		this.author_zid = author;
	}

	public Long getAss_id() {
		return ass_id;
	}

	public void setAss_id(Long assign_id) {
		this.ass_id = assign_id;
	}

	public Long getClass_id() {
		return class_id;
	}

	public void setClass_id(Long class_id) {
		this.class_id = class_id;
	}

	@Override
	public String toString() {
		return "Title: " + title + ", Week Number: " + class_id;
	}

}
