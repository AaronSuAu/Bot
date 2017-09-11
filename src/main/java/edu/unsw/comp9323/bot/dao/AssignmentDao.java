package edu.unsw.comp9323.bot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import edu.unsw.comp9323.bot.dto.AssignmentInfoDto;

@Mapper
@Component
public interface AssignmentDao {

	@Select("SELECT a.name as name, a.due_date as due_date, r.upload_time as upload_time, p.name as author_name, r.path as material_path"
			+ " FROM ass as a, resource as r, person_info as p"
			+ " WHERE a.id = r.ass_id and p.zid = r.author_zid and r.ass_id not null")
	List<AssignmentInfoDto> findAll();

	@Select("SELECT a.name as name, a.due_date as due_date, r.upload_time as upload_time, p.name as author_name, r.path as material_path"
			+ " FROM ass as a, resource as r, person_info as p" + " WHERE a.id = r.ass_id and p.zid = r.author_zid"
			+ " and a.name = #{title} ")
	List<AssignmentInfoDto> findAssignmentByTitle(@Param("title") String title);
}
