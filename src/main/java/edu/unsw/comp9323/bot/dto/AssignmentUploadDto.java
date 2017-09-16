package edu.unsw.comp9323.bot.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class AssignmentUploadDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String zid;
	private String password;
	private String name;// phase1
	private String due_date;

	public AssignmentUploadDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssignmentUploadDto(String zid, String password, String name, String due_date) {
		super();
		this.zid = zid;
		this.password = password;
		this.name = name;
		this.due_date = due_date;
	}

	public String getZid() {
		return zid;
	}

	public void setZid(String zid) {
		this.zid = zid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	@Override
	public String toString() {
		return "AssignmentUploadDto [zid=" + zid + ", password=" + password + ", name=" + name + ", due_date="
				+ due_date + "]";
	}

}
