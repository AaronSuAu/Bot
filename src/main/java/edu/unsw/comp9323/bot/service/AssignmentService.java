package edu.unsw.comp9323.bot.service;

import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;

public interface AssignmentService {

	public String getAllAssignment(AIWebhookRequest input);

	public String getAssignmentByTitle(AIWebhookRequest input);

	// receive from POST by web html
	public String addAssignmentByTitle(AIWebhookRequest input);

	public String changeAssignmentByTitle(AIWebhookRequest input);

	public String deleteAssignmentByTitle(AIWebhookRequest input);

	public String studentSubmitAssignment(AIWebhookRequest input);

	public String getAllUnmarkedAssignmentGroup(AIWebhookRequest input);

	public String getAssSubmissionByAssTitleAndGroupNb(AIWebhookRequest input);

	public String markAssignmentByGroupNb(AIWebhookRequest input);

	public String getUnsubmitingGroup(AIWebhookRequest input);

}
