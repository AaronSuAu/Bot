package edu.unsw.comp9323.bot.service;

import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;

public interface EmailService {

	public String sendEmailToZid(AIWebhookRequest input);

	public String sendEmailToGroup(AIWebhookRequest input);

}
