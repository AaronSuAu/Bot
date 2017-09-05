package com.comp9323.chatbot.springbootmybatis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comp9323.chatbot.springbootmybatis.model.User;
import com.comp9323.chatbot.springbootmybatis.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/rest/")
public class UserController {
	@Autowired
	UserServiceImpl userServiceImpl;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<User> getAll() {
		return userServiceImpl.findAll();
	}

}
