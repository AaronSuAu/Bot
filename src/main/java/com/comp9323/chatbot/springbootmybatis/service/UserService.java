package com.comp9323.chatbot.springbootmybatis.service;

import java.util.List;

import com.comp9323.chatbot.springbootmybatis.model.User;

public interface UserService {
	public List<User> findAll();
}
