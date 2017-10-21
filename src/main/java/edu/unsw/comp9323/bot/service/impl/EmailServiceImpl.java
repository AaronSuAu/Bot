package edu.unsw.comp9323.bot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import edu.unsw.comp9323.bot.dao.AssignmentDao;
import edu.unsw.comp9323.bot.dao.EmailDao;
import edu.unsw.comp9323.bot.dao.Person_infoDao;
import edu.unsw.comp9323.bot.model.Email;
import edu.unsw.comp9323.bot.model.Identity;
import edu.unsw.comp9323.bot.model.Person_info;
import edu.unsw.comp9323.bot.service.EmailService;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;
import edu.unsw.comp9323.bot.util.EmailUtil;
import edu.unsw.comp9323.bot.util.UserIdentityUtil;
import edu.unsw.comp9323.bot.util.ValidationUtil;

@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	Person_infoDao person_infoDao;

	@Autowired
	ValidationUtil validationUtil;

	@Autowired
	EmailUtil emailUtil;

	@Autowired
	Person_info person_info;

	@Autowired
	UserIdentityUtil userIdentityUtil;

	@Autowired
	AssignmentDao assignmentDao;

	@Autowired
	EmailService emailService;

	@Autowired
	EmailDao emaildao;

	@Override
	public String sendEmailToZid(AIWebhookRequest input) {

		if (!validationUtil.isLecturerOrStudent(input)) {
			return "Authentication failed, please reenter your detailed";
		}
		System.out.println("Authentication verified");

		Person_info from_person = getSender(input);

		ArrayList<String> receiver = new ArrayList<>();
		String falseStatement = "";
		String trueStatement = "Send email to ";

		HashMap<String, JsonElement> result = input.getResult().getParameters();
//		JsonPrimitive subject_sign = result.get("email-subject").getAsJsonObject()
//				.getAsJsonPrimitive("email-subject-sign");
//		JsonPrimitive subject_value = result.get("email-subject").getAsJsonObject().getAsJsonPrimitive("an");
//		String subject_string = subject_value.getAsString();
//
//		JsonPrimitive body_sign = result.get("email-body").getAsJsonObject().getAsJsonPrimitive("email-body-sign");
//		JsonPrimitive body_value = result.get("email-body").getAsJsonObject().getAsJsonPrimitive("an");
//		String body_string = body_value.getAsString();
		String subject_string = result.get("email-subject").getAsString();
		String body_string = result.get("email-body").getAsString();
		JsonArray student = result.get("student-no").getAsJsonArray();
		System.out.println(subject_string);
		System.out.println(body_string);
		// sending to whole class
		if (student.get(0).getAsString().toLowerCase().contains("all")
				|| student.get(0).getAsString().toLowerCase().contains("class")) {

			trueStatement += "whole class ";
			List<Person_info> list = person_infoDao.findAll();
			if (list.size() == 0) {
				System.out.println("Unable to find any student. Please check again");
				falseStatement += "Unable to find any student. Please check again\n";
			}
			for (Person_info person : list) {

				if (person.getEmail() == null) {
					System.out.println("Unable to find email for student " + person.getZid() + ". Please check again");
					falseStatement += "Unable to find email for student " + person.getZid() + ". Please check again\n";
				} else {
					trueStatement += person.getZid();
					System.out.println(person.getZid() + ":" + person.getEmail());
					// receiver.add(person.getEmail().toString());
					receiver.add(person.getZid());
				}

			}
		} else {
			// sending to some of the students
			for (int i = 0; i < student.size(); i++) {
				String stu = student.get(i).toString().replace("\"", "");
				if (stu.toLowerCase().contains("group")) {// if sending to group
					System.out.println("sending to " + stu);
					Long group_nb = Long.valueOf(stu.toLowerCase().replace("group", "")).longValue();
					List<Person_info> list = person_infoDao.getUserByGroupNb(group_nb);
					for (Person_info person : list) {
						if (person.getEmail() == null) {
							System.out.println("Unable to find email for student " + stu + ". Please check again");
							falseStatement += "Unable to find email for student " + stu + ". Please check again\n";
						} else {
							trueStatement += person.getZid() + "(" + stu + ") ";
							System.out.println(person.getZid() + ":" + person.getEmail());
							// receiver.add(person.getEmail().toString());
							receiver.add(person.getZid());
						}
					}
				} else {// sending to individual
					trueStatement += stu + " ";
					Person_info find = new Person_info();
					find.setZid(stu);

					List<Person_info> list = person_infoDao.findUserFromZid(find);
					if (list.size() == 0) {
						System.out.println("Unable to find student " + stu + ". Please check again");
						falseStatement += "Unable to find student " + stu + ". Please check again\n";
					}
					for (Person_info person : list) {
						if (person.getEmail() == null) {
							System.out.println("Unable to find email for student " + stu + ". Please check again");
							falseStatement += "Unable to find email for student " + stu + ". Please check again\n";
						} else {
							System.out.println(person.getZid() + ":" + person.getEmail());
							// receiver.add(person.getEmail().toString());
							receiver.add(person.getZid());
						}

					}
				}
			}
			trueStatement += "success.";
		}

		String body = body_string;
		String subject = subject_string;

		System.out.println("insert into email table...");
		// create a email object
		Email email = new Email(from_person.getZid(), subject, body, (long) 0);
		// insert into db
		emaildao.insertEmail(email);
		if (receiver.size() == 0) {
			System.out.println("One or more email is invalid.");
		}
		for (String s : receiver) {
			System.out.println(email.getId() + ": " + s);
			emaildao.addReceivers(email.getId(), s);
		}
		// try {
		// emailUtil.sendFromGMail(receiver, subject, body, from_person);
		//
		// } catch (AddressException ae) {
		// System.out.println("Address are incorrect ... ");
		// falseStatement += "Address are incorrect ... \n";
		// } catch (MessagingException me) {
		// System.out.println("Username or Password are incorrect ...");
		// falseStatement += "Username or Password are incorrect ...\n";
		// } catch (InvalidActivityException e) {
		// System.out.println("One or more email is invalid.");
		// falseStatement += "One or more email is invalid.\n";
		// }

		System.out.println("trueStatement" + trueStatement);
		System.out.println("falseStatement" + falseStatement);
		if (falseStatement.length() == 0)
			return trueStatement;
		else
			return falseStatement + trueStatement;

	}

	@Override
	public String sendEmailTemplate(AIWebhookRequest input) {
		System.out.println("sendEmailTemplate");
		if (!validationUtil.isLecturerOrStudent(input)) {
			return "Authentication failed, please reenter your detailed";
		}
		System.out.println("Authentication verified");

		Person_info from_person = getSender(input);

		ArrayList<String> receiver = new ArrayList<>();
		String falseStatement = "";
		String trueStatement = "Send email to ";

		HashMap<String, JsonElement> result = input.getResult().getParameters();
		System.out.println(result);

		JsonArray student = result.get("student-no").getAsJsonArray();

		// sending to whole class
		if (student.get(0).getAsString().toLowerCase().contains("all")
				|| student.get(0).getAsString().toLowerCase().contains("class")) {

			trueStatement += "whole class ";
			List<Person_info> list = person_infoDao.findAll();
			if (list.size() == 0) {
				System.out.println("Unable to find any student. Please check again");
				falseStatement += "Unable to find any student. Please check again\n";
			}
			for (Person_info person : list) {

				if (person.getEmail() == null) {
					System.out.println("Unable to find email for student " + person.getZid() + ". Please check again");
					falseStatement += "Unable to find email for student " + person.getZid() + ". Please check again\n";
				} else {
					trueStatement += person.getZid();
					System.out.println(person.getZid() + ":" + person.getEmail());
					// receiver.add(person.getEmail().toString());
					receiver.add(person.getZid());
				}

			}
		} else {
			// sending to some of the students
			for (int i = 0; i < student.size(); i++) {
				String stu = student.get(i).toString().replace("\"", "");
				if (stu.toLowerCase().contains("group")) {// if sending to group
					System.out.println("sending to " + stu);
					Long group_nb = Long.valueOf(stu.toLowerCase().replace("group", "")).longValue();
					List<Person_info> list = person_infoDao.getUserByGroupNb(group_nb);
					for (Person_info person : list) {
						if (person.getEmail() == null) {
							System.out.println("Unable to find email for student " + stu + ". Please check again");
							falseStatement += "Unable to find email for student " + stu + ". Please check again\n";
						} else {
							trueStatement += person.getZid() + "(" + stu + ") ";
							System.out.println(person.getZid() + ":" + person.getEmail());
							// receiver.add(person.getEmail().toString());
							receiver.add(person.getZid());
						}
					}
				} else {// sending to individual
					trueStatement += stu + " ";
					Person_info find = new Person_info();
					find.setZid(stu);

					List<Person_info> list = person_infoDao.findUserFromZid(find);
					if (list.size() == 0) {
						System.out.println("Unable to find student " + stu + ". Please check again");
						falseStatement += "Unable to find student " + stu + ". Please check again\n";
					}
					for (Person_info person : list) {
						if (person.getEmail() == null) {
							System.out.println("Unable to find email for student " + stu + ". Please check again");
							falseStatement += "Unable to find email for student " + stu + ". Please check again\n";
						} else {
							System.out.println(person.getZid() + ":" + person.getEmail());
							// receiver.add(person.getEmail().toString());
							receiver.add(person.getZid());
						}

					}
				}
			}
			trueStatement += "success.";
		}

		String emailTemplate = result.get("email-template").getAsJsonObject().getAsJsonPrimitive("an").getAsString();
		System.out.println(emailTemplate);

		String subject = Character.toUpperCase(emailTemplate.charAt(0)) + emailTemplate.substring(1);
		String body = getBodyOfTemplatedEmail(emailTemplate);

		System.out.println("insert into email table...");
		// create a email object
		Email email = new Email(from_person.getZid(), subject, body, (long) 0);
		// insert into db
		emaildao.insertEmail(email);
		if (receiver.size() == 0) {
			System.out.println("One or more email is invalid.");
		}
		for (String s : receiver) {
			System.out.println(email.getId() + ": " + s);
			emaildao.addReceivers(email.getId(), s);
		}
		// try {
		// emailUtil.sendFromGMail(receiver, subject, body, from_person);
		// } catch (AddressException ae) {
		// System.out.println("Address are incorrect ... ");
		// falseStatement += "Address are incorrect ... \n";
		// } catch (MessagingException me) {
		// System.out.println("Username or Password are incorrect ...");
		// falseStatement += "Username or Password are incorrect ...\n";
		// } catch (InvalidActivityException e) {
		// System.out.println("One or more email is invalid.");
		// falseStatement += "One or more email is invalid.\n";
		// }
		System.out.println("true: " + trueStatement);
		System.out.println("false :" + falseStatement + " " + falseStatement.length());
		if (falseStatement.length() == 0) {
			System.out.println("true: " + trueStatement);
			return trueStatement;
		} else
			return falseStatement + trueStatement;
	}

	private String getBodyOfTemplatedEmail(String emailTemplate) {
		String result = "";

		if (emailTemplate.toLowerCase().contains("cancel")) {
			result = "Hello All,\nI'm sorry to inform you that due to personal issue I need to cancel ";
			if (emailTemplate.toLowerCase().contains("today")) {
				result += "today's ";
			} else if (emailTemplate.toLowerCase().contains("tomorrow")) {
				result += "tomorrow's ";
			} else if (emailTemplate.toLowerCase().contains("this week")) {
				result += "this week's ";
			} else if (emailTemplate.toLowerCase().contains("next week")) {
				result += "next week's ";
			}
			result += "class.";
		} else if (emailTemplate.toLowerCase().contains("sick")) {
			result = "Hello All,\nI'm sorry to inform you I am terribly sick these days so I need to cancel ";
			if (emailTemplate.toLowerCase().contains("today")) {
				result += "today's ";
			} else if (emailTemplate.toLowerCase().contains("tomorrow")) {
				result += "tomorrow's ";
			} else if (emailTemplate.toLowerCase().contains("this week")) {
				result += "this week's ";
			} else if (emailTemplate.toLowerCase().contains("next week")) {
				result += "next week's ";
			}
			result += "class.";
		} else {
			result = emailTemplate;
		}
		return result;
	}

	private Person_info getSender(AIWebhookRequest input) {

		Identity identity = userIdentityUtil.getIdentity(input);
		return person_infoDao.getUserByZid(identity.getZid());
	}

	@Override
	public String sendEmailToAllStudent(String subject, String body, Person_info from_person) {
		ArrayList<String> receiver = person_infoDao.getAllStudentEmails();
		System.out.println("insert into email table...");
		// create a email object
		Email email = new Email(from_person.getZid(), subject, body, (long) 0);
		// insert into db
		emaildao.insertEmail(email);
		if (receiver.size() == 0) {
			return "Fail to email all students email for informing new assignment. One or more email is invalid.";
		}
		for (String s : receiver) {
			System.out.println(email.getId() + ": " + s);
			emaildao.addReceivers(email.getId(), s);
		}
		// try {
		// emailUtil.sendFromGMail(toEmails, subject, body, from);
		// } catch (Exception e) {
		// e.printStackTrace();
		// return "Fail to email all students email for informing new assignment";
		// }

		return "Sent to all students email of informing new assignment";
	}

}
