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

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
		person_info.setzId(user);
		person_info.setPassword(password);
		return person_infoServiceImpl.validateUser(person_info);
		
	}
	
}
