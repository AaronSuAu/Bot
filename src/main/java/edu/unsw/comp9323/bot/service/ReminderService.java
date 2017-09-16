package edu.unsw.comp9323.bot.service;

import java.util.List;

import edu.unsw.comp9323.bot.model.Reminder;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;

public interface ReminderService {
	public List<Reminder> getAllReminders(AIWebhookRequest input);
	public boolean deleteReminder(AIWebhookRequest input);
	public boolean addReminder(AIWebhookRequest input);
	public void remindByEmail();
	public boolean updateReminder(AIWebhookRequest input);
}
