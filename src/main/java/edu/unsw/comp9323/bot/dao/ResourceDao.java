package edu.unsw.comp9323.bot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import edu.unsw.comp9323.bot.model.Resource;

@Mapper
@Component
public interface ResourceDao {
	@Select("SELECT * FROM resource WHERE class_id is not null")
	List<Resource> getAllLectureResource();

	@Select("select * from resource where id = #{id}")
	Resource getResourceById(Long id);

	@Select("select * from resource where class_id = #{classId}")
	public List<Resource> getResourceByClass(@Param("classId") Long classId);

	@Select("select * from resource where ass_id = #{assignId}")
	public List<Resource> getResourceByAssignment(@Param("assignId") Long assignId);

	@Insert("insert into resource (ass_id, title, path, author_zid, upload_time, class_id)"
			+ "values (#{ass_id}, #{title}, #{path}, #{author_zid}, #{upload_time}, #{class_id})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "resource.id")
	public Long uploadAssignResource(Resource resource);

	@Delete("DELETE FROM resource WHERE id = #{id}")
	void deleteResource(Resource resource);
	
	@Delete("delete from resource where class_id = #{id}")
	int deleteResourceByClassId(@Param("id") int id);
	
	
}
