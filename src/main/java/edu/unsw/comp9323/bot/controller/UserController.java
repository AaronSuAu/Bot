package edu.unsw.comp9323.bot.controller;

import java.io.BufferedInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcProperties.View;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.unsw.comp9323.bot.model.Resource;
import edu.unsw.comp9323.bot.model.Person_info;
import edu.unsw.comp9323.bot.service.impl.ResourceServiceImpl;
import edu.unsw.comp9323.bot.service.impl.Person_infoServiceImpl;

@RestController
@RequestMapping("/")
public class UserController {
	@Autowired
	Person_infoServiceImpl person_infoServiceImpl;
	
	@Autowired
	ResourceServiceImpl resourceServiceImpl;
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public int validateUser(@Param("zid") String user, @Param("password") String password) {
		Person_info person_info = new Person_info();
		person_info.setZid(user);
		person_info.setPassword(password);
		return person_infoServiceImpl.validateUser(person_info);
		
	}
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
	public ModelAndView returnUploadPage(@RequestParam("assignment_title") String title,
			@RequestParam("due_date_string") String due_date_string, 
			@RequestParam("zid") String zid){
		
		return new ModelAndView("upload");
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public void createUser(){
//		for(int i = 0; i<30; i++){
//			person_infoServiceImpl.createUser();
//		}
	}

	
	
}
