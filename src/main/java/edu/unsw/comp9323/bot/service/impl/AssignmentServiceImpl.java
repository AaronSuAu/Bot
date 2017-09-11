package edu.unsw.comp9323.bot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unsw.comp9323.bot.dao.AssignmentDao;
import edu.unsw.comp9323.bot.dto.AssignmentInfoDto;
import edu.unsw.comp9323.bot.service.AssignmentService;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;
import edu.unsw.comp9323.bot.util.assignmentUitl;

@Service
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	AssignmentDao assignmentDao;

	/**
	 * return all assignments(list) including assignment name, due date, upload
	 * time, author and , material url(s)
	 * 
	 * TODO refine return format
	 */
	@Override
	public String getAllAssignment(AIWebhookRequest input) {
		System.out.println("getAllAssignment()"); // debug

		List<AssignmentInfoDto> assignmentInfoDtoList = assignmentDao.findAll();
		System.out.println(assignmentInfoDtoList.toString()); // debug

		return assignmentUitl.renderAssignmentReturnMsg(assignmentInfoDtoList);
	}

	/**
	 * return matched assignments(list) including assignment name, due date, upload
	 * time, author and , material url(s)
	 * 
	 * TODO refine return format
	 */
	@Override
	public String getAssignmentByTitle(AIWebhookRequest input) {
		System.out.println("getAssignmentByTitle()"); // debug

		String title = input.getResult().getParameters().get("assignment-title").getAsString();
		System.out.println("assignment-title: " + title); // debug

		List<AssignmentInfoDto> assignmentInfoDtoList = assignmentDao.findAssignmentByTitle(title);

		return assignmentUitl.renderAssignmentReturnMsg(assignmentInfoDtoList);
	}

	/**
	 * generate url for get file
	 */
	@Override
	public String addAssignmentByTitle(AIWebhookRequest input) {
		System.out.println("addAssignmentByTitle()"); // debug

		String assignment_title = input.getResult().getParameters().get("assignment-title").getAsString();
		String due_date_string = input.getResult().getParameters().get("assignment-due_date").getAsString();
		String authro_zid = input.getResult().getParameters().get("assignment-author_zid").getAsString();
		// TODO form thml for get file from user
		return "http://localhost:8080/upload_file?assignment_title=" + assignment_title + "&due_date_string="
				+ due_date_string + "&authro_zid=" + authro_zid;
	}

	/**
	 * generate url for get file
	 * 
	 * TODO change from add assignment
	 */
	@Override
	public String changeAssignmentByTitle(AIWebhookRequest input) {
		System.out.println("addAssignmentByTitle()"); // debug

		String assignment_title = input.getResult().getParameters().get("assignment-title").getAsString();
		String due_date_string = input.getResult().getParameters().get("assignment-due_date").getAsString();
		String authro_zid = input.getResult().getParameters().get("assignment-author_zid").getAsString();
		return "http://localhost:8080/upload_file?assignment_title=" + assignment_title + "&due_date_string="
				+ due_date_string + "&authro_zid=" + authro_zid;
	}

	/**
	 * delete ass and corresponding materials
	 */
	@Override
	public String deleteAssignmentByTitlr(AIWebhookRequest input) {
		System.out.println("deleteAssignmentByTitlr()"); // debug

		String title = input.getResult().getParameters().get("assignment-title").getAsString();
		System.out.println("assignment-title: " + title); // debug

		try {
			assignmentDao.deleteAssignmentByTitle(title);
			// delete success
			return "delete assignment:" + title + "success.";
		} catch (Exception e) { // TODO how to catch exception
			// delete fail
			return "delete assignment:" + title + "success.";
		}
	}

}
