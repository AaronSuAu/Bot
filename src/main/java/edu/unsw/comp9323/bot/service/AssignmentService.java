package edu.unsw.comp9323.bot.service;

import edu.unsw.comp9323.bot.service.impl.AIWebHookService.AIWebhookRequest;

public interface AssignmentService {

	String getAllAssignment(AIWebhookRequest input);

	String getAssignmentByTitle(AIWebhookRequest input);

	String addAssignmentByTitle(AIWebhookRequest input);

	String changeAssignmentByTitle(AIWebhookRequest input);

	String deleteAssignmentByTitlr(AIWebhookRequest input);

}
