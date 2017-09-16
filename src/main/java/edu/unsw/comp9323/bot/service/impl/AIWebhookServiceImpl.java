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

@Service
public class AIWebhookServiceImpl implements AIWebbookService {
	// private static final String RESPONSE_CONTENT_TYPE = "application/json";
	//
	// private static final String RESPONSE_CHARACTER_ENCODING = "utf-8";
	//
	// private static final long serialVersionUID = 1L;
	//
	// private final Gson gson = GsonFactory.getDefaultFactory().getGson();

	@Autowired
	private AssignmentService assignmentService;
	@Autowired
	private EmailService emailService;
	
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
		if (intentName.equals("getAllAssignment")) {
			returnMsg = assignmentService.getAllAssignment(input);
		} else if (intentName.equals("getAssignmentByTitle")) {
			returnMsg = assignmentService.getAssignmentByTitle(input);
		} else if (intentName.equals("addAssignmentByTitle")) {
			returnMsg = assignmentService.addAssignmentByTitle(input);
		} else if (intentName.equals("changeAssignmentByTitle")) {
			returnMsg = assignmentService.changeAssignmentByTitle(input);
		} else if (intentName.equals("deleteAssignmentByTitlr")) {
			returnMsg = assignmentService.deleteAssignmentByTitlr(input);
		} else {
			// not for assignment
		}

		// TODO for resource

		// TODO for reminder

		// TODO for email
		if (intentName.equals("send_email_to_zid")) {
			returnMsg = emailService.sendEmailToZid(input);
		} 
		// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// Date date = new Date();
		// output.setSpeech("Webhook get: intent name:" + intentName + "return at" +
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
