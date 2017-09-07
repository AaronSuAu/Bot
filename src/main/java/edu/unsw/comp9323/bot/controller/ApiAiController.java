package edu.unsw.comp9323.bot.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import ai.api.AIServiceContext;
import ai.api.AIServiceException;
import ai.api.GsonFactory;
import ai.api.model.AIResponse;
import ai.api.model.Fulfillment;
import edu.unsw.comp9323.bot.service.impl.AIService;
import edu.unsw.comp9323.bot.service.impl.AIWebHookService;
import edu.unsw.comp9323.bot.service.impl.AIWebHookService.AIWebhookRequest;

@RestController
@RequestMapping("/rest/")
public class ApiAiController {

	HttpSession session = null;
	AIServiceContext serviceContext = null;

	@Autowired
	AIService aiService;
	@Autowired
	AIWebHookService webHookService;
	
	@RequestMapping(value = "/ai", method = RequestMethod.GET)
	public String getAIResponse(@RequestParam("query") String query) {
		// HttpSession session = null;
		try {
			AIResponse ai = aiService.request(query, serviceContext);
			// return ai.getResult().getFulfillment().getSpeech();
			HashMap<String, JsonElement> result = ai.getResult().getParameters();
			if (result.get("assignment-title") != null) {
				return ai.getResult().getFulfillment().getSpeech() + result.get("assignment-title").toString();
			}
			return ai.getResult().getFulfillment().getSpeech();
		} catch (AIServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/webhook", method = RequestMethod.POST)
	public String webHookAI(@RequestBody String request){
		System.out.println(request);
		Gson gson = GsonFactory.getDefaultFactory().getGson();
		Fulfillment output = new Fulfillment();
		webHookService.doWebhook(gson.fromJson(request, AIWebhookRequest.class), output);
		return gson.toJson(output);
	}
}
