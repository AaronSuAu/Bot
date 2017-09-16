package edu.unsw.comp9323.bot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unsw.comp9323.bot.dao.ClassInfoDao;
import edu.unsw.comp9323.bot.model.ClassInfo;
import edu.unsw.comp9323.bot.service.ClassInfoService;

@Service
public class ClassInfoServiceImpl implements ClassInfoService{
	@Autowired
	ClassInfoDao classInfoDao;
	ClassInfo getClassByWeek(int week){
		return classInfoDao.getClassByWeek(week);
	}
}
