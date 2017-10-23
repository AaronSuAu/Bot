package edu.unsw.comp9323.bot.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import edu.unsw.comp9323.bot.model.Person_info;

@Mapper
@Component
public interface Person_infoDao {

	@Select("select group_nb from person_info where zid=#{zid}")
	Long findGroupNbByZid(String zid);

	@Select("select * from person_info")
	List<Person_info> findAll();

	@Select("select * from person_info where zid = #{zid} and password = #{password}")
	Person_info validateUser(Person_info person_info);

	@Select("select * from person_info where zid = #{zid}")
	Person_info getUserByZid(@Param("zid") String zid);

	@Insert("insert into person_info (zid, password) " + "values(#{zId}, #{password})")
	int createPerson_info(@Param("zid") String zid, @Param("password") String password);

	@Update("update person_info set password = #{password} where zid = #{zid}")
	int changePassword(@Param("password") String password, @Param("zid") String zid);

	@Select("select * from person_info where zid = #{zid} ")
	List<Person_info> findUserFromZid(Person_info person_info);

	@Select("select * from person_info where group_nb = #{group_nb}")
	List<Person_info> getUserByGroupNb(@Param("group_nb") Long group_nb);

	@Select("select * from person_info where role = 0")
	ArrayList<Person_info> getAllStudent();

	@Select("select email from person_info where role = 0")
	ArrayList<String> getAllStudentEmails();

	@Select("select email from person_info where group_nb = #{group_nb}")
	ArrayList<String> getEmailByGroupNb(@Param("group_nb") Long group_nb);

	@Select("select zid from person_info where group_nb = #{group_nb}")
	ArrayList<String> getIdByGroupNb(Long group_nb);

	@Select("select zid from person_info where role = 0")
	ArrayList<String> getAllStudentZid();
}
