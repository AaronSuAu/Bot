package edu.unsw.comp9323.bot.service;

import java.util.List;

import edu.unsw.comp9323.bot.model.Person_info;
import edu.unsw.comp9323.bot.model.Reminder;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;

public interface ReminderService {
	public String getAllReminders(AIWebhookRequest input);

	public String deleteReminder(AIWebhookRequest input);

	public String addReminder(AIWebhookRequest input);

	public String updateReminder(AIWebhookRequest input);

	public List<Person_info> getReceivers(long id);

	public List<Reminder> getReminderToSend();

	public Person_info getPersonInfo(String zid);

	public boolean updateReminderFlag(int flag, long id);

	public String getReminderDetails(AIWebhookRequest input);
}
