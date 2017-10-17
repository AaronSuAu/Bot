package edu.unsw.comp9323.bot.util;

import java.util.List;

public class ButtonBuilder {
	private String text;
	private Inline_Keyboard reply_markup;
	
	public ButtonBuilder(String text, Inline_Keyboard reply_markup) {
		super();
		this.text = text;
		this.reply_markup = reply_markup;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Inline_Keyboard getReply_markup() {
		return reply_markup;
	}
	public void setReply_markup(Inline_Keyboard reply_markup) {
		this.reply_markup = reply_markup;
	}
	
	
}
