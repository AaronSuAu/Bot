package edu.unsw.comp9323.bot.model;

import org.springframework.stereotype.Component;

@Component
public class Email {
	private Long id;
	private String sender_id;
	private String subject;
	private String body;
	private Long flag;

	public Email() {
		super();
	}

	public Email(String sender_id, String subject, String body, Long flag) {
		super();

		this.sender_id = sender_id;
		this.subject = subject;
		this.body = body;
		this.flag = flag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSender_id() {
		return sender_id;
	}

	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getFlag() {
		return flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}

}
