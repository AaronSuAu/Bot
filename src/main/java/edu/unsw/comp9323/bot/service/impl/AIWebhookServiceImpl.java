package edu.unsw.comp9323.bot.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
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
import edu.unsw.comp9323.bot.util.BasicButton;
import edu.unsw.comp9323.bot.util.ButtonBuilder;
import edu.unsw.comp9323.bot.util.Inline_Keyboard;
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
		try {

			// for authorization
			if (intentName.equals("ExitContent")) {
				// https://discuss.api.ai/t/send-structured-facebook-message-from-webhook/1174/5
				// https://dialogflow.com/docs/rich-messages#custom-payload
				BasicButton bB = new BasicButton("GoogleText", "www.google.com");
				List<BasicButton> bList = new ArrayList<>();
				List<List<BasicButton>> bOuterList= new ArrayList<List<BasicButton>>();
				bList.add(bB);
				bOuterList.add(bList);
				Inline_Keyboard iKeyboard = new Inline_Keyboard(bOuterList);
				ButtonBuilder builder = new ButtonBuilder("Button list", iKeyboard);
				System.out.println(new Gson().toJson(builder));
				 //String jsonString = "{\r\n \"text\": \"Google\",\r\n\"reply_markup\": {\r\n \"inline_keyboard\": [\r\n [\r\n{\r\n \"text\": \"google\",\r\n \"url\": \"www.google.com\"\r\n }\r\n ]\r\n ]\r\n }\r\n }";
				 JsonParser jsonParser = new JsonParser();
				 JsonObject jo = (JsonObject)jsonParser.parse(new Gson().toJson(builder));
				Map<String, JsonElement> map = new HashMap<>();
				map.put("telegram", jo);
				output.setData(map);
				// returnMsg ="exit";
			}
			if (intentName.equals("login")) {
				returnMsg = authenticationService.isValidUser(input);
			}

			// for assignment
			if (intentName.equals("getAllAssignment")) {
				returnMsg = assignmentService.getAllAssignment(input);
			} else if (intentName.equals("getAssignmentByTitle")) {
				returnMsg = assignmentService.getAssignmentByTitle(input);
			} else if (intentName.equals("addAssignmentByTitle")) {
				returnMsg = assignmentService.addAssignmentByTitle(input);
			} else if (intentName.equals("changeAssignmentByTitle")) {
				returnMsg = assignmentService.changeAssignmentByTitle(input);
			} else if (intentName.equals("deleteAssignmentByTitle")) {
				returnMsg = assignmentService.deleteAssignmentByTitle(input);
			} else if (intentName.equals("studentSubmitAssignment")) {
				returnMsg = assignmentService.studentSubmitAssignment(input);
			} else if (intentName.equals("getUnsubmitingGroup")) {
				// TODO
				returnMsg = assignmentService.getUnsubmitingGroup(input);
			} else if (intentName.equals("getAssSubmissionByAssTitleAndGroupNb")) {
				returnMsg = assignmentService.getAssSubmissionByAssTitleAndGroupNb(input);
			} else if (intentName.equals("getAssMarkByAssTitle")) {
				returnMsg = assignmentService.getAssMarkByAssTitle(input);
			} else if (intentName.equals("getAllUnmarkedAssignmentGroup")) {
				returnMsg = assignmentService.getAllUnmarkedAssignmentGroup(input);
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
