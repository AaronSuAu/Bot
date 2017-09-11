package edu.unsw.comp9323.bot.model;

import org.springframework.stereotype.Component;

@Component
public class Person_info {
	private String zId;
	private Long group_nb;
	private String email;
	private String name;
	private int role;
	private String password;
	
	public String getzId() {
		return zId;
	}
	public void setzId(String zId) {
		this.zId = zId;
	}
	public Long getGroup_nb() {
		return group_nb;
	}
	public void setGroup_nb(Long group_nb) {
		this.group_nb = group_nb;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
