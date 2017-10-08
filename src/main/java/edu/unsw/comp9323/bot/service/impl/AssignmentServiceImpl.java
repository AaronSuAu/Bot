package edu.unsw.comp9323.bot.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unsw.comp9323.bot.constant.Constant;
import edu.unsw.comp9323.bot.dao.Ass_studentDao;
import edu.unsw.comp9323.bot.dao.AssignmentDao;
import edu.unsw.comp9323.bot.dao.Person_infoDao;
import edu.unsw.comp9323.bot.dao.ResourceDao;
import edu.unsw.comp9323.bot.dto.AssignmentInfoDto;
import edu.unsw.comp9323.bot.model.Ass_student;
import edu.unsw.comp9323.bot.model.Assignment;
import edu.unsw.comp9323.bot.model.Person_info;
import edu.unsw.comp9323.bot.service.AssignmentService;
import edu.unsw.comp9323.bot.service.EmailService;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;
import edu.unsw.comp9323.bot.util.AssignmentUtil;
import edu.unsw.comp9323.bot.util.EmailUtil;
import edu.unsw.comp9323.bot.util.UserIdentityUtil;
import edu.unsw.comp9323.bot.util.ValidationUtil;

@Service
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	AssignmentDao assignmentDao;
	@Autowired
	Assignment assignment;
	@Autowired
	Person_infoDao person_infoDao;
	@Autowired
	Ass_student ass_student;
	@Autowired
	Ass_studentDao ass_studentDao;
	@Autowired
	ValidationUtil validationUtil;
	@Autowired
	AssignmentUtil assignmentUtil;
	@Autowired
	ResourceDao resourceDao;
	@Autowired
	UserIdentityUtil userIdentityUtil;
	@Autowired
	EmailService emailService;
	@Autowired
	EmailUtil emailUtil;
	@Autowired
	Person_info person_info;

	/**
	 * return all assignments(list) including assignment name, due date, upload
	 * time, author and , material url(s)
	 * 
	 * TODO refine return format
	 */
	@Override
	public String getAllAssignment(AIWebhookRequest input) {
		System.out.println("getAllAssignment()"); // debug

		// Authorization
		if (!validationUtil.isLecturerOrStudent(input)) {
			return "Authorization fail";
		}

		List<AssignmentInfoDto> assignmentInfoDtoList = assignmentDao.findAll();

		return assignmentUtil.renderAssignmentReturnMsg(assignmentInfoDtoList);
	}

	/**
	 * return matched assignments(list) including assignment name, due date, upload
	 * time, author and , material url(s)
	 * 
	 * TODO refine return format
	 */
	@Override
	public String getAssignmentByTitle(AIWebhookRequest input) throws Exception {
		System.out.println("getAssignmentByTitle()"); // debug

		// Authorization
		if (!validationUtil.isLecturerOrStudent(input)) {
			return "Authorization fail";
		}

		String title = input.getResult().getParameters().get("assignment-title").getAsString();

		List<AssignmentInfoDto> assignmentInfoDtoList = assignmentDao.findAssignmentByTitle(title);

		return assignmentUtil.renderAssignmentReturnMsg(assignmentInfoDtoList);
	}

	/**
	 * generate url for get file
	 */
	@Override
	public String addAssignmentByTitle(AIWebhookRequest input) throws Exception {
		System.out.println("addAssignmentByTitle()"); // debug

		// Authorization
		if (!validationUtil.isLecturer(input)) {
			return "Authorization fail";
		}

		String assignment_title = input.getResult().getParameters().get("assignment-title").getAsString();
		List<String> allAassignmentTitles = assignmentDao.getAllAssignmentTitles();
		if (allAassignmentTitles.contains(assignment_title)) {
			System.out.println("titile name dupl");
			return "assignment:" + assignment_title + " has exist, do you want to change that assignment info?";
		}

		String due_date_string = input.getResult().getParameters().get("assignment-due_date").getAsString();
		String author_zid = userIdentityUtil.getIdentity(input).getZid();
		String type = "add";
		return "to upload assignment material: http://localhost:8080/page/upload/assignment_material?type=" + type
				+ "&assignment_title=" + assignment_title + "&due_date_string=" + due_date_string + "&author_zid="
				+ author_zid;
	}

	/**
	 * generate url for get file
	 * 
	 * TODO change from add assignment
	 */
	@Override
	public String changeAssignmentByTitle(AIWebhookRequest input) throws Exception {
		System.out.println("changeAssignmentByTitle()"); // debug

		// Authorization
		if (!validationUtil.isLecturer(input)) {
			return "Authorization fail";
		}

		String assignment_title = input.getResult().getParameters().get("assignment-title").getAsString();
		String due_date_string = input.getResult().getParameters().get("assignment-due_date").getAsString();
		String authro_zid = userIdentityUtil.getIdentity(input).getZid();
		String type = "update";
		return "to upload assignment material: http://localhost:8080/page/upload/assignment_material?type=" + type
				+ "&assignment_title=" + assignment_title + "&due_date_string=" + due_date_string + "&author_zid="
				+ authro_zid;
	}

	/**
	 * delete ass and corresponding materials
	 */
	@Override
	public String deleteAssignmentByTitle(AIWebhookRequest input) throws Exception {
		System.out.println("deleteAssignmentByTitle()"); // debug

		// Authorization
		if (!validationUtil.isLecturer(input)) {
			return "Authorization fail";
		}

		String title = input.getResult().getParameters().get("assignment-title").getAsString();
		assignment.setName(title);
		assignment = assignmentDao.getAssignmentIdByTitle(title);

		try {
			assignmentDao.deleteAssignmentByTitle(assignment);
			// delete success do delete file
			String filesDir = "assignment/" + assignment.getName() + "/material";
			File toDeletefiles = new File(Constant.ROOTPATH + filesDir);
			FileUtils.cleanDirectory(toDeletefiles);

			return "success to delete assignment: " + title;
		} catch (Exception e) {
			// delete fail
			e.printStackTrace();
			return "fail to delete assignment: " + title;
		}
	}

	/**
	 * student submit assignment by group
	 */
	@Override
	public String studentSubmitAssignment(AIWebhookRequest input) throws Exception {
		System.out.println("studentSubmitAssignment()"); // debug

		// Authorization
		if (!validationUtil.isStudent(input)) {
			return "Authorization fail";
		}

		String title = input.getResult().getParameters().get("assignment-title").getAsString();
		Long ass_id = null;
		// check assignment title if is valid
		assignment = assignmentDao.getAssignmentIdByTitle(title);
		if (assignment == null) {
			return "assignment title: " + title + " is not valid";
		} else {
			ass_id = assignment.getId();
		}
		String zid = userIdentityUtil.getIdentity(input).getZid();
		return "to upload your assignment: http://localhost:8080/page/upload/student_sbumission?ass_id=" + ass_id
				+ "&zid=" + zid;
	}

	/**
	 * TODO get all unsubmission group
	 * 
	 * TODO sql and after action: email group
	 */
	@Override
	public String getUnsubmitingGroup(AIWebhookRequest input) throws Exception {
		System.out.println("getUnsubmitingGroup()"); // debug

		// Authorization
		if (!validationUtil.isLecturer(input)) {
			return "Authorization fail";
		}

		List<Ass_student> ass_students = ass_studentDao.getAllUnsubmitGroups(); // TODO sql
		String returnMsg = "";
		for (Ass_student ass_student : ass_students) {
			String assignment_title = assignmentDao.getAssignmentTitlesById(ass_student.getAss_id());
			String tmp = "-Assignment title: " + assignment_title + " unsubmit group: group" + ass_student.getGroup_nb()
					+ "\n";
			returnMsg += tmp;
		}
		return returnMsg; // TODO send eamil these groups to remind them to submit assignment
	}

	/**
	 * get assignment submission by assignment title and group number
	 */
	@Override
	public String getAssSubmissionByAssTitleAndGroupNb(AIWebhookRequest input) throws Exception {
		System.out.println("getAssSubmissionByAssTitleAndGroupNb()"); // debug

		// Authorization
		if (!validationUtil.isLecturer(input)) {
			return "Authorization fail";
		}

		String assignment_title = input.getResult().getParameters().get("assignment-title").getAsString();
		assignment = assignmentDao.getAssignmentIdByTitle(assignment_title);
		if (assignment == null) {
			return "assignment title is wrong";
		} else {
			ass_student.setAss_id(assignment.getId());
		}
		Long group_nb = Long.parseLong(input.getResult().getParameters().get("group-nb").getAsString());
		ass_student.setGroup_nb(group_nb);
		ass_student = ass_studentDao.getSubmissionByIdAndGroup(ass_student);
		return assignmentUtil.renderSubmissionReturnMsg(ass_student);
	}

	/**
	 * student get assignment submission by assignment title and group number
	 */
	@Override
	public String getAssMarkByAssTitle(AIWebhookRequest input) throws Exception {
		System.out.println("getAssMarkByAssTitle()"); // debug

		// Authorization
		if (!validationUtil.isStudent(input)) {
			return "Authorization fail";
		}

		String assignment_title = input.getResult().getParameters().get("assignment-title").getAsString();
		assignment = assignmentDao.getAssignmentIdByTitle(assignment_title);
		if (assignment == null) {
			return "assignment title is wrong";
		} else {
			ass_student.setAss_id(assignment.getId());
		}

		String zid = userIdentityUtil.getIdentity(input).getZid();
		person_info = person_infoDao.getUserByZid(zid);
		Long group_nb = person_info.getGroup_nb();
		ass_student.setGroup_nb(group_nb);
		ass_student = ass_studentDao.getSubmissionByIdAndGroup(ass_student);
		return "your assignment of " + assignment_title + "'s mark is " + ass_student.getGrade();
	}

	/**
	 * get all not submission groups which submitted their assignments
	 */
	@Override
	public String getAllUnmarkedAssignmentGroup(AIWebhookRequest input) throws Exception {
		System.out.println("getAllUnmarkedAssignmentGroup()"); // debug

		// Authorization
		if (!validationUtil.isLecturer(input)) {
			return "Authorization fail";
		}

		List<Ass_student> unmarkedAss_students = ass_studentDao.getAllUnmarkedGroup();
		String returnMsg = "Unmarked groups:";
		for (Ass_student unmarkedAss_student : unmarkedAss_students) {
			// id is id of ass_student,instead of group_nb
			String assignment_title = assignmentDao.getAssignmentTitlesById(unmarkedAss_student.getAss_id());
			String tmp = " -group" + unmarkedAss_student.getGroup_nb() + " assignment title: " + assignment_title
					+ " to GET submission:http://localhost:8080/resource/showPDF/submission/"
					+ unmarkedAss_student.getId();
			returnMsg += tmp;
		}
		return returnMsg;
	}

	/**
	 * mark assignment by group nb, set grade in ass_student table
	 */
	@Override
	public String markAssignmentByGroupNb(AIWebhookRequest input) throws Exception {
		System.out.println("markAssignmentByGroupNb()"); // debug

		// Authorization
		if (!validationUtil.isLecturer(input)) {
			return "Authorization fail";
		}

		String title = input.getResult().getParameters().get("assignment-title").getAsString();
		Long group_nb = Long.parseLong(input.getResult().getParameters().get("group-nb").getAsString());
		assignment = assignmentDao.getAssignmentIdByTitle(title);
		Float grade = Float.parseFloat(input.getResult().getParameters().get("grade").getAsString());
		ass_student.setId(assignment.getId());
		ass_student.setGroup_nb(group_nb);
		ass_student.setGrade(grade);
		if (ass_studentDao.markById(ass_student)) {

			// send email to this group student for inform assignment mark release
			ArrayList<String> toEmails = person_infoDao.getEmailByGroupNb(group_nb);
			String subject = "Mark for Group" + group_nb.toString() + " is released";
			String body = "Hi, there\n you get " + grade + " for assignment of " + title + ".";
			String zid = userIdentityUtil.getIdentity(input).getZid();
			person_info = person_infoDao.getUserByZid(zid);
			// TODO ask if send email
			if (emailUtil.sendFromGMail(toEmails, subject, body, person_info)) {
				return "mark has set, sent to group for inform assignment mark release.";
			} else {
				return "fail to send email";
			}

		} else {
			return "something wrong, can you mark again";
		}
	}

}
