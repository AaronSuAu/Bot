package edu.unsw.comp9323.bot.service.impl;

import java.io.Serializable;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import ai.api.GsonFactory;
import ai.api.model.AIResponse;
import ai.api.model.Fulfillment;
@Component
public class AIWebHookService {
	private static final String RESPONSE_CONTENT_TYPE = "application/json";

	private static final String RESPONSE_CHARACTER_ENCODING = "utf-8";

	private static final long serialVersionUID = 1L;

	private final Gson gson = GsonFactory.getDefaultFactory().getGson();
	
	public void doWebhook(AIWebhookRequest input, Fulfillment output){
		output.setSpeech("Webhook said : " + input.getResult().getFulfillment().getSpeech());
	};

	  /**
	   * Web-hook request model class
	   */
	  public static class AIWebhookRequest extends AIResponse {
	    private static final long serialVersionUID = 1L;

	    private OriginalRequest originalRequest;

	    /**
	     * Get original request object
	     * @return <code>null</code> if original request undefined in
	     * request object
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
	     * @return <code>null</code> if source isn't defined in a request
	     */
	    public String getSource() {
	      return source;
	    }
	    /**
	     * Get data map
	     * @return <code>null</code> if data isn't defined in a request
	     */
	    public Map<String, ?> getData() {
	      return data;
	    }
	}
}
