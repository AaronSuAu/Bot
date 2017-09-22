package edu.unsw.comp9323.bot.service;

import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;

public interface AuthenticationService {
	public String isValidUser(AIWebhookRequest input);
}
