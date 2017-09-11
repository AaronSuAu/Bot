package edu.unsw.comp9323.bot.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.unsw.comp9323.bot.dao.AssignmentDao;
import edu.unsw.comp9323.bot.dao.ResourceDao;
import edu.unsw.comp9323.bot.model.Assignment;
import edu.unsw.comp9323.bot.model.Resource;
import edu.unsw.comp9323.bot.service.impl.ResourceServiceImpl;

@RestController
@RequestMapping("/resource") // only hande rest request from url or upload html
public class AssignmentController {

	@Autowired
	AssignmentDao assignmentDao;
	@Autowired
	Assignment assignment;
	@Autowired
	ResourceServiceImpl resourceServiceImpl;
	@Autowired
	ResourceDao resourceDao;
	@Autowired
	Resource resource;

	/**
	 * download PDF by given resource id
	 * 
	 * @param response
	 * @param id
	 * @return download file
	 */

	@RequestMapping(value = "/downloadPDF/assignment/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public FileSystemResource download(HttpServletResponse response, @PathVariable("id") Long id) {
		// get filePath from database
		System.out.println("id:" + id);
		resource = resourceDao.getAssignmentById(id);
		String filePath = resource.getPath();

		// access file under src/main/resources
		ClassLoader classLoader = getClass().getClassLoader();
		File fileObj = null;
		try {
			fileObj = new File(classLoader.getResource(filePath).getFile());
		} catch (Exception e) {
			System.out.println("file does not exit!");
		}

		response.setHeader("Content-Disposition", "attachment; filename=" + fileObj.getName());
		return new FileSystemResource(fileObj);
	}

	/**
	 * view PDF in browser by given resource id
	 * 
	 * @param httpServletRequest
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/showPDF/assignment/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> showPDF(HttpServletRequest httpServletRequest, @PathVariable("id") Long id)
			throws IOException {
		// get filePath from database
		System.out.println("id:" + id); // debug
		resource = resourceDao.getAssignmentById(id);
		System.out.println(resource.toString()); // debug

		String filePath = resource.getPath();
		System.out.println(filePath); // debug

		// access file under src/main/resources
		ClassLoader classLoader = getClass().getClassLoader();
		File fileObj = null;
		try {
			fileObj = new File(classLoader.getResource(filePath).getFile());
		} catch (Exception e) {
			System.out.println("file does not exit!");
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentDispositionFormData("attachment", java.net.URLEncoder.encode(fileObj.getName(), "UTF-8"));
		httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(fileObj), httpHeaders, HttpStatus.OK);
	}

	/**
	 * add assignment and assignment resource. TODO multiple assignment materials
	 * 
	 * @param file
	 * @param assignmentUploadDto
	 */
	@RequestMapping(value = "/upload/assignment", method = RequestMethod.POST)
	public void receiveResource(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("due_date_string") String due_date_string, @RequestParam("zid") String zid) {
		/*
		 * insert into ass table and return ass_id
		 */
		java.sql.Date due_date = null;
		try {
			// due_date need to follow certain formate: "January 2, 2010"
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date utilDate = simpleDateFormat.parse(due_date_string);
			due_date = new Date(utilDate.getTime());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		assignment.setName(name);
		assignment.setDue_date(due_date);

		// do insert ass
		System.out.println("inserting assignment:" + assignment.toString()); // debug
		assignmentDao.setAssignment(assignment);// return back to assignment(id)
		System.out.println("new assignment id: " + assignment.getId().toString());
		/*
		 * insert into resource table
		 */
		byte[] bytes;
		try {
			bytes = file.getBytes();
			String filePath = "src/main/resources/assignment/" + name + "/material/" + file.getOriginalFilename();
			Path path = Paths.get(filePath);
			Files.write(path, bytes);
			resource.setAss_id(assignment.getId());
			resource.setPath(filePath);
			resource.setTitle(file.getOriginalFilename());
			resource.setTimestamp(new Timestamp(System.currentTimeMillis()));
			resource.setAuthor(zid);
			System.out.println("inserting resource:" + resource.toString()); // debug
			resourceServiceImpl.uploadAssignResource(resource);
			System.out.println("new resource id: " + resource.getId().toString());
			// TODO jump to upload success page
		} catch (IOException e) {
			e.printStackTrace();
			// TODO jump to upload fail page
		}
	}

}
