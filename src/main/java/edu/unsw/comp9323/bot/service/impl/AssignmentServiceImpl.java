package edu.unsw.comp9323.bot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;

import edu.unsw.comp9323.bot.dao.AssignmentDao;
import edu.unsw.comp9323.bot.dto.AssignmentInfoDto;
import edu.unsw.comp9323.bot.service.AssignmentService;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;
import edu.unsw.comp9323.bot.util.assignmentUitl;

@Service
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	AssignmentDao assignmentDao;

	@Override
	public String getAllAssignment(AIWebhookRequest input) {
		System.out.println("getAllAssignment()");

		List<AssignmentInfoDto> assignmentInfoDtoList = assignmentDao.findAll();
		System.out.println(assignmentInfoDtoList.size());
		return assignmentUitl.renderAssignmentReturnMsg(assignmentInfoDtoList);
	}

	/**
	 * TODO return assignment name, due date, material url(s), upload time, author
	 */
	@Override
	public String getAssignmentByTitle(AIWebhookRequest input) {
		System.out.println("getAssignmentByTitle()");

		JsonElement title = input.getResult().getParameters().get("assignment-title");
		String titleString = title.getAsString();
		System.out.println("assignment-title: " + title.getAsString()); // title = "'phase1'"

		List<AssignmentInfoDto> assignmentInfoDtoList = assignmentDao.findAssignmentByTitle(titleString);

		return assignmentUitl.renderAssignmentReturnMsg(assignmentInfoDtoList);
	}

	@Override
	public String addAssignmentByTitle(AIWebhookRequest input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String changeAssignmentByTitle(AIWebhookRequest input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteAssignmentByTitlr(AIWebhookRequest input) {
		// TODO Auto-generated method stub
		return null;
	}

}
