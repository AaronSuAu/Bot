package edu.unsw.comp9323.bot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import edu.unsw.comp9323.bot.model.Lecture;

@Mapper
@Component
public interface LectureDao {

	@Select("select * from class where week = #{week}")
	Lecture getLectureInfoByWeek(Lecture lecture);
	
	@Select("select * from class where week = #{week}")
	List<Lecture> getLectureByWeek(@Param("week") int week);
}
