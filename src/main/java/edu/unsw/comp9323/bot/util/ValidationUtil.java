package edu.unsw.comp9323.bot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unsw.comp9323.bot.model.Identity;
import edu.unsw.comp9323.bot.model.Person_info;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;
import edu.unsw.comp9323.bot.service.impl.Person_infoServiceImpl;

@Service
public class ValidationUtil {

	@Autowired
	Person_infoServiceImpl person_infoServiceImpl;
	@Autowired
	Person_info person_info;
	@Autowired
	Identity identity;
	@Autowired
	UserIdentityUtil userIdentityUtil;

	public Boolean isLecturer(AIWebhookRequest input) {
		System.out.println("Validtion: isLecturer");
		String zid = userIdentityUtil.getIdentity(input).getZid();
		String password = userIdentityUtil.getIdentity(input).getPassword();
		person_info.setZid(zid);
		person_info.setPassword(password);
		if (person_infoServiceImpl.validateUser(person_info) == 1) {
			return true;
		}
		return false;
	}

	public Boolean isStudent(AIWebhookRequest input) {
		System.out.println("Validtion: isStudent");
		String zid = userIdentityUtil.getIdentity(input).getZid();
		String password = userIdentityUtil.getIdentity(input).getPassword();
		person_info.setZid(zid);
		person_info.setPassword(password);
		if (person_infoServiceImpl.validateUser(person_info) == 0) {
			return true;
		}
		return false;
	}

	public Boolean isLecturerOrStudent(AIWebhookRequest input) {
		System.out.println("Validtion: isLecturerOrStudent");
		String zid = userIdentityUtil.getIdentity(input).getZid();
		String password = userIdentityUtil.getIdentity(input).getPassword();
		person_info.setZid(zid);
		person_info.setPassword(password);
		if (person_infoServiceImpl.validateUser(person_info) != -1) {
			return true;
		}
		return false;
	}

}
