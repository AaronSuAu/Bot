package edu.unsw.comp9323.bot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unsw.comp9323.bot.dao.AssignmentDao;
import edu.unsw.comp9323.bot.model.Assignment;
import edu.unsw.comp9323.bot.service.AssignmentService;
import edu.unsw.comp9323.bot.service.impl.AIWebHookService.AIWebhookRequest;

@Service
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	AssignmentDao assignmentDao;

	@Override
	public String getAllAssignment(AIWebhookRequest input) {
		// TODO Auto-generated method stub
		List<Assignment> assignemntsList = assignmentDao.findAll();

		return "webhook: from getAllAssignment.getAllAssignment";
	}

	@Override
	public String getAssignmentByTitle(AIWebhookRequest input) {
		// TODO Auto-generated method stub
		return null;
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
