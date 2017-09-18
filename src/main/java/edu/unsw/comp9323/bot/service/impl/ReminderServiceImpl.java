package edu.unsw.comp9323.bot.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;

import edu.unsw.comp9323.bot.dao.ReminderDao;
import edu.unsw.comp9323.bot.model.Reminder;
import edu.unsw.comp9323.bot.service.ReminderService;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;
import edu.unsw.comp9323.bot.util.UserIdentityUtil;

@Service
public class ReminderServiceImpl implements ReminderService {

	@Autowired
	ReminderDao reminderDao;
	@Autowired
	EmailServiceImpl emailService;
	@Autowired
	UserIdentityUtil userIdentityUtil;

	@Override
	public String getAllReminders(AIWebhookRequest input) {
		// TODO Auto-generated method stub
		// String zid =
		// input.getResult().getParameters().get("zid").getAsString();
		String zid = userIdentityUtil.getIdentity(input).getZid();
		List<Reminder> reminders = null;
		String returnMsg = "";
		String date = input.getResult().getParameters().get("date").getAsString();
		String date_period = input.getResult().getParameters().get("date-period").getAsString();
		if (date.equals("") && date_period.equals("")) {
			reminders = reminderDao.findRemindersByOwner(zid);
		} else if (!date_period.equals("")) {
			String[] datePeriod = date_period.split("/");
			reminders = reminderDao.findRemindersByDatePeriod(zid, datePeriod[0], datePeriod[1]);
		} else if (!date.equals("")) {
			reminders = reminderDao.findRemindersByDate(zid, date);
		}

		if (reminders == null) {
			returnMsg += "No reminder found !";
		} else {
			for (Reminder r : reminders) {
				returnMsg += r.getId() + ". " + r.getTitle() + ": " + r.getDate() + "; ";
			}
		}

		return returnMsg;
	}

	@Override
	public boolean deleteReminder(AIWebhookRequest input) {
		// TODO Auto-generated method stub
		// String reminder_id =
		JsonArray reminder_ids = input.getResult().getParameters().get("number").getAsJsonArray();
		if (reminder_ids != null || reminder_ids.size() != 0) {
			for (int i = 0; i < reminder_ids.size(); i++) {
				if (reminderDao.findReminderById(Long.parseLong(reminder_ids.get(i).toString())) == null) {
					return false;// no such reminder
				} else {
					if (!reminderDao.deleteReminder(Long.parseLong(reminder_ids.get(i).toString())))
						return false;// fail to delete
				}
			}
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean addReminder(AIWebhookRequest input) {
		// TODO Auto-generated method stub
		Reminder newReminder = new Reminder();
		newReminder.setDate(new Date(System.currentTimeMillis()));
		newReminder.setContent("1111");
		newReminder.setOwner("z5032760");
		newReminder.setTitle("2222");
		if (reminderDao.insertReminder(newReminder))
			return true;
		else
			return false;
	}

	@Override
	public void remindByEmail() {
		// TODO Auto-generated method stu
		// sendFromGMail(ArrayList<String> to, String subject, String body)
	}

	@Override
	public boolean updateReminder(AIWebhookRequest input) {
		// TODO Auto-generated method stub

		List<Reminder> reminders = reminderDao.findReminderById((long) 1);
		if (reminders == null) {
			return false;// no such reminder
		} else if (reminders.size() > 0) {
			Reminder newReminder = reminders.get(0);
			newReminder.setDate(new Date(System.currentTimeMillis()));
			newReminder.setContent("1111");
			newReminder.setOwner("z5032760");
			newReminder.setTitle("2222");
			if (reminderDao.updateReminder(newReminder))
				return true;// delete successfully
			else
				return false;// fail to delete
		} else {
			return false;
		}
	}

}
