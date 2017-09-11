package edu.unsw.comp9323.bot.service;

import ai.api.model.Fulfillment;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;

public interface AIWebbookService {
	void doWebhook(AIWebhookRequest input, Fulfillment output);
}
