package edu.unsw.comp9323.bot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unsw.comp9323.bot.dao.LectureDao;
import edu.unsw.comp9323.bot.model.Lecture;
import edu.unsw.comp9323.bot.service.LectureService;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;

@Service
public class LectureServiceImpl implements LectureService {
	@Autowired
	LectureDao lectureDao;
	@Autowired
	Lecture lecture;

	@Override
	public String getLectureInfoByWeek(AIWebhookRequest input) {
		Integer week_nb = Integer.parseInt(input.getResult().getParameters().get("week-nb").getAsString().replaceAll("\\D+",""));
		lecture.setWeek(week_nb);
		lecture = lectureDao.getLectureInfoByWeek(lecture);
		return lecture.toString();
	}

}
