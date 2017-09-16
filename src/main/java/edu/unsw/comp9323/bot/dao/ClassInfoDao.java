package edu.unsw.comp9323.bot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

import edu.unsw.comp9323.bot.model.ClassInfo;

@Mapper
@Component
public interface ClassInfoDao {
	@Select("select * from class where week = #{week}")
	ClassInfo getClassByWeek(int week);
}
