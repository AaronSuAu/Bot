package edu.unsw.comp9323.bot.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component
public class Ass_student implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long group_nb;
	private Long ass_id;
	private String path;
	private Timestamp submit_time;
	private Float grade;

	public Ass_student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ass_student(Long id, Long group_nb, Long ass_id, String path, Timestamp submit_time, float grade) {
		super();
		this.id = id;
		this.group_nb = group_nb;
		this.ass_id = ass_id;
		this.path = path;
		this.submit_time = submit_time;
		this.grade = grade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGroup_nb() {
		return group_nb;
	}

	public void setGroup_nb(Long group_nb) {
		this.group_nb = group_nb;
	}

	public Long getAss_id() {
		return ass_id;
	}

	public void setAss_id(Long ass_id) {
		this.ass_id = ass_id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Timestamp getSubmit_time() {
		return submit_time;
	}

	public void setSubmit_time(Timestamp submit_time) {
		this.submit_time = submit_time;
	}

	public Float getGrade() {
		return grade;
	}

	public void setGrade(Float grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Ass_student [id=" + id + ", group_nb=" + group_nb + ", ass_id=" + ass_id + ", path=" + path
				+ ", submit_time=" + submit_time + ", grade=" + grade + "]";
	}

}
