package com.comp9323.chatbot.springbootmybatis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comp9323.chatbot.springbootmybatis.dao.UserDao;
import com.comp9323.chatbot.springbootmybatis.model.User;
import com.comp9323.chatbot.springbootmybatis.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	public List<User> findAll() {
		return userDao.findAll();
	}
}
