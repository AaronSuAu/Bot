package edu.unsw.comp9323.bot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unsw.comp9323.bot.dao.Person_infoDao;
import edu.unsw.comp9323.bot.model.Person_info;
import edu.unsw.comp9323.bot.service.Person_infoService;
import edu.unsw.comp9323.bot.service.UserService;

@Service
public class Person_infoServiceImpl implements Person_infoService {

	@Autowired
	Person_infoDao person_infoDao;
	/**
	 * 
	 * @param person_info
	 * @return 0 if student, 1 if tutor, -1 wrong password
	 */
	public int validateUser(Person_info person_info) {
		List<Person_info> list = person_infoDao.validateUser(person_info);
		if(list.size() == 0){
			return -1;
		}
		return person_info.getRole();
	}
}
