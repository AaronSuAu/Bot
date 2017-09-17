package edu.unsw.comp9323.bot.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Lecture implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private int week;
	private Date date;
	private String location;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Lecture() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Lecture(Long id, int week, Date date, String location) {
		super();
		this.id = id;
		this.week = week;
		this.date = date;
		this.location = location;
	}

	@Override
	public String toString() {
		return "Lecture [id=" + id + ", week=" + week + ", date=" + date + ", location=" + location + "]";
	}

}
