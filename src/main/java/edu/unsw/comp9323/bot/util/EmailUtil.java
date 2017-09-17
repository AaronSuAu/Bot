package edu.unsw.comp9323.bot.util;

import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activity.InvalidActivityException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import edu.unsw.comp9323.bot.model.GMailAuthenticator;


public class EmailUtil {
	// GMail user name (just the part before "@gmail.com")


		public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);

		public static boolean validate(String emailStr) {
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
			return matcher.find();
		}

		public static boolean isInteger(String s) {
			try {
				Integer.parseInt(s);
			} catch (NumberFormatException e) {
				return false;
			} catch (NullPointerException e) {
				return false;
			}
			return true;
		}

		public static boolean isValidEmailAddress(String email) {
			boolean result = true;
			try {
				InternetAddress emailAddr = new InternetAddress(email);
				emailAddr.validate();
			} catch (AddressException ex) {
				result = false;
			}
			return result;
		}

		public static Boolean sendFromGMail(ArrayList<String> to, String subject, String body)throws AddressException,MessagingException,InvalidActivityException {
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
            for( int i = 0; i < to.size(); i++ ) {
                toAddress[i] = new InternetAddress(to.get(i));
            }
            for( int i = 0; i < toAddress.length; i++) {
            		if(!isValidEmailAddress(to.get(i))) {
            			System.out.println(to.get(i));
            			InvalidActivityException email = new InvalidActivityException("One or more email is invalid.");
            			throw email;
            		}
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("Send email sucess");
            return true;
		}
}
