package edu.unsw.comp9323.bot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unsw.comp9323.bot.dao.Ass_studentDao;
import edu.unsw.comp9323.bot.dao.AssignmentDao;
import edu.unsw.comp9323.bot.dao.Person_infoDao;
import edu.unsw.comp9323.bot.dto.AssignmentInfoDto;
import edu.unsw.comp9323.bot.model.Ass_student;
import edu.unsw.comp9323.bot.model.Assignment;
import edu.unsw.comp9323.bot.model.Person_info;
import edu.unsw.comp9323.bot.service.AssignmentService;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;
import edu.unsw.comp9323.bot.util.assignmentUitl;

@Service
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	AssignmentDao assignmentDao;
	@Autowired
	Assignment assignment;
	@Autowired
	Person_info person_info;
	@Autowired
	Person_infoDao person_infoDao;
	@Autowired
	Ass_student ass_student;
	@Autowired
	Ass_studentDao ass_studentDao;

	/**
	 * return all assignments(list) including assignment name, due date, upload
	 * time, author and , material url(s)
	 * 
	 * TODO refine return format
	 */
	@Override
	public String getAllAssignment(AIWebhookRequest input) {
		System.out.println("getAllAssignment()"); // debug
		// TODO authentication authorization
		List<AssignmentInfoDto> assignmentInfoDtoList = assignmentDao.findAll();
		System.out.println(assignmentInfoDtoList.toString()); // debug

		return assignmentUitl.renderAssignmentReturnMsg(assignmentInfoDtoList);
	}

	/**
	 * return matched assignments(list) including assignment name, due date, upload
	 * time, author and , material url(s)
	 * 
	 * TODO refine return format
	 */
	@Override
	public String getAssignmentByTitle(AIWebhookRequest input) {
		System.out.println("getAssignmentByTitle()"); // debug
		// TODO authentication authorization
		String title = input.getResult().getParameters().get("assignment-title").getAsString();
		System.out.println("assignment-title: " + title); // debug

		List<AssignmentInfoDto> assignmentInfoDtoList = assignmentDao.findAssignmentByTitle(title);

		return assignmentUitl.renderAssignmentReturnMsg(assignmentInfoDtoList);
	}

	/**
	 * generate url for get file
	 */
	@Override
	public String addAssignmentByTitle(AIWebhookRequest input) {
		System.out.println("addAssignmentByTitle()"); // debug
		// TODO authentication authorization
		String assignment_title = input.getResult().getParameters().get("assignment-title").getAsString();
		List<String> allAassignmentTitles = assignmentDao.getAllAssignmentTitles();
		if (allAassignmentTitles.contains(assignment_title)) {
			System.out.println("titile name dupl");
			return "assignment:" + assignment_title + " has exist, do you want to change that assignment info?";
		}
		String due_date_string = input.getResult().getParameters().get("assignment-due_date").getAsString();
		String authro_zid = input.getResult().getParameters().get("assignment-author_zid").getAsString();
		String type = "add_assignment";
		// TODO jump to form html for get file from user, return back url is below
		return "http://localhost:8080/assignment/add?assignment_title=" + assignment_title + "&due_date_string="
				+ due_date_string + "&authro_zid=" + authro_zid;
	}

	/**
	 * generate url for get file
	 * 
	 * TODO change from add assignment
	 */
	@Override
	public String changeAssignmentByTitle(AIWebhookRequest input) {
		System.out.println("changeAssignmentByTitle()"); // debug
		// TODO authentication authorization
		String assignment_title = input.getResult().getParameters().get("assignment-title").getAsString();
		String due_date_string = input.getResult().getParameters().get("assignment-due_date").getAsString();
		String authro_zid = input.getResult().getParameters().get("assignment-author_zid").getAsString();
		String type = "update_assignment";
		// TODO jump to form html for get file from user return back url is below
		return "http://localhost:8080/assignment/update?&assignment_title=" + assignment_title + "&due_date_string="
				+ due_date_string + "&authro_zid=" + authro_zid;
	}

	/**
	 * delete ass and corresponding materials
	 */
	@Override
	public String deleteAssignmentByTitlr(AIWebhookRequest input) {
		System.out.println("deleteAssignmentByTitlr()"); // debug
		// TODO authentication authorization
		String title = input.getResult().getParameters().get("assignment-title").getAsString();
		System.out.println("assignment-title: " + title); // debug

		try {
			assignmentDao.deleteAssignmentByTitle(title);
			// delete success
			return "delete assignment:" + title + "success.";
		} catch (Exception e) { // TODO how to catch exception
			// delete fail
			return "delete assignment:" + title + "success.";
		}
	}

	/**
	 * student submit assignment by group
	 */
	@Override
	public String studentSubmitAssignment(AIWebhookRequest input) {
		System.out.println("studentSubmitAssignment()"); // debug
		// TODO authentication authorization
		String title = input.getResult().getParameters().get("assignment-title").getAsString();
		Long ass_id = null;
		// check assignment title if is valid
		assignment = assignmentDao.getAssignmentIdByTitle(title);
		if (assignment == null) {
			return "assignment title: " + title + " is not valid";
		} else {
			ass_id = assignment.getId();
		}
		String zid = input.getResult().getParameters().get("zid").getAsString();
		// TODO jump to form html for get file from user return back url is below
		return "POST file http://localhost:8080/resource/assignment/submission?&ass_id=" + ass_id + "&zid=" + zid;
	}

	/**
	 * get all unsubmission group
	 * 
	 * TODO sql and after action: email group
	 */
	@Override
	public String getUnsubmitingGroup(AIWebhookRequest input) {
		System.out.println("getUnsubmitingGroup()"); // debug
		// TODO authentication authorization
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
	public String getAssSubmissionByAssTitleAndGroupNb(AIWebhookRequest input) {
		System.out.println("getAssSubmissionByAssTitleAndGroupNb()"); // debug
		// TODO authentication authorization
		String assignment_title = input.getResult().getParameters().get("assignment-title").getAsString();
		assignment = assignmentDao.getAssignmentIdByTitle(assignment_title);
		if (assignment == null) {
			return "assignment title is wrong";
		} else {
			ass_student.setAss_id(assignment.getId());
		}
		Long group_nb = Long.parseLong(input.getResult().getParameters().get("group_nb").getAsString());
		ass_student.setGroup_nb(group_nb);
		ass_student = ass_studentDao.getSubmissionByIdAndGroup(ass_student);
		return ass_student.toString()
				+ " to GET submission:http://localhost:8080/resource/download/assignment/submission/"
				+ ass_student.getId() + "\n";
		// TODO show url:http://localhost:8080/resource/showPDF/assignment/submission/4
	}

	/**
	 * get all not submission groups
	 */
	@Override
	public String getAllUnmarkedAssignmentGroup(AIWebhookRequest input) {
		System.out.println("getAllUnmarkedAssignmentGroup()"); // debug
		// TODO authentication authorization
		List<Ass_student> unmarkedAss_students = ass_studentDao.getAllUnmarkedGroup();
		String returnMsg = "Unmarked groups:\n";
		for (Ass_student unmarkedAss_student : unmarkedAss_students) {
			// id is id of ass_student,instead of group_nb
			String assignment_title = assignmentDao.getAssignmentTitlesById(unmarkedAss_student.getAss_id());
			String tmp = " group" + unmarkedAss_student.getGroup_nb() + " assignment title: " + assignment_title
					+ " to GET submission:http://localhost:8080/assignment/submission/" + unmarkedAss_student.getId()
					+ "\n";
			returnMsg += tmp;
		}
		return returnMsg;
	}

	/**
	 * mark assignment by group nb, set grade in ass_student table
	 */
	@Override
	public String markAssignmentByGroupNb(AIWebhookRequest input) {
		System.out.println("markAssignmentByGroupNb()"); // debug
		// TODO authentication authorization
		Long ass_studentId = Long.parseLong(input.getResult().getParameters().get("ass_studentId").getAsString());
		Float grade = Float.parseFloat(input.getResult().getParameters().get("grade").getAsString());
		ass_student.setId(ass_studentId);
		ass_student.setGrade(grade);
		if (ass_studentDao.markById(ass_student)) {
			return "mark has set";
		} else {
			return "something wrong, can you mark again";
		}
	}

}
