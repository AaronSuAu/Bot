package edu.unsw.comp9323.bot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.unsw.comp9323.bot.dao.Person_infoDao;
import edu.unsw.comp9323.bot.model.Person_info;
import edu.unsw.comp9323.bot.service.AuthenticationService;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	Person_infoDao person_infoDao;
	@Autowired
	Person_info person_info;

	@Override
	public String isValidUser(AIWebhookRequest input) {
		System.out.println("AuthenticationServiceImpl: isValidUser");

		String zid = input.getResult().getParameters().get("zid").getAsString();
		String password = input.getResult().getParameters().get("password").getAsString();

		person_info = person_infoDao.getUserByZid(zid);
		// zid doesn't exist
		if (person_info == null) {
			System.out.println("zid doesn't exist");
			return "zid and password don't match, please login again";
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// @param: plain,code
		boolean b = encoder.matches(password, person_info.getPassword());
		// wrong password
		if (!b) {
			System.out.println("wrong password");
			return "zid and password don't match, please login again";
		} else {
			return "Welcome, " + person_info.getName();
		}

	}

}
