package edu.unsw.comp9323.bot.dao;

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
	 * get all unsent reminders
	 */
	@Select("select * from reminder where flag = 1 and date = #{date}")
	List<Reminder> findReminderBySendDate(@Param("date") String date);

	/*
	 * select users' reminders
	 */
	@Select("select * from reminder where owner = #{owner}")
	List<Reminder> findRemindersByOwner(@Param("owner") String owner);

	@Select("select * from reminder where owner = #{owner} and date BETWEEN #{start} AND #{end} ")
	List<Reminder> findRemindersByDatePeriod(@Param("owner") String owner, @Param("start") String start,
			@Param("end") String end);

	@Select("select * from reminder where owner = #{owner} and date = #{date} ")
	List<Reminder> findRemindersByDate(@Param("owner") String owner, @Param("date") String date);

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

	@Update("update reminder " + "set date = #{date} " + ", content = #{content}, title = #{title} "
			+ ", owner = #{owner} " + "where id = #{id}")
	boolean updateReminder(Reminder reminder);

	@Update("update reminder set flag = #{flag} where id = #{id} ")
	boolean updateReminderFlag(@Param("flag") int flag, @Param("id") Long id);

	/*
	 * create a new reminder
	 */
	@Insert("insert into reminder (date, content, title, owner) " + "values(#{date}, #{content}, #{title}, #{owner})")
	boolean insertReminder(Reminder reminder);

	/*
	 * get all the receivers
	 */
	@Select("select p.zid, p.group_nb, p.email, p.name, p.role, p.password "
			+ "from person_info p , reminder r, reminder_receiver rr "
			+ "where r.id = #{id} and rr.reminder = r.id and rr.receiver = p.zid")
	List<Person_info> getReceivers(@Param("id") long id);

	/*
	 * get person Info
	 */
	@Select("select * from person_info where zid = #{zid} ")
	Person_info getPersonInfo(@Param("zid") String zid);

}
