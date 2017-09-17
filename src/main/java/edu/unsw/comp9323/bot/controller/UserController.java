package edu.unsw.comp9323.bot.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.unsw.comp9323.bot.model.Person_info;
import edu.unsw.comp9323.bot.service.impl.Person_infoServiceImpl;

@RestController
@RequestMapping("/")
public class UserController {
	@Autowired
	Person_infoServiceImpl person_infoServiceImpl;

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public int validateUser(@Param("zid") String user, @Param("password") String password) {
		Person_info person_info = new Person_info();
		person_info.setZid(user);
		person_info.setPassword(password);
		return person_infoServiceImpl.validateUser(person_info);

	}

}
