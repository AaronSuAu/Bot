package edu.unsw.comp9323.bot.service;

import edu.unsw.comp9323.bot.model.Person_info;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;

public interface EmailService {

	public String sendEmailToZid(AIWebhookRequest input);

	public String sendEmailTemplate(AIWebhookRequest input);

	public String sendEmailToAllStudent(String subject, String body, Person_info from);

}
