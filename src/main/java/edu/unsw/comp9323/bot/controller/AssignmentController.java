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
import java.util.List;

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

import edu.unsw.comp9323.bot.constant.Constant;
import edu.unsw.comp9323.bot.dao.Ass_studentDao;
import edu.unsw.comp9323.bot.dao.AssignmentDao;
import edu.unsw.comp9323.bot.dao.Person_infoDao;
import edu.unsw.comp9323.bot.dao.ResourceDao;
import edu.unsw.comp9323.bot.model.Ass_student;
import edu.unsw.comp9323.bot.model.Assignment;
import edu.unsw.comp9323.bot.model.Resource;

@RestController
@RequestMapping("/resource") // only hande rest request from url or upload html
public class AssignmentController {

	@Autowired
	AssignmentDao assignmentDao;
	@Autowired
	Assignment assignment;
	@Autowired
	ResourceDao resourceDao;
	@Autowired
	Resource resource;
	@Autowired
	Ass_student ass_student;
	@Autowired
	Person_infoDao person_infoDao;
	@Autowired
	Ass_studentDao ass_studentDao;

	/**
	 * download PDF by given resource id
	 * 
	 * @param response
	 * @param id
	 * @return download file
	 */
	@RequestMapping(value = "/downloadPDF/assignment/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public FileSystemResource downloadPDF_ass(HttpServletResponse response, @PathVariable("id") Long id) {
		System.out.println("downloadPDF");
		// get filePath from database
		resource = resourceDao.getResourceById(id);
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
	public ResponseEntity<byte[]> showPDF_ass(HttpServletRequest httpServletRequest, @PathVariable("id") Long id)
			throws IOException {
		System.out.println("showPDF");

		// get filePath from database
		System.out.println("id:" + id); // debug
		resource = resourceDao.getResourceById(id);
		System.out.println("resource: " + resource.toString()); // debug

		String filePath = resource.getPath();
		System.out.println("filePath: " + filePath); // debug

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
	 * @param name
	 * @param due_date_string
	 * @param zid
	 */
	@RequestMapping(value = "/assignment/add", method = RequestMethod.POST)
	public String setAssignment(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("due_date_string") String due_date_string, @RequestParam("author_zid") String author_zid) {
		System.out.println("setAssignment()");

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
			String filePath = "assignment/" + name + "/material/" + file.getOriginalFilename();
			Path path = Paths.get(Constant.ROOTPATH + filePath);
			Files.write(path, bytes);
			resource.setAss_id(assignment.getId());
			resource.setPath(filePath);
			resource.setTitle(file.getOriginalFilename());
			resource.setTimestamp(new Timestamp(System.currentTimeMillis()));
			resource.setAuthor(author_zid);
			System.out.println("inserting resource:" + resource.toString()); // debug
			resourceDao.uploadAssignResource(resource);
			System.out.println("new resource id: " + resource.getId().toString());
			return "<h1>success to add new assignment material</h1>";
		} catch (IOException e) {
			e.printStackTrace();
			return "<h1>fail to add new assignment material</h1>";
		}
	}

	/**
	 * update assignment and assignment resource. TODO multiple assignment materials
	 * 
	 * @param file
	 * @param name
	 * @param due_date_string
	 * @param zid
	 */
	@RequestMapping(value = "/assignment/update", method = RequestMethod.POST)
	public String updateAssignment(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("due_date_string") String due_date_string, @RequestParam("author_zid") String author_zid) {
		System.out.println("updateAssignment()");

		/*
		 * update ass table
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
		Long ass_id = assignmentDao.getAssignmentIdByTitle(name).getId();
		assignment.setId(ass_id);
		assignment.setName(name);
		assignment.setDue_date(due_date);

		// do insert ass
		System.out.println("update assignment:" + assignment.toString()); // debug
		assignmentDao.updateAssignment(assignment);// return back to assignment(id)
		System.out.println("updated assignment: " + assignment.toString());

		/*
		 * update resource table
		 */
		byte[] bytes;
		try {
			// delete old file(s) and row(s) in resource table
			List<Resource> toDeleteResources = resourceDao.getResourceByAssignment(ass_id);
			for (Resource toDeleteResource : toDeleteResources) {
				File toDeletefile = new File(Constant.ROOTPATH + toDeleteResource.getPath());
				toDeletefile.delete();
				resourceDao.deleteResource(toDeleteResource);
			}

			// add write new file(s)
			bytes = file.getBytes();
			String filePath = "assignment/" + name + "/material/" + file.getOriginalFilename();
			Path path = Paths.get(Constant.ROOTPATH + filePath);
			Files.write(path, bytes);

			// update resource row, now only single file
			resource.setAss_id(assignment.getId());
			resource.setPath(filePath);
			resource.setTitle(file.getOriginalFilename());
			resource.setTimestamp(new Timestamp(System.currentTimeMillis()));
			resource.setAuthor(author_zid);
			System.out.println("inserting resource:" + resource.toString()); // debug
			resourceDao.uploadAssignResource(resource);
			System.out.println("new resource id: " + resource.getId().toString());
			return "<h1>success to update new assignment material</h1>";
		} catch (IOException e) {
			e.printStackTrace();
			return "<h1>fail to update new assignment material</h1>";
		}
	}

	/**
	 * student submit assignment(only one file).
	 * 
	 * @param file
	 * @param ass_id
	 * @param zid
	 */
	@RequestMapping(value = "/assignment/submission", method = RequestMethod.POST)
	public String submission(@RequestParam("file") MultipartFile file, @RequestParam("ass_id") Long ass_id,
			@RequestParam("zid") String zid) {
		System.out.println("submission");
		/*
		 * insert into ass_student table
		 */
		ass_student.setAss_id(ass_id);
		// get group_nb
		Long group_nb = person_infoDao.findGroupNbByZid(zid);
		ass_student.setGroup_nb(group_nb);
		ass_student.setSubmit_time(new Timestamp(System.currentTimeMillis()));
		String assignment_title = assignmentDao.getAssignmentTitlesById(ass_id);

		byte[] bytes;
		try {
			// write file
			bytes = file.getBytes();
			String filePath = "assignment/" + assignment_title + "/submission/group" + group_nb + "/"
					+ file.getOriginalFilename();
			Path path = Paths.get(Constant.ROOTPATH + filePath);
			Files.write(path, bytes);

			// insert into ass_student table
			ass_student.setPath(filePath);
			System.out.println("inserting ass_student:" + ass_student.toString()); // debug
			ass_studentDao.setSubmission(ass_student);
			System.out.println("new ass_student id: " + ass_student.getId().toString());
			return "<h1>success to submit your assignment</h1>";
		} catch (IOException e) {
			e.printStackTrace();
			return "<h1>fail to submit your assignment</h1>";
		}
	}

	/**
	 * download submission by given ass_student id
	 * 
	 * @param response
	 * @param id
	 * @return download file
	 */
	@RequestMapping(value = "/download/submission/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public FileSystemResource download_submission(HttpServletResponse response, @PathVariable("id") Long id) {
		System.out.println("download_submission");

		// get filePath from database
		String filePath = ass_studentDao.gePathById(id);
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
	 * view submission PDF in browser by given ass_student id
	 *
	 * @param httpServletRequest
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/showPDF/submission/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> showPDF_submission(HttpServletRequest httpServletRequest, @PathVariable("id") Long id)
			throws IOException {
		System.out.println("showPDF_submission");

		// get filePath from database
		String filePath = ass_studentDao.gePathById(id);
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
}
