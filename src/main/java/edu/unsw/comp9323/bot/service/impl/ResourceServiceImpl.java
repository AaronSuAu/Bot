package edu.unsw.comp9323.bot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.core.pattern.parser.Parser;
import edu.unsw.comp9323.bot.dao.LectureDao;
import edu.unsw.comp9323.bot.dao.ResourceDao;
import edu.unsw.comp9323.bot.dto.AssignmentInfoDto;
import edu.unsw.comp9323.bot.model.Lecture;
import edu.unsw.comp9323.bot.model.Resource;
import edu.unsw.comp9323.bot.service.ResourceService;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;
import edu.unsw.comp9323.bot.util.ResourceUtil;
import edu.unsw.comp9323.bot.util.ValidationUtil;

@Service
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	Resource resource;
	@Autowired
	ResourceDao resourceDao;
	@Autowired
	ResourceUtil resourceUtil;
	@Autowired
	ValidationUtil validationUtil;
	@Autowired
	LectureDao lectureDao;

	@Override
	public String getAllLectureResource(AIWebhookRequest input) {
		List<Resource> resources = resourceDao.getAllLectureResource();
		return resourceUtil.renderResourceReturnMsg(resources);
	}

	public List<Resource> getResourceByAssignment(Long assignId) {
		return resourceDao.getResourceByAssignment(assignId);
	}

	public List<Resource> getResourceByClass(Long classId) {
		return resourceDao.getResourceByClass(classId);
	}

	public Long uploadAssignResource(Resource resource) {
		return resourceDao.uploadAssignResource(resource);
	}
	
	
	public String getClassMaterialUrlByWeek(AIWebhookRequest input){
		System.out.println("get Class Material by week"); // debug

		// Authorization
		if (!validationUtil.isLecturerOrStudent(input)) {
			return "Authorization fail";
		}

		String classId = input.getResult().getParameters().get("material-week-number").getAsString();

		List<Resource> list = resourceDao.getResourceByClass(Long.parseLong(classId));
		if(list.size() == 0){
			return "Wrong week number";
		}
		return resourceUtil.renderResourceReturnMsg(list);
	}
	
	public String addClassMaterialUrlByWeek(AIWebhookRequest input){
		System.out.println("add Class Material by week"); // debug

		// Authorization
		if (!validationUtil.isLecturerOrStudent(input)) {
			return "Authorization fail";
		}

		String classId = input.getResult().getParameters().get("material-week-number").getAsString();
		List<Lecture> list = lectureDao.getLectureByWeek(Integer.parseInt(classId));
		if(list.size() == 0){
			return "Wrong lecture number";
		}
		String name = input.getResult().getParameters().get("material-title").getAsString();
		String author_zid = input.getResult().getParameters().get("zid").getAsString();
		return "To add material, go to http://localhost:8080/page/material/add?"
				+ "name="+name+"&author_zid="+author_zid+"&class_id="+classId+"&type=add";
	}

	@Override
	public String deleteLectureResourceByWeek(AIWebhookRequest input) {
		System.out.println("delete Class Material by week"); // debug

		// Authorization
		if (!validationUtil.isLecturerOrStudent(input)) {
			return "Authorization fail";
		}
		String classId = input.getResult().getParameters().get("material-week-number").getAsString();

		if(resourceDao.deleteResourceByClassId(Integer.parseInt(classId)) == 0){
			return "Fail. Check the week number";
		}
		return "Success";
	}

	@Override
	public String updateClassMaterialUrlByWeek(AIWebhookRequest input) {
		System.out.println("update Class Material by week"); // debug

		// Authorization
		if (!validationUtil.isLecturerOrStudent(input)) {
			return "Authorization fail";
		}

		String classId = input.getResult().getParameters().get("material-week-number").getAsString();
		List<Lecture> list = lectureDao.getLectureByWeek(Integer.parseInt(classId));
		if(list.size() == 0){
			return "Wrong lecture number";
		}
		String name = input.getResult().getParameters().get("material-title").getAsString();
		String author_zid = input.getResult().getParameters().get("zid").getAsString();
		return "To add material, go to http://localhost:8080/page/material/add?"
				+ "name="+name+"&author_zid="+author_zid+"&class_id="+classId+"&type=update";
	}

}
