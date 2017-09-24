package edu.unsw.comp9323.bot.util;

import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;

import javax.activity.InvalidActivityException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import edu.unsw.comp9323.bot.constant.Constant;
import edu.unsw.comp9323.bot.model.GMailAuthenticator;
import edu.unsw.comp9323.bot.model.Person_info;

@Service
public class EmailUtil {
	// GMail user name (just the part before "@gmail.com")

	public boolean validate(String emailStr) {
		Matcher matcher = Constant.VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	public boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	public Boolean sendFromGMail(ArrayList<String> to, String subject, String body, Person_info from_person)
			throws AddressException, MessagingException, InvalidActivityException {
		String from = "comp9323bot@gmail.com";
		String pass = "comp9323";
		System.out.println("sendFromGMail()");

		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(props, new GMailAuthenticator(from, pass));

		MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(from));
		InternetAddress[] toAddress = new InternetAddress[to.size()];

		// To get the array of addresses
		if (to.size() == 0) {
			InvalidActivityException email = new InvalidActivityException("One or more email is invalid.");
			throw email;
		}
		for (int i = 0; i < to.size(); i++) {
			toAddress[i] = new InternetAddress(to.get(i));
		}
		for (int i = 0; i < toAddress.length; i++) {
			if (!isValidEmailAddress(to.get(i))) {
				System.out.println(to.get(i));
				InvalidActivityException email = new InvalidActivityException("One or more email is invalid.");
				throw email;
			}
			message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		}

		message.setSubject(subject);
		body += "\n\n\nFrom:\n" + from_person.getName() + "(" + from_person.getZid() + ")" + "\n"
				+ from_person.getEmail();
		message.setText(body);
		Transport transport = session.getTransport("smtp");
		transport.connect(host, from, pass);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		System.out.println("Send email sucess");
		return true;
	}
}
