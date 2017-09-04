package com.comp9323.chatbot.springbootmybatis;

import org.apache.ibatis.type.MappedTypes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.comp9323.chatbot.springbootmybatis.model.User;

//@MappedTypes(User.class)
//@MapperScan("com.comp9323.chatbot.springbootmybatis.dao")
@SpringBootApplication
public class SpringBootMybatisApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootMybatisApplication.class, args);
	}
}
