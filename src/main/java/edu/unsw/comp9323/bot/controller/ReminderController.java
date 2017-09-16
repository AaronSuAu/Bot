package edu.unsw.comp9323.bot.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.unsw.comp9323.bot.model.Reminder;
import edu.unsw.comp9323.bot.service.ReminderService;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;
import edu.unsw.comp9323.bot.service.impl.ReminderServiceImpl;

@RestController
@RequestMapping("/reminder")
public class ReminderController {
	@Autowired
	private ReminderServiceImpl reminderService;
	private AIWebhookRequest input;
	
	
	@RequestMapping(value = "/getReminders", method = RequestMethod.GET)
	public String getReminders(){
		List<Reminder> reminders = reminderService.getAllReminders(input);
		return reminders.size() + "size";
	}
	@RequestMapping(value = "/addReminders", method = RequestMethod.GET)
	public void addRemindersBy(){
		reminderService.addReminder(input);
	}
	@RequestMapping(value = "/rmReminders", method = RequestMethod.GET)
	public void rmRemindersBy(){
		reminderService.deleteReminder(input);
	}
	@RequestMapping(value = "/updateReminders", method = RequestMethod.GET)
	public void updateRemindersBy(){
		reminderService.updateReminder(input);
	}

}
