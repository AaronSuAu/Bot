package edu.unsw.comp9323.bot.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;

import ai.api.model.AIResponse;
import ai.api.model.Fulfillment;
import edu.unsw.comp9323.bot.service.AIWebbookService;
import edu.unsw.comp9323.bot.service.AssignmentService;
import edu.unsw.comp9323.bot.service.EmailService;
import edu.unsw.comp9323.bot.service.LectureService;
import edu.unsw.comp9323.bot.service.ReminderService;
import edu.unsw.comp9323.bot.service.ResourceService;

@Service
public class AIWebhookServiceImpl implements AIWebbookService {

	@Autowired
	private AssignmentService assignmentService;
	@Autowired
	private LectureService lectureService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ReminderService reminderService;

	public void doWebhook(AIWebhookRequest input, Fulfillment output) {
		// Get intent
		String intentName = input.getResult().getMetadata().getIntentName();

		// Get parameter
		HashMap<String, JsonElement> params = input.getResult().getParameters();

		// return string
		String returnMsg = null;

		/**
		 * Dispatch to different services
		 */
		// for assignment
		if (intentName.equals("getAllAssignment")) {// back-end checked
			returnMsg = assignmentService.getAllAssignment(input);
		} else if (intentName.equals("getAssignmentByTitle")) {// back-end
																// checked
			returnMsg = assignmentService.getAssignmentByTitle(input);
		} else if (intentName.equals("addAssignmentByTitle")) {// back-end
																// checked
			returnMsg = assignmentService.addAssignmentByTitle(input);
		} else if (intentName.equals("changeAssignmentByTitle")) { // back-end
																	// checked
			returnMsg = assignmentService.changeAssignmentByTitle(input);
		} else if (intentName.equals("deleteAssignmentByTitle")) { // back-end
																	// checked
			returnMsg = assignmentService.deleteAssignmentByTitle(input);
		} else if (intentName.equals("studentSubmitAssignment")) { // back-end
																	// checked
			returnMsg = assignmentService.studentSubmitAssignment(input);
		} else if (intentName.equals("getUnsubmitingGroup")) {
			// TODO
			returnMsg = assignmentService.getUnsubmitingGroup(input);
		} else if (intentName.equals("getAssSubmissionByAssTitleAndGroupNb")) { // back-end
																				// checked
			returnMsg = assignmentService.getAssSubmissionByAssTitleAndGroupNb(input);
		} else if (intentName.equals("getAllUnmarkedAssignmentGroup")) { // back-end
																			// checked
			returnMsg = assignmentService.getAllUnmarkedAssignmentGroup(input);
		} else if (intentName.equals("markAssignmentByGroupNb")) { // back-end
																	// checked
			returnMsg = assignmentService.markAssignmentByGroupNb(input);
		} else {
			// not for assignment
		}

		// for class TODO more functions
		if (intentName.equals("getLectureInfoByWeek")) {
			returnMsg = lectureService.getLectureInfoByWeek(input);
		} else {
			// not for class
		}

		// TODO for lecture resource
		if (intentName.equals("getAllLectureResource")) {
			returnMsg = resourceService.getAllLectureResource(input);
		} else if (intentName.equals("getLectureResourceByWeek")) {
			returnMsg = resourceService.getClassMaterialUrlByWeek(input);
		} else if (intentName.equals("addLectureResourceByWeek")) {
			returnMsg = resourceService.addClassMaterialUrlByWeek(input);
		} else if (intentName.equals("deleteLectureResourceByWeek")) {
			returnMsg = resourceService.deleteLectureResourceByWeek(input);
		} else if (intentName.equals("changeLectureResourceByWeek")) {
			returnMsg = resourceService.updateClassMaterialUrlByWeek(input);
		} else {
			// not for lecture resource
		}

		// TODO for reminder
		if (intentName.equals("getReminders")) {
			returnMsg = reminderService.getAllReminders(input);
		} else if (intentName.equals("deleteReminder")) {
			if (reminderService.deleteReminder(input)) {
				returnMsg = "Reminder Removed Successfully!";
			} else {
				returnMsg = "Failed To Remove!";
			}
		}

		// TODO for email
		if (intentName.equals("send_email_to_zid"))

		{
			returnMsg = emailService.sendEmailToZid(input);
		} else if (intentName.equals("send-email-template")) {
			returnMsg = emailService.sendEmailTemplate(input);
		}
		// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// Date date = new Date();
		// output.setSpeech("Webhook get: intent name:" + intentName + "return
		// at" +
		// dateFormat.format(date));
		output.setSpeech(returnMsg);
	};

	/**
	 * Web-hook request model class
	 */
	public static class AIWebhookRequest extends AIResponse {
		private static final long serialVersionUID = 1L;

		private OriginalRequest originalRequest;

		/**
		 * Get original request object
		 * 
		 * @return <code>null</code> if original request undefined in request
		 *         object
		 */
		public OriginalRequest getOriginalRequest() {
			return originalRequest;
		}
	}

	/**
	 * Original request model class
	 */
	protected static class OriginalRequest implements Serializable {
		private static final long serialVersionUID = 1L;
		private String source;
		private Map<String, ?> data;

		/**
		 * Get source description string
		 * 
		 * @return <code>null</code> if source isn't defined in a request
		 */
		public String getSource() {
			return source;
		}

		/**
		 * Get data map
		 * 
		 * @return <code>null</code> if data isn't defined in a request
		 */
		public Map<String, ?> getData() {
			return data;
		}
	}
}
