package edu.unsw.comp9323.bot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unsw.comp9323.bot.dao.Person_infoDao;
import edu.unsw.comp9323.bot.model.Person_info;
import edu.unsw.comp9323.bot.service.Person_infoService;

@Service
public class Person_infoServiceImpl implements Person_infoService {

	@Autowired
	Person_infoDao person_infoDao;
	@Autowired
	Person_info person_info;

	/**
	 * 
	 * @param person_info
	 * @return 0 if student, 1 if tutor, -1 wrong password
	 */
	public int validateUser(Person_info person_info) {
		System.out.println("Validing zid: " + person_info.getZid() + " pwd: " + person_info.getPassword());
		person_info = person_infoDao.validateUser(person_info);
		if (person_info == null) {
			System.out.println("zid and pwd not match");
			return -1;
		} else {
			System.out.println("role: " + person_info.getRole());
			return person_info.getRole();
		}
	}
}
