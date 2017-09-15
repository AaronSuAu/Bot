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

import edu.unsw.comp9323.bot.dto.AssignmentInfoDto;
import edu.unsw.comp9323.bot.model.Assignment;

@Mapper
@Component
public interface AssignmentDao {

	@Select("SELECT a.name as name, a.due_date as due_date, r.upload_time as upload_time, p.name as author_name, r.id as material_id"
			+ " FROM ass as a, resource as r, person_info as p"
			+ " WHERE a.id = r.ass_id and p.zid = r.author_zid and r.ass_id is not null")
	List<AssignmentInfoDto> findAll();

	@Select("SELECT a.name as name, a.due_date as due_date, r.upload_time as upload_time, p.name as author_name, r.id as material_id"
			+ " FROM ass as a, resource as r, person_info as p" + " WHERE a.id = r.ass_id and p.zid = r.author_zid"
			+ " and a.name = #{title} and r.ass_id is not null")
	List<AssignmentInfoDto> findAssignmentByTitle(@Param("title") String title);

	@Insert("insert into ass (name, due_date) values (#{name}, #{due_date})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ass.id")
	void setAssignment(Assignment assignment);

	@Delete("DELETE FROM ass WHERE name = #{title}")
	void deleteAssignmentByTitle(String title);

	@Update("UPDATE ass SET name=#{name}, due_date = #{due_date} WHERE id = #{id}")
	void updateAssignment(Assignment assignment);

	@Select("SELECT * FROM ass  WHERE name = #{title}")
	Assignment getAssignmentIdByTitle(String title);

	@Select("SELECT name FROM ass")
	List<String> getAllAssignmentTitles();

	@Select("SELECT name FROM ass WHERE id = #{id}")
	String getAssignmentTitlesById(Long id);
}
