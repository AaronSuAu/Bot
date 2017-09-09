package edu.unsw.comp9323.bot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import edu.unsw.comp9323.bot.model.Assignment;

@Mapper
@Component
public interface AssignmentDao {

	@Select("select * from ass")
	List<Assignment> findAll();
}
