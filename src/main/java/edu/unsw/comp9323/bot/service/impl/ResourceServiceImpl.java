package edu.unsw.comp9323.bot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import ch.qos.logback.core.pattern.parser.Parser;
import edu.unsw.comp9323.bot.constant.Constant;
import edu.unsw.comp9323.bot.dao.LectureDao;
import edu.unsw.comp9323.bot.dao.ResourceDao;
import edu.unsw.comp9323.bot.dto.AssignmentInfoDto;
import edu.unsw.comp9323.bot.model.Lecture;
import edu.unsw.comp9323.bot.model.Resource;
import edu.unsw.comp9323.bot.service.ResourceService;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;
import edu.unsw.comp9323.bot.util.BasicButton;
import edu.unsw.comp9323.bot.util.ButtonBuilder;
import edu.unsw.comp9323.bot.util.Inline_Keyboard;
import edu.unsw.comp9323.bot.util.ResourceUtil;
import edu.unsw.comp9323.bot.util.UserIdentityUtil;
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
	@Autowired
	UserIdentityUtil userIdentityUtil;
	/**
	 * return all the lecture resource links
	 * @param input From API.AI webhook
	 * @return lecture resource link
	 */
	@Override
	public String getAllLectureResource(AIWebhookRequest input) {
		List<Resource> resources = resourceDao.getAllLectureResource();
		return resourceUtil.renderResourceReturnMsg(resources);
	}
	
	/**
	 * return the resource for the assignment 
	 * @param assignId assignment id in database
	 * @return a list of link that contains assignment ID
	 */
	public List<Resource> getResourceByAssignment(Long assignId) {
		return resourceDao.getResourceByAssignment(assignId);
	}
	/**
	 * get the resource for week 12 for instance
	 * @param classId the class week number
	 * @return the links of the resource
	 */
	public List<Resource> getResourceByClass(Long classId) {
		return resourceDao.getResourceByClass(classId);
	}
	/**
	 * upload assignment resource for 
	 * @param resource the resource object
	 * @return 1 if success, 0 if fail
	 */
	public Long uploadAssignResource(Resource resource) {
		return resourceDao.uploadAssignResource(resource);
	}
	
	/**
	 * get the lectuer material url by week
	 * @param input from api.ai
	 * @return wrong information or the links
	 */
	public String getClassMaterialUrlByWeek(AIWebhookRequest input){
		System.out.println("get Class Material by week"); // debug

		// Authorization
		if (!validationUtil.isLecturerOrStudent(input)) {
			return "Authorization fail";
		}

		String classId = input.getResult().getParameters().get("week-number").getAsString().replaceAll("\\D+","");

		List<Resource> list = resourceDao.getResourceByClass(Long.parseLong(classId));
		if(list.size() == 0){
			return "Wrong week number Or no resources for the week";
		}
		return resourceUtil.renderResourceReturnMsg(list);
	}
	/**
	 * it will give the user a link, through the website, user can upload
	 * @param input from api.ai
	 * @return links to add the class material 
	 */
	public String addClassMaterialUrlByWeek(AIWebhookRequest input){
		System.out.println("add Class Material by week"); // debug

		// Authorization
		if (!validationUtil.isLecturer(input)) {
			return "Authorization fail";
		}

		String classId = input.getResult().getParameters().get("week-number").getAsString().replaceAll("\\D+","");
		List<Lecture> list = lectureDao.getLectureByWeek(Integer.parseInt(classId));
		if(list.size() == 0){
			return "Wrong lecture number";
		}
		String name = input.getResult().getParameters().get("material-title").getAsString();
		String author_zid = userIdentityUtil.getIdentity(input).getZid();
		//
		List<List<BasicButton>> bOuterList= new ArrayList<List<BasicButton>>();
		BasicButton bB = new BasicButton("Upload", Constant.DOMAIN_NAME+"/page/material/add?"
				+ "name="+name+"&author_zid="+author_zid+"&class_id="+classId+"&type=add");
		List<BasicButton> bList = new ArrayList<>();
		bList.add(bB);
		bOuterList.add(bList);
		Inline_Keyboard iKeyboard = new Inline_Keyboard(bOuterList);
		ButtonBuilder builder = new ButtonBuilder("Click the following button to add the class material", iKeyboard);
		//
		return new Gson().toJson(builder);
	}
	/**
	 * delete the lecture resource according to the lecture resource
	 * @param input from api.ai
	 * @return success or fail
	 */
	@Override
	public String deleteLectureResourceByWeek(AIWebhookRequest input) {
		System.out.println("delete Class Material by week"); // debug

		// Authorization
		if (!validationUtil.isLecturer(input)) {
			return "Authorization fail";
		}
		String classId = input.getResult().getParameters().get("week-number").getAsString().replaceAll("\\D+","");

		if(resourceDao.deleteResourceByClassId(Integer.parseInt(classId)) == 0){
			return "Fail. Check the week number";
		}
		return "Success";
	}
	/**
	 * it will return a link, through this link, user will go to a page to update
	 * @param input from api.ai
	 * @return fail information or the link
	 */
	@Override
	public String updateClassMaterialUrlByWeek(AIWebhookRequest input) {
		System.out.println("update Class Material by week"); // debug

		// Authorization
		if (!validationUtil.isLecturer(input)) {
			return "Authorization fail";
		}

		String classId = input.getResult().getParameters().get("week-number").getAsString().replaceAll("\\D+","");
		List<Lecture> list = lectureDao.getLectureByWeek(Integer.parseInt(classId));
		if(list.size() == 0){
			return "Wrong lecture number";
		}
		String name = input.getResult().getParameters().get("material-title").getAsString();
		String author_zid = userIdentityUtil.getIdentity(input).getZid();
		return "To update material, go to http://localhost:8080/page/material/add?"
				+ "name="+name+"&author_zid="+author_zid+"&class_id="+classId+"&type=update";
	}

}
