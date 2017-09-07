package edu.unsw.comp9323.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import ai.api.GsonFactory;
import ai.api.model.Fulfillment;
import edu.unsw.comp9323.bot.service.impl.AIWebHookService;
import edu.unsw.comp9323.bot.service.impl.AIWebHookService.AIWebhookRequest;

@RestController
@RequestMapping("/webhook/")
public class AIWebhookController {

	@Autowired
	AIWebHookService webHookService;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String webHookAI(@RequestBody String request) {

		Gson gson = GsonFactory.getDefaultFactory().getGson();
		Fulfillment output = new Fulfillment();

		webHookService.doWebhook(gson.fromJson(request, AIWebhookRequest.class), output);

		return gson.toJson(output);
	}
}
