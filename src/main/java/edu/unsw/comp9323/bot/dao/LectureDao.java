package edu.unsw.comp9323.bot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import edu.unsw.comp9323.bot.model.Lecture;

@Mapper
@Component
public interface LectureDao {

	@Select("select * from class where week = #{week}")
	Lecture getLectureInfoByWeek(Lecture lecture);
}
