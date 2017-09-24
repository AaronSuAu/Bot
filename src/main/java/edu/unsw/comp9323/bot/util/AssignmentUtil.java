package edu.unsw.comp9323.bot.util;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.unsw.comp9323.bot.constant.Constant;
import edu.unsw.comp9323.bot.dto.AssignmentInfoDto;
import edu.unsw.comp9323.bot.model.Ass_student;

@Service
public class AssignmentUtil {
	/**
	 * @param assignmentInfoDtoList
	 * @return string of formated assignments information including assignment name,
	 *         due date, upload time, author and , material url(s)
	 */
	public String renderAssignmentReturnMsg(List<AssignmentInfoDto> assignmentInfoDtoList) {
		String returnMsg = "";
		String hostStringLocal = "localhost:8080";
		String hostStringGCloud = "cmbot-b3f5e.appspot.com";

		for (AssignmentInfoDto assignmentInfoDto : assignmentInfoDtoList) {
			String info = " -" + assignmentInfoDto.toString();
			String show_url_localhost = Constant.DOMAIN_NAME + "/file/showPDF/resource/"
					+ assignmentInfoDto.getMaterial_id();
			String download_url_localhost = Constant.DOMAIN_NAME + "/file/download/resource/"
					+ assignmentInfoDto.getMaterial_id();
			returnMsg = returnMsg + info + show_url_localhost + " " + download_url_localhost;
		}

		// for (AssignmentInfoDto assignmentInfoDto : assignmentInfoDtoList) {
		// String info = "-" + assignmentInfoDto.toString();
		// String show_url_localhost = "\nhttp://" + hostStringLocal +
		// "/resource/showPDF/assignment/"
		// + assignmentInfoDto.getMaterial_id() + "\n";
		// String show_url_gcloud = "\nhttp://" + hostStringGCloud +
		// "/resource/showPDF/assignment/"
		// + assignmentInfoDto.getMaterial_id() + "\n";
		// String download_url_localhost = "\nhttp://" + hostStringLocal +
		// "/resource/downloadPDF/assignment/"
		// + assignmentInfoDto.getMaterial_id() + "\n";
		// String download_url_gcloud = "\nhttp://" + hostStringGCloud +
		// "/resource/downloadPDF/assignment/"
		// + assignmentInfoDto.getMaterial_id() + "\n";
		// returnMsg = returnMsg + info + show_url_localhost + show_url_gcloud +
		// download_url_localhost
		// + download_url_gcloud + "\n";
		// }

		return returnMsg;
	}

	public String renderSubmissionReturnMsg(Ass_student ass_student) {
		String returnMsg = ass_student.toString();
		String hostStringLocal = "localhost:8080";
		String hostStringGCloud = "cmbot-b3f5e.appspot.com";

		returnMsg += " to get submission: "+Constant.DOMAIN_NAME + "/resource/showPDF/submission/"
				+ ass_student.getId();

		return returnMsg;
	}
}
