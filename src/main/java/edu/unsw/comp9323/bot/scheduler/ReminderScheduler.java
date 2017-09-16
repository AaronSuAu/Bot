package edu.unsw.comp9323.bot.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.unsw.comp9323.bot.service.impl.ReminderServiceImpl;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;

@Component
public class ReminderScheduler {
	@Autowired
	private ReminderServiceImpl reminderService;
	
	
	@Scheduled(cron = "*/10 * * * * *")
	public void remindByEmail() {
		// TODO Auto-generated method stub
		reminderService.remindByEmail();
//		System.out.println("!!!!!!!!!");

	}
}
