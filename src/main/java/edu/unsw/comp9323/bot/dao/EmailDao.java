package edu.unsw.comp9323.bot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import edu.unsw.comp9323.bot.model.Email;

@Mapper
@Component
public interface EmailDao {

	@Select("select * from email where flag = 0")
	List<Email> findUnsentEmail();

	@Select("select receiver from email_receiver where email_id = #{email_id}")
	List<String> getReceivers(@Param("email_id") long email_id);

	@Insert("insert into email (sender_id, subject, body, flag) "
			+ "values(#{sender_id}, #{subject}, #{body}, #{flag})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "email.id")
	boolean insertEmail(Email email);

	@Insert("insert into email_receiver (email_id, receiver) values(#{email_id}, #{receiver})")
	boolean addReceivers(@Param("email_id") long email_id, @Param("receiver") String receiver);

	@Delete("delete from email where id = #{id}")
	boolean deleteEmail(@Param("id") Long id);

	@Update("update email set flag = #{flag} where id = #{id} ")
	boolean updateEmailFlag(@Param("flag") Long flag, @Param("id") Long id);
}
