package edu.unsw.comp9323.bot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.unsw.comp9323.bot.model.User;
import edu.unsw.comp9323.bot.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/rest/")
public class UserController {
	@Autowired
	UserServiceImpl userServiceImpl;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<User> getAll() {
		System.out.println("get all");
		return userServiceImpl.findAll();
	}

}
