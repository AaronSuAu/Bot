package edu.unsw.comp9323.bot.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class AssignmentDto {
	private Long id;
	private String name;
	private Date due_date;

	public AssignmentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssignmentDto(String name, Date due_date) {
		super();
		this.name = name;
		this.due_date = due_date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
