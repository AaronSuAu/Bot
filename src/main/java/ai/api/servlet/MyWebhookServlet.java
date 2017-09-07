package ai.api.servlet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebServlet;

import ai.api.model.Fulfillment;

@WebServlet("/webhook_servlet")
public class MyWebhookServlet extends AIWebhookServlet {
	@Override
	protected void doWebhook(AIWebhookRequest input, Fulfillment output) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		output.setSpeech(
				"You said: " + input.getResult().getFulfillment().getSpeech() + " at:" + dateFormat.format(date));
	}
}