package edu.unsw.comp9323.bot.util;

import java.util.List;

import edu.unsw.comp9323.bot.dto.AssignmentInfoDto;

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
			String info = "-" + assignmentInfoDto.toString();
			String show_url_localhost = "\nhttp://" + hostStringLocal + "/resource/showPDF/assignment/"
					+ assignmentInfoDto.getMaterial_id() + "\n";
			String show_url_gcloud = "\nhttp://" + hostStringGCloud + "/resource/showPDF/assignment/"
					+ assignmentInfoDto.getMaterial_id() + "\n";
			String download_url_localhost = "\nhttp://" + hostStringLocal + "/resource/downloadPDF/assignment/"
					+ assignmentInfoDto.getMaterial_id() + "\n";
			String download_url_gcloud = "\nhttp://" + hostStringGCloud + "/resource/downloadPDF/assignment/"
					+ assignmentInfoDto.getMaterial_id() + "\n";
			returnMsg = returnMsg + info + show_url_localhost + show_url_gcloud + download_url_localhost
					+ download_url_gcloud + "\n";
		}

		return returnMsg;
	}
}
