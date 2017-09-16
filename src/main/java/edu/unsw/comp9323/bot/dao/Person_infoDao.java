package edu.unsw.comp9323.bot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import edu.unsw.comp9323.bot.model.Person_info;

@Mapper
@Component
public interface Person_infoDao {

	@Select("select * from person_info")
	List<Person_info> findAll();
	
	@Select("select * from person_info where zid = #{zId} and password = #{password}")
	List<Person_info> validateUser(Person_info person_info); 
}