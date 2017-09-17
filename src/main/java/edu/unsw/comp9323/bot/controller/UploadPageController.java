package edu.unsw.comp9323.bot.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/page")
public class UploadPageController {

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public void createUser() {
		// for(int i = 0; i<30; i++){
		// person_infoServiceImpl.createUser();
		// }
	}

	@RequestMapping(value = "/upload/assignment_material", method = RequestMethod.GET)
	public ModelAndView page_upload_assignment_material(@RequestParam("assignment_title") String title,
			@RequestParam("due_date_string") String due_date_string, @RequestParam("author_zid") String authro_zid,
			@RequestParam("type") Optional<String> type) {
		return new ModelAndView("upload_ass_material");
	}

	@RequestMapping(value = "/upload/student_sbumission", method = RequestMethod.GET)
	public ModelAndView page_upload_student_sbumission(@RequestParam("ass_id") String ass_id,
			@RequestParam("zid") String zid) {
		return new ModelAndView("student_submission");
	}

	/*
	 * TODO var author_zid = getUrlParameter("author_zid"); var class_id =
	 * getUrlParameter("class_id"); var name = getUrlParameter("assignment_title");
	 * var type = getUrlParameter("type");
	 */
	@RequestMapping(value = "page/material/add", method = RequestMethod.GET)
	public ModelAndView returnUploadMaterialPage(@RequestParam("assignment_title") String title,
			@RequestParam("class_id") String class_id, @RequestParam("author_zid") String zid,
			@RequestParam Optional<String> type) {

		return new ModelAndView("upload_class_material");
	}

}
