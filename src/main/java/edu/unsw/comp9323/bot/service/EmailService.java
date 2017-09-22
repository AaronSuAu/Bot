package edu.unsw.comp9323.bot.service;

import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;

public interface EmailService {

	public String sendEmailToZid(AIWebhookRequest input);

	public String sendEmailTemplate(AIWebhookRequest input);

	public String sendEmailToAllStudent();

	// TODO
	public void sendEmailToZid(String zid);

	public void sendEmailToGroup(Long group_nb);

}
