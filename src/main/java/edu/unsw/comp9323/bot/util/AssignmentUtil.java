package edu.unsw.comp9323.bot.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

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
		List<List<BasicButton>> bOuterList = new ArrayList<List<BasicButton>>();
		for (AssignmentInfoDto assignmentInfoDto : assignmentInfoDtoList) {
			String info = assignmentInfoDto.getName() + " Due date:" + assignmentInfoDto.getDue_date().toString();
			String show_url_localhost = Constant.DOMAIN_NAME + "/file/showPDF/resource/"
					+ assignmentInfoDto.getMaterial_id();
			String download_url_localhost = Constant.DOMAIN_NAME + "/file/download/resource/"
					+ assignmentInfoDto.getMaterial_id();
			BasicButton bB = new BasicButton(info, show_url_localhost);
			List<BasicButton> bList = new ArrayList<>();
			bList.add(bB);
			bOuterList.add(bList);
			returnMsg = returnMsg + info + show_url_localhost + " " + download_url_localhost;
		}
		Inline_Keyboard iKeyboard = new Inline_Keyboard(bOuterList);
		ButtonBuilder builder = new ButtonBuilder("Assignment List: ", iKeyboard);
		return new Gson().toJson(builder);

	}

	public String renderSubmissionReturnMsg(Ass_student ass_student) {
		List<List<BasicButton>> bOuterList = new ArrayList<List<BasicButton>>();
		BasicButton bB = new BasicButton("Assignment submission",
				Constant.DOMAIN_NAME + "/resource/showPDF/submission/" + ass_student.getId());
		List<BasicButton> bList = new ArrayList<>();
		bList.add(bB);
		bOuterList.add(bList);
		Inline_Keyboard iKeyboard = new Inline_Keyboard(bOuterList);
		ButtonBuilder builder = new ButtonBuilder("Click the following button to to get submission", iKeyboard);
		return new Gson().toJson(builder);

	}
}
