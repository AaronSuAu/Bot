package edu.unsw.comp9323.bot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unsw.comp9323.bot.dao.UserDao;
import edu.unsw.comp9323.bot.model.Person_info;
import edu.unsw.comp9323.bot.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	public List<Person_info> findAll() {
		return userDao.findAll();
	}
}
