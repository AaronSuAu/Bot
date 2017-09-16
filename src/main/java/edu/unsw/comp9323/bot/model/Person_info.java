package edu.unsw.comp9323.bot.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class Person_info implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String zid;
	private Long group_nb;
	private String email;
	private String name;
	private int role;
	private String password;

	public Person_info() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Person_info(String zid, Long group_nb, String email, String name, int role, String password) {
		super();
		this.zid = zid;
		this.group_nb = group_nb;
		this.email = email;
		this.name = name;
		this.role = role;
		this.password = password;
	}

	public String getZid() {
		return zid;
	}

	public void setZid(String zid) {
		this.zid = zid;
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

	@Override
	public String toString() {
		return "Person_info [zid=" + zid + ", group_nb=" + group_nb + ", email=" + email + ", name=" + name + ", role="
				+ role + ", password=" + password + "]";
	}
}
