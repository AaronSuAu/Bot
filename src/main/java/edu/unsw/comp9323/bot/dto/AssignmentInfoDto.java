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
	private String material_id;

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
		this.material_id = material_path;
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

	public String getMaterial_id() {
		return material_id;
	}

	public void setMaterial_id(String material_id) {
		this.material_id = material_id;
	}

	@Override
	public String toString() {
//		return "AssignmentInfoDto [name=" + name + ", due_date=" + due_date + ", upload_time=" + upload_time
//				+ ", author_name=" + author_name + ", material_id=" + material_id + "]";
		return "AssignmentInfoDto [name=" + name + ", due_date=" + due_date + "]";
	}

}
