package edu.unsw.comp9323.bot.service;

import edu.unsw.comp9323.bot.model.Person_info;

public interface Person_infoService {
	public int validateUser(Person_info recived_person_info);

	public void changePassword();

	public String generateRandomString(String str, int length);

	public void createUser();
}
