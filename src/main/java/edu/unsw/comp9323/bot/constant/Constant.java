package edu.unsw.comp9323.bot.constant;

import java.util.regex.Pattern;

public class Constant {
	public final static String ROOTPATH = "src/main/resources/";
	public final static Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	public final static String DOMAIN_NAME = "http://41d64e9d.ngrok.io";
	public final static String EMAIL_NAME = "comp9323bot@gmail.com";
	public final static String EMAIL_PASS = "comp9323";
}
