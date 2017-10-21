package edu.unsw.comp9323.bot.service;

import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;

public interface AssignmentService {

	public String getAllAssignment(AIWebhookRequest input);

	public String getAssignmentByTitle(AIWebhookRequest input) throws Exception;

	// receive from POST by web html
	public String addAssignmentByTitle(AIWebhookRequest input) throws Exception;

	public String changeAssignmentByTitle(AIWebhookRequest input) throws Exception;

	public String deleteAssignmentByTitle(AIWebhookRequest input) throws Exception;

	public String studentSubmitAssignment(AIWebhookRequest input) throws Exception;

	public String getAllUnmarkedAssignmentGroup(AIWebhookRequest input) throws Exception;

	public String getAssSubmissionByAssTitleAndGroupNb(AIWebhookRequest input) throws Exception;

	public String markAssignmentByGroupNb(AIWebhookRequest input) throws Exception;

	// public String getUnsubmitingGroup(AIWebhookRequest input) throws Exception;

	public String getAssMarkByAssTitle(AIWebhookRequest input) throws Exception;

}
