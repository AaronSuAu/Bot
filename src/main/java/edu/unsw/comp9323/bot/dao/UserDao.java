package edu.unsw.comp9323.bot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import edu.unsw.comp9323.bot.model.Person_info;

@Mapper
@Component
public interface UserDao {

	@Select("select * from user")
	List<Person_info> findAll();
}
