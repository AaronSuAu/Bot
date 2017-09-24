package edu.unsw.comp9323.bot.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Override
	public int validateUser(Person_info recived_person_info) {
		person_info = person_infoDao.getUserByZid(recived_person_info.getZid());
		// zid doesn't exist
		if (person_info == null) {
			System.out.println("zid doesn't exist");
			return -1;
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// @param: plain,code
		boolean b = encoder.matches(recived_person_info.getPassword(), person_info.getPassword());
		// wrong password
		if (!b) {
			System.out.println("wrong password");
			return -1;
		}

		// return role
		return person_info.getRole();

	}

	// *********************************the code below is for generating dummy
	// data****
	// encode all the password before
	@Override
	public void changePassword() {
		List<Person_info> list = person_infoDao.findAll();
		for (Person_info p : list) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			person_infoDao.changePassword(encoder.encode("123456"), p.getZid());
		}
	}

	@Override
	public String generateRandomString(String str, int length) {
		String SALTCHARS = str;
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < length) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	@Override
	public void createUser() {
		String zid = "z" + generateRandomString("1234567890", 7);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = encoder.encode("123456");
		person_infoDao.createPerson_info(zid, password);
	}
}
