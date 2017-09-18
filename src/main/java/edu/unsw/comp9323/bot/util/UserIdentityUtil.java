package edu.unsw.comp9323.bot.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.api.model.AIOutputContext;
import edu.unsw.comp9323.bot.model.Identity;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;

@Service
public class UserIdentityUtil {
	@Autowired
	Identity identity;

	public Identity getIdentity(AIWebhookRequest input) {
		List<AIOutputContext> aIOutputContexts = input.getResult().getContexts();
		String zid_from_context = null;
		String password_from_context = null;
		for (AIOutputContext aiOutputContext : aIOutputContexts) {
			if (aiOutputContext.getName().equals("zid")) {
				zid_from_context = aiOutputContext.getParameters().get("zid").getAsString();
				password_from_context = aiOutputContext.getParameters().get("password").getAsString();
			}
		}

		identity.setZid(zid_from_context);
		identity.setPassword(password_from_context);
		return identity;
	}
}
