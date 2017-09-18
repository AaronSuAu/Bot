package edu.unsw.comp9323.bot.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class Identity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String zid;
	private String password;

	public Identity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Identity(String zid, String password) {
		super();
		this.zid = zid;
		this.password = password;
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

}
