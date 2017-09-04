package com.comp9323.chatbot.springbootmybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.comp9323.chatbot.springbootmybatis.model.User;

@Mapper
@Component
public interface UserDao {
	@Select("select * from user")
	List<User> findAll();
}
