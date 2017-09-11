package edu.unsw.comp9323.bot.util;

import java.util.List;

import edu.unsw.comp9323.bot.dto.AssignmentInfoDto;

public class assignmentUitl {
	public static String renderAssignmentReturnMsg(List<AssignmentInfoDto> assignmentInfoDtoList) {

		// only return url right now
		String returnMsg = "";
		String hostStringLocal = "localhost:8080";
		String hostStringGCloud = "cmbot-b3f5e.appspot.com";

		returnMsg += "*******************************Localhost URL***************************************\n ";

		for (AssignmentInfoDto assignmentInfoDto : assignmentInfoDtoList) {
			String info = " " + assignmentInfoDto.toString();
			String tmp = info + "\n http://" + hostStringLocal + "/rest/showPDF/assignment/"
					+ assignmentInfoDto.getName() + "/material/" + assignmentInfoDto.getMaterial_path() + ";\n";
			returnMsg += tmp;
		}

		returnMsg += "\n*******************************Google Cloud URL***************************************\n ";

		for (AssignmentInfoDto assignmentInfoDto : assignmentInfoDtoList) {
			String info = " " + assignmentInfoDto.toString();
			String tmp = info + "\n http://" + hostStringGCloud + "/rest/showPDF/assignment/"
					+ assignmentInfoDto.getName() + "/material/" + assignmentInfoDto.getMaterial_path() + ";\n ";
			returnMsg += tmp;
		}

		return returnMsg;
	}
}
