package edu.unsw.comp9323.bot.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.unsw.comp9323.bot.dao.EmailDao;
import edu.unsw.comp9323.bot.dao.Person_infoDao;
import edu.unsw.comp9323.bot.model.Email;
import edu.unsw.comp9323.bot.model.Person_info;
import edu.unsw.comp9323.bot.util.EmailUtil;

@Component
public class SendEmailScheduler {
	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private EmailDao emaildao;

	@Autowired
	Person_infoDao person_infoDao;

	@Autowired
	Person_info person_info;

	@Scheduled(cron = "*/1 * * * * *")
	public void scheduleSendEmail() {
		// System.out.println("here!!!!!!!!!!!!");
		// get info from db
		List<Email> emails = emaildao.findUnsentEmail();
		// send
		for (Email email : emails) {
			Long email_id = email.getId();
			Person_info from_person = person_infoDao.getUserByZid(email.getSender_id());
			List<String> receivers = emaildao.getReceivers(email_id);
			ArrayList<String> to_email = new ArrayList<>();
			for (String zid : receivers) {
				Person_info person = person_infoDao.getUserByZid(zid);
				System.out.println("------Schedule to send to " + person.getEmail());
				to_email.add(person.getEmail());
			}

			emailUtil.sendFromGMail(to_email, email.getSubject(), email.getBody(), from_person);

			emaildao.updateEmailFlag((long) 1, email_id);
		}

	}
}
