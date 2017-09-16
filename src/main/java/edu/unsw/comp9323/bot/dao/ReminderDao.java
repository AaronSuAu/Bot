package edu.unsw.comp9323.bot.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import edu.unsw.comp9323.bot.model.Person_info;
import edu.unsw.comp9323.bot.model.Reminder;

@Mapper
@Component
public interface ReminderDao {
	
	/*
	 * select users' reminders
	 */
	@Select("select * from reminder where owner = #{owner}")
	List<Reminder> findRemindersByOwner(@Param("owner") String owner);
	
	/*
	 * select reminder by a id
	 */
	@Select("select * from reminder where id = #{id}")
	List<Reminder> findReminderById(@Param("id") Long id);
	
	/*
	 * delete one reminder
	 */
	@Delete("delete from reminder where id = #{id}")
	boolean deleteReminder(@Param("id") Long id);
	
	
	/*
	 * update a reminder
	 */
	@Update("update reminder "
			+ "set date = #{date} "
			+ ", content = #{content}, title = #{title} "
			+ ", owner = #{owner} "
			+ "where id = #{id}")
	boolean updateReminder( Reminder reminder);
	
	/*
	 * create a new reminder
	 */
	@Insert("insert into reminder (date, content, title, owner) "
			+ "values(#{date}, #{content}, #{title}, #{owner})")
	boolean insertReminder( Reminder reminder);
	
	/*
	 * get all the receivers
	 */
	@Select("select p.zid, p.group_nb, p.email, p.name, p.role, p.password "
			+ "from person_info p , reminder r, reminder_receiver rr "
			+ "where r.id = #{id} and rr.reminder = r.id and rr.receiver = p.zid")
	List<Person_info> getReceivers(@Param("id") String id);
	

}
