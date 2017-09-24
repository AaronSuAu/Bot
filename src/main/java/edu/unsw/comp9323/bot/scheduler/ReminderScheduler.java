package edu.unsw.comp9323.bot.scheduler;

import java.util.ArrayList;
import java.util.List;

import javax.activity.InvalidActivityException;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.unsw.comp9323.bot.model.Person_info;
import edu.unsw.comp9323.bot.model.Reminder;
import edu.unsw.comp9323.bot.service.impl.ReminderServiceImpl;
import edu.unsw.comp9323.bot.util.EmailUtil;

@Component
public class ReminderScheduler {
	@Autowired
	private ReminderServiceImpl reminderService;
	@Autowired
	private EmailUtil emailUtil;

	@Scheduled(cron = "*/5 * * * * *")
	public void remindByEmail() {
		// TODO Auto-generated method stub
		List<Reminder> reminders = reminderService.getReminderToSend();
		List<Person_info> receivers;
		ArrayList<String> receiver_email;
		for (Reminder r : reminders) {
			receivers = reminderService.getReceivers(r.getId());
			receiver_email = new ArrayList<String>();
			if (receivers != null) {
				for (Person_info p : receivers) {
					receiver_email.add(p.getEmail());
				}
			}
			try {
				emailUtil.sendFromGMail(receiver_email, r.getTitle(), r.getContent(),
						reminderService.getPersonInfo(r.getOwner()));
			} catch (InvalidActivityException | MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reminderService.updateReminderFlag(0, r.getId());
		}
	}
}
