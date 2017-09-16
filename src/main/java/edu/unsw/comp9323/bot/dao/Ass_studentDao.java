package edu.unsw.comp9323.bot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import edu.unsw.comp9323.bot.model.Ass_student;

@Mapper
@Component
public interface Ass_studentDao {

	@Select("SELECT * FROM ass_student WHERE grade is null")
	List<Ass_student> getAllUnmarkedGroup();

	@Update("UPDATE ass_student SET grade = #{grade} WHERE id = #{id}")
	boolean markById(Ass_student ass_student);

	@Select("SELECT * FROM ass_student WHERE group_nb = #{group_nb} and ass_id = #{ass_id}")
	Ass_student getSubmissionByIdAndGroup(Ass_student ass_student);

	@Select("TODO")
	List<Ass_student> getAllUnsubmitGroups();

	@Insert("insert into ass_student (group_nb, ass_id, path, submit_time) values (#{group_nb}, #{ass_id}, #{path}, #{submit_time})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ass_student.id")
	void setSubmission(Ass_student ass_student);

	@Select("SELECT path FROM ass_student WHERE id = #{id}")
	String gePathById(Long id);

}
