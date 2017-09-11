package edu.unsw.comp9323.bot.service;

import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;

public interface AssignmentService {

	public String getAllAssignment(AIWebhookRequest input);

	public String getAssignmentByTitle(AIWebhookRequest input);

	public String addAssignmentByTitle(AIWebhookRequest input);

	public String changeAssignmentByTitle(AIWebhookRequest input);

	public String deleteAssignmentByTitlr(AIWebhookRequest input);

}
