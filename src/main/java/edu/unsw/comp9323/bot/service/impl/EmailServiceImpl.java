package edu.unsw.comp9323.bot.service.impl;

import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;
import edu.unsw.comp9323.bot.dao.*;
import edu.unsw.comp9323.bot.model.Person_info;
import edu.unsw.comp9323.bot.util.*;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activity.InvalidActivityException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import edu.unsw.comp9323.bot.service.EmailService;
@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	Person_infoDao userDao;
	@Override
	public String sendEmailToZid(AIWebhookRequest input) {
		ArrayList<String> receiver = new ArrayList<>();
		String falseStatement="";
		String trueStatement="Send email to ";
		String pattern = "^(\")(.*)(\")$";
		Pattern r = Pattern.compile(pattern);
		Matcher m;
		HashMap<String, JsonElement> result = input.getResult().getParameters();
		JsonPrimitive subject_sign = result.get("email-subject").getAsJsonObject().getAsJsonPrimitive("email-subject-sign");
		JsonPrimitive subject_value = result.get("email-subject").getAsJsonObject().getAsJsonPrimitive("an");
		String subject_string =subject_value.toString();
		m = r.matcher(subject_string);
	     if (m.find( )) {
	         subject_string =m.group(2); 
	      }else {
	         subject_string = subject_value.toString().replace("\"", "");
	      }	
//		System.out.println(subject_sign+":"+subject_string);
		
		JsonPrimitive body_sign = result.get("email-body").getAsJsonObject().getAsJsonPrimitive("email-body-sign");
		JsonPrimitive body_value = result.get("email-body").getAsJsonObject().getAsJsonPrimitive("an");
		String body_string="";
		m = r.matcher(body_value.toString());
	      if (m.find( )) {
	         body_string =m.group(2); 
	      }else {
	         body_string = body_value.toString().replace("\"", "");
	      }
//		System.out.println(body_sign+":"+body_string);
		
		JsonArray student = result.get("student-no").getAsJsonArray();
		ArrayList<String> arrays = new ArrayList<String>();
	    for (int i = 0; i < student.size(); i++) {
	    			String stu = student.get(i).toString().replace("\"", "");
	            arrays.add(stu);
	            trueStatement+=stu+" ";
	            Person_info find = new Person_info();
	            find.setZid(stu);
	        
	            List<Person_info> list = userDao.findUserFromZid(find);
	            if(list.size()==0) {
	            		System.out.println("Unable to find student "+stu+". Please check again");
	            		falseStatement+= "Unable to find student "+stu+". Please check again\n";
	            }
	            for(Person_info person:list) {
	            		System.out.println(stu+":"+person.getEmail());
	            		receiver.add(person.getEmail().toString());
	            		
	            }
	    }
	    trueStatement+="success.";

        String body = body_string;
        String subject = subject_string;

        try {
        		EmailUtil.sendFromGMail(receiver, subject, body);
		}  catch (AddressException ae) {
            System.out.println("Address are incorrect ... ");
            falseStatement+= "Address are incorrect ... \n";
        }
        catch (MessagingException me) {
            System.out.println("Username or Password are incorrect ...");
            falseStatement+= "Username or Password are incorrect ...\n";
        }catch (InvalidActivityException e) {
        		System.out.println("One or more email is invalid.");
            falseStatement+= "One or more email is invalid.\n";
		}
        
		if(falseStatement.length()==0)
			return trueStatement;
		else return falseStatement;

	}
	@Override
	public String sendEmailToGroup(AIWebhookRequest input) {
		ArrayList<String> receiver = new ArrayList<>();
		String falseStatement="";
		String trueStatement="Send email to ";
		String pattern = "^(\")(.*)(\")$";
		Pattern r = Pattern.compile(pattern);
		Matcher m;
		HashMap<String, JsonElement> result = input.getResult().getParameters();
		JsonPrimitive subject_sign = result.get("email-subject").getAsJsonObject().getAsJsonPrimitive("email-subject-sign");
		JsonPrimitive subject_value = result.get("email-subject").getAsJsonObject().getAsJsonPrimitive("an");
		String subject_string =subject_value.toString();
		m = r.matcher(subject_string);
	     if (m.find( )) {
	         subject_string =m.group(2); 
	      }else {
	         subject_string = subject_value.toString().replace("\"", "");
	      }	
//		System.out.println(subject_sign+":"+subject_string);
		
		JsonPrimitive body_sign = result.get("email-body").getAsJsonObject().getAsJsonPrimitive("email-body-sign");
		JsonPrimitive body_value = result.get("email-body").getAsJsonObject().getAsJsonPrimitive("an");
		String body_string="";
		m = r.matcher(body_value.toString());
	      if (m.find( )) {
	         body_string =m.group(2); 
	      }else {
	         body_string = body_value.toString().replace("\"", "");
	      }
//		System.out.println(body_sign+":"+body_string);
		
		JsonArray student = result.get("student-no").getAsJsonArray();
		ArrayList<String> arrays = new ArrayList<String>();
	    for (int i = 0; i < student.size(); i++) {
	    			String stu = student.get(i).toString().replace("\"", "");
	            arrays.add(stu);
	            trueStatement+=stu+" ";
	            Person_info find = new Person_info();
	            find.setZid(stu);
	        
	            List<Person_info> list = userDao.findUserFromZid(find);
	            if(list.size()==0) {
	            		System.out.println("Unable to find student "+stu+". Please check again");
	            		falseStatement+= "Unable to find student "+stu+". Please check again\n";
	            }
	            for(Person_info person:list) {
	            		System.out.println(stu+":"+person.getEmail());
	            		receiver.add(person.getEmail().toString());
	            		
	            }
	    }
	    trueStatement+="success.";
        String body = body_string;
        String subject = subject_string;

        try {
        		EmailUtil.sendFromGMail(receiver, subject, body);
		}  catch (AddressException ae) {
            System.out.println("Address are incorrect ... ");
            falseStatement+= "Address are incorrect ... \n";
        }
        catch (MessagingException me) {
            System.out.println("Username or Password are incorrect ...");
            falseStatement+= "Username or Password are incorrect ...\n";
        }catch (InvalidActivityException e) {
        		System.out.println("One or more email is invalid.");
            falseStatement+= "One or more email is invalid.\n";
		}
        
		if(falseStatement.length()==0)
			return trueStatement;
		else return falseStatement;
	}


}
