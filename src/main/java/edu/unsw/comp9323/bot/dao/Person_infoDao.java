package edu.unsw.comp9323.bot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface Person_infoDao {

	@Select("select group_nb from person_info where zid=#{zid}")
	Long findGroupNbByZid(String zid);
}
