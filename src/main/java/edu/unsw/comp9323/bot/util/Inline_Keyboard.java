package edu.unsw.comp9323.bot.util;

import java.util.List;

public class Inline_Keyboard {
	private List<List<BasicButton>>  inline_keyboard;

	public Inline_Keyboard(List<List<BasicButton>> inline_keyboard) {
		super();
		this.inline_keyboard = inline_keyboard;
	}

	public List<List<BasicButton>> getInline_keyboard() {
		return inline_keyboard;
	}

	public void setInline_keyboard(List<List<BasicButton>> inline_keyboard) {
		this.inline_keyboard = inline_keyboard;
	}
	

}
