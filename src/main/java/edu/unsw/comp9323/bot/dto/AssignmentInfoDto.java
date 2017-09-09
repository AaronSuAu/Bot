package edu.unsw.comp9323.bot.dto;

import java.io.Serializable;
import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class AssignmentInfoDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// for return assignment info
	private String name;
	private Date due_date;
	private Date upload_time;
	private String author_name;
	private String material_path;

	public AssignmentInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssignmentInfoDto(String name, Date due_date, Date upload_time, String author_name, String material_path) {
		super();
		this.name = name;
		this.due_date = due_date;
		this.upload_time = upload_time;
		this.author_name = author_name;
		this.material_path = material_path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDue_date() {
		return due_date;
	}

	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	public Date getUpload_time() {
		return upload_time;
	}

	public void setUpload_time(Date upload_time) {
		this.upload_time = upload_time;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getMaterial_path() {
		return material_path;
	}

	public void setMaterial_path(String material_path) {
		this.material_path = material_path;
	}

	@Override
	public String toString() {
		return "AssignmentInfoDto [name=" + name + ", due_date=" + due_date + ", upload_time=" + upload_time
				+ ", author_name=" + author_name + ", material_path=" + material_path + "]";
	}

}
