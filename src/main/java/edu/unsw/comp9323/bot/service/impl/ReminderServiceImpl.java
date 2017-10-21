package edu.unsw.comp9323.bot.service.impl;

import java.math.BigInteger;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import edu.unsw.comp9323.bot.dao.ReminderDao;
import edu.unsw.comp9323.bot.model.Person_info;
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

		if (reminders == null || reminders.size() == 0) {
			returnMsg += "No reminder found !";
		} else {
			returnMsg += "|-ID-|-Title-|-Reminding Date-|\n";
			for (Reminder r : reminders) {
				returnMsg += r.getId() + ". " + r.getTitle() + ": " + r.getDate() + ";\n";
			}
		}

		return returnMsg;
	}

	@Override
	public String deleteReminder(AIWebhookRequest input) {
		// TODO Auto-generated method stub
		String zid = userIdentityUtil.getIdentity(input).getZid();// get zid
		JsonArray reminder_ids = input.getResult().getParameters().get("number").getAsJsonArray();
		if (reminder_ids != null || reminder_ids.size() != 0) {
			for (int i = 0; i < reminder_ids.size(); i++) {
				List<Reminder> reminders = reminderDao.findReminderById(Long.parseLong(reminder_ids.get(i).toString()));
				if (reminders == null || reminders.size() == 0) {
					return "No such reminder";// no such reminder
				} else if (!reminders.get(0).getOwner().equals(zid)) {
					return "You are not authorized to delete reminders of other user! ";
				} else {
					if (!reminderDao.deleteReminder(Long.parseLong(reminder_ids.get(i).toString())))
						return "Fail to delete";// fail to delete
				}
			}
			return "Reminder removed.";
		} else {
			return "No such reminder.";
		}

	}

	@Override
	public String addReminder(AIWebhookRequest input) {
		// TODO Auto-generated method stub
		JsonArray reminder_no = input.getResult().getParameters().get("student-no").getAsJsonArray();
		String dateString = input.getResult().getParameters().get("date").getAsString();
		String content = input.getResult().getParameters().get("reminder_content").getAsString();
		String title = input.getResult().getParameters().get("reminder_title").getAsString();
		String zid = userIdentityUtil.getIdentity(input).getZid();
		// init a new reminder object
		Reminder newReminder = new Reminder();
		// get date
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = new Date(sdf.parse(dateString).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Date format error.";
		}

		newReminder.setDate(date);
		newReminder.setContent(content);
		newReminder.setOwner(zid);
		newReminder.setTitle(title);
		try {
			if (reminderDao.insertReminder(newReminder)) {
				return addReceivers(reminder_no, newReminder.getId(), zid);
			} else
				return "Fail to create the reminder.";
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			return "Date format error";
		}

	}

	/*
	 * add receivers to a reminder
	 */
	private String addReceivers(JsonArray reminder_no, long reminder_id, String mZid) {
		// TODO Auto-generated method stub
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		// get receiver
		List<String> receivers = getListFromJsonArray(reminder_no);
		if (receivers.contains("me") || receivers.contains("myself")) {
			if (!reminderDao.addReceivers(reminder_id, mZid))
				return "Fail to create a Reminder (cannot add a receiver: " + mZid + ")";

		}
		if (receivers.contains("all") || receivers.contains("everybody") || receivers.contains("class")
				|| receivers.contains("everyone") || receivers.contains("whole class")
				|| receivers.contains("all members"))

		{
			List<Person_info> persons = reminderDao.getAllReceivers();
			for (Person_info person_info : persons) {
				if (!reminderDao.addReceivers(reminder_id, person_info.getZid()))
					return "Fail to create a Reminder (cannot add a receiver: " + person_info.getZid() + ")";
			}
		} else {

			for (String r : receivers) {
				// add group receivers
				if (r.contains("group")) {
					Matcher m = p.matcher(r);
					try {
						int group_nb = Integer.parseInt(m.replaceAll("").trim());
						List<Person_info> group_reveivers = reminderDao.getPersonInfoByGroup(group_nb);
						if (group_reveivers != null) {
							for (Person_info receiver : group_reveivers) {
								if (!reminderDao.addReceivers(reminder_id, receiver.getZid()))
									return "Fail to create a Reminder (cannot add a receiver: " + receiver.getZid()
											+ ")";
							}
						}
					} catch (NumberFormatException e) {
						return "Invalid group number input!";
					} catch (Exception e) {
						return "Sql operation failed";
					}
				} else if (r.contains("z")) { // add single receiver
					Person_info receiver = getPersonInfo(r);
					if (receiver != null) {
						if (!reminderDao.addReceivers(reminder_id, receiver.getZid()))
							return "Fail to create a Reminder (cannot add a receiver: " + receiver.getZid() + ")";
					}
				}
			} // end of for loop
		}
		return "Reminder created!";
	}

	public ArrayList<String> getListFromJsonArray(JsonArray ja) {
		ArrayList<String> aList = new ArrayList<String>();
		for (JsonElement element : ja) {
			aList.add(ja.getAsString());
		}
		return aList;
	}

	@Override
	public void remindByEmail() {
		// TODO Auto-generated method stu
		// sendFromGMail(ArrayList<String> to, String subject, String body)
	}

	@Override
	public String updateReminder(AIWebhookRequest input) {
		// TODO Auto-generated method stub

		BigInteger reminder_id = input.getResult().getParameters().get("number").getAsBigInteger();
		List<Reminder> reminders = reminderDao.findReminderById((long) reminder_id.intValue());
		if (reminders == null) {
			return "No such reminder to update";// no such reminder
		} else if (reminders.size() > 0) {
			Reminder reminder = reminders.get(0);
			// get data from api.ai
			JsonElement dateString = input.getResult().getParameters().get("reminder_date");
			JsonElement content = input.getResult().getParameters().get("reminder_content");
			JsonElement title = input.getResult().getParameters().get("reminder_title");
			// update
			if (dateString != null) {
				DateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				try {
					reminder.setDate(new Date(sdf.parse(dateString.getAsString()).getTime()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "Date format error.";
				}
			}
			if (content != null) {
				reminder.setContent(content.getAsString());
			}
			if (title != null) {
				reminder.setTitle(title.getAsString());
			}
			if (reminderDao.updateReminder(reminder))
				return "Reminder updated";// delete
											// successfully
			else
				return "Fail to update";// fail to delete
		} else {
			return "No such reminder to update";
		}
	}

	/*
	 * get receivers
	 * 
	 * @see edu.unsw.comp9323.bot.service.ReminderService#getReceivers(long)
	 */
	@Override
	public List<Person_info> getReceivers(long rid) {
		// TODO Auto-generated method stub
		return reminderDao.getReceivers(rid);
	}

	@Override
	public List<Reminder> getReminderToSend() {
		// TODO Auto-generated method stub
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		// Date date = new Date(new java.util.Date().getTime());
		return reminderDao.findReminderBySendDate(date);
	}

	@Override
	public Person_info getPersonInfo(String zid) {
		// TODO Auto-generated method stub
		return reminderDao.getPersonInfo(zid);
	}

	@Override
	public boolean updateReminderFlag(int flag, long id) {
		// TODO Auto-generated method stub
		return reminderDao.updateReminderFlag(flag, id);
	}

	@Override
	public String getReminderDetails(AIWebhookRequest input) {
		// TODO Auto-generated method stub
		long id = input.getResult().getParameters().get("number").getAsLong();
		String zid = userIdentityUtil.getIdentity(input).getZid();
		List<Reminder> reminders = reminderDao.findReminderById(id);
		if (reminders == null || reminders.size() == 0 || reminders.get(0) == null) {
			return "No such reminder";
		}
		Reminder reminder = reminders.get(0);
		if (!zid.equals(reminder.getOwner())) {
			return "No access to this reminder.";
		}
		List<Person_info> receivers = reminderDao.getReceivers(reminder.getId());
		String receiver_string = "";
		for (Person_info p : receivers) {
			receiver_string += p.getZid() + ", ";
		}
		receiver_string.substring(0, receiver_string.length() - 1);
		return "Reminder ID: \n  " + reminder.getId() + "\n" + "Title: \n  " + reminder.getTitle() + "\n"
				+ "Content: \n  " + reminder.getContent() + "\n" + "Remind Date: \n  " + reminder.getDate() + "\n"
				+ "Send to: \n  " + receiver_string;

	}

}
