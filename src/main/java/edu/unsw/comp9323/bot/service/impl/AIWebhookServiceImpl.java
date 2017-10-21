package edu.unsw.comp9323.bot.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ai.api.model.AIResponse;
import ai.api.model.Fulfillment;
import edu.unsw.comp9323.bot.service.AIWebbookService;
import edu.unsw.comp9323.bot.service.AssignmentService;
import edu.unsw.comp9323.bot.service.AuthenticationService;
import edu.unsw.comp9323.bot.service.EmailService;
import edu.unsw.comp9323.bot.service.LectureService;
import edu.unsw.comp9323.bot.service.ReminderService;
import edu.unsw.comp9323.bot.service.ResourceService;
import edu.unsw.comp9323.bot.util.ValidationUtil;

@Service
public class AIWebhookServiceImpl implements AIWebbookService {

	@Autowired
	AssignmentService assignmentService;
	@Autowired
	LectureService lectureService;
	@Autowired
	ResourceService resourceService;
	@Autowired
	EmailService emailService;
	@Autowired
	ReminderService reminderService;
	@Autowired
	ValidationUtil validationUtil;
	@Autowired
	AuthenticationService authenticationService;

	public void addButton(String returnMsg, Fulfillment output, Map<String, JsonElement> telegramMap) {
		if (returnMsg.contains("{")) {
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(returnMsg);
			telegramMap.put("telegram", jo);
			output.setData(telegramMap);
		}
	}

	public void doWebhook(AIWebhookRequest input, Fulfillment output) {

		// Get intent
		String intentName = input.getResult().getMetadata().getIntentName();

		// Get parameter
		HashMap<String, JsonElement> params = input.getResult().getParameters();
		Map<String, JsonElement> telegramMap = new HashMap<>();

		// return string
		String returnMsg = null;

		/**
		 * Dispatch to different services
		 */
		try {

			// for authorization
			if (intentName.equals("login")) {
				returnMsg = authenticationService.isValidUser(input);
			}

			// for assignment
			if (intentName.equals("getAllAssignment")) {
				returnMsg = assignmentService.getAllAssignment(input);
				addButton(returnMsg, output, telegramMap);
			} else if (intentName.equals("getAssignmentByTitle")) {
				returnMsg = assignmentService.getAssignmentByTitle(input);
				addButton(returnMsg, output, telegramMap);

			} else if (intentName.equals("addAssignmentByTitle")) {
				returnMsg = assignmentService.addAssignmentByTitle(input);
				addButton(returnMsg, output, telegramMap);
			} else if (intentName.equals("changeAssignmentByTitle")) {
				returnMsg = assignmentService.changeAssignmentByTitle(input);
				addButton(returnMsg, output, telegramMap);
			} else if (intentName.equals("deleteAssignmentByTitle")) {
				returnMsg = assignmentService.deleteAssignmentByTitle(input);
			} else if (intentName.equals("studentSubmitAssignment")) {
				returnMsg = assignmentService.studentSubmitAssignment(input);
				addButton(returnMsg, output, telegramMap);

			} else if (intentName.equals("getUnsubmitingGroup")) {
				// TODO
				// returnMsg = assignmentService.getUnsubmitingGroup(input);
			} else if (intentName.equals("getAssSubmissionByAssTitleAndGroupNb")) {
				returnMsg = assignmentService.getAssSubmissionByAssTitleAndGroupNb(input);
				addButton(returnMsg, output, telegramMap);
			} else if (intentName.equals("getAssMarkByAssTitle")) {
				returnMsg = assignmentService.getAssMarkByAssTitle(input);
			} else if (intentName.equals("getAllUnmarkedAssignmentGroup")) {
				returnMsg = assignmentService.getAllUnmarkedAssignmentGroup(input);
				addButton(returnMsg, output, telegramMap);
			} else if (intentName.equals("markAssignmentByGroupNb")) {
				returnMsg = assignmentService.markAssignmentByGroupNb(input);
			} else {
				// not for assignment
			}

			if (intentName.equals("getLectureInfoByWeek")) {
				returnMsg = lectureService.getLectureInfoByWeek(input);
			} else {
				// not for class
			}

			if (intentName.equals("getAllLectureResource")) {
				returnMsg = resourceService.getAllLectureResource(input);
				addButton(returnMsg, output, telegramMap);
			} else if (intentName.equals("getLectureResourceByWeek")) {
				returnMsg = resourceService.getClassMaterialUrlByWeek(input);
				addButton(returnMsg, output, telegramMap);
			} else if (intentName.equals("addLectureResourceByWeek")) {
				returnMsg = resourceService.addClassMaterialUrlByWeek(input);
				addButton(returnMsg, output, telegramMap);
			} else if (intentName.equals("deleteLectureResourceByWeek")) {
				returnMsg = resourceService.deleteLectureResourceByWeek(input);
			} else if (intentName.equals("changeLectureResourceByWeek")) {
				returnMsg = resourceService.updateClassMaterialUrlByWeek(input);
			} else {
				// not for lecture resource
			}

			// reminder
			if (intentName.equals("getReminders")) {
				returnMsg = reminderService.getAllReminders(input);
			} else if (intentName.equals("deleteReminder")) {
				returnMsg = reminderService.deleteReminder(input);
			} else if (intentName.equals("updateReminder")) {
				returnMsg = reminderService.updateReminder(input);
			} else if (intentName.equals("createReminder")) {
				returnMsg = reminderService.addReminder(input);
			} else if (intentName.equals("getReminderDetails")) {
				returnMsg = reminderService.getReminderDetails(input);
			}

			if (intentName.equals("send_email_to_zid")) {
				returnMsg = emailService.sendEmailToZid(input);
			} else if (intentName.equals("send-email-template")) {
				returnMsg = emailService.sendEmailTemplate(input);
			}

		} catch (Exception e) {
			e.printStackTrace();
			returnMsg = "500 check log for detail";
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
		 * @return <code>null</code> if original request undefined in request object
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
