package edu.unsw.comp9323.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.unsw.comp9323.bot.service.impl.ReminderServiceImpl;

@RestController
@RequestMapping("/reminder")
public class ReminderController {
	@Autowired
	private ReminderServiceImpl reminderService;

	// @RequestMapping(value = "/getReminders", method = RequestMethod.POST)
	// public String getReminders() {
	// List<Reminder> reminders = reminderService.getAllReminders(input);
	// return reminders.size() + " ";
	// }
	//
	// @RequestMapping(value = "/addReminders", method = RequestMethod.GET)
	// public void addRemindersBy() {
	// reminderService.addReminder(input);
	// }
	//
	// @RequestMapping(value = "/rmReminders", method = RequestMethod.GET)
	// public void rmRemindersBy() {
	// reminderService.deleteReminder(input);
	// }
	//
	// @RequestMapping(value = "/updateReminders", method = RequestMethod.GET)
	// public void updateRemindersBy() {
	// reminderService.updateReminder(input);
	// }

}
