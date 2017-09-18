package edu.unsw.comp9323.bot.service;

import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;

public interface ReminderService {
	public String getAllReminders(AIWebhookRequest input);

	public boolean deleteReminder(AIWebhookRequest input);

	public boolean addReminder(AIWebhookRequest input);

	public void remindByEmail();

	public boolean updateReminder(AIWebhookRequest input);
}
