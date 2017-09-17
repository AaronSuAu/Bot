package edu.unsw.comp9323.bot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
	
	@Select("select * from person_info where zid = #{zId}")
	List<Person_info> getUserByZid(@Param("zId") String zId);
	
	@Insert("insert into person_info (zid, password) "
			+ "values(#{zId}, #{password})")
	int createPerson_info(@Param("zId") String zId, @Param("password") String password);
	
	@Update("update person_info set password = #{password} where zid = #{zid}")
	int changePassword(@Param("password") String password, @Param("zid") String zid);
	
}
