package edu.unsw.comp9323.bot.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class AssignmentController {
	// file field can not including suffix
	// http://localhost:8080/rest/downloadPDF/assignment/phase1/material/s.pdf
	@RequestMapping(value = "/downloadPDF/assignment/{phase}/material/{file}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public FileSystemResource download(HttpServletResponse response, @PathVariable("phase") String phase,
			@PathVariable("file") String file) {
		System.out.println(file);
		// access file under src/main/resources
		ClassLoader classLoader = getClass().getClassLoader();
		File fileObj = new File(
				classLoader.getResource("assignment/" + phase + "/material/" + file + ".pdf").getFile());
		response.setHeader("Content-Disposition", "attachment; filename=" + fileObj.getName());
		return new FileSystemResource(fileObj);
	}

	@RequestMapping(value = "/showPDF/assignment/{phase}/material/{file}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> showPDF(HttpServletRequest httpServletRequest, @PathVariable("phase") String phase,
			@PathVariable("file") String file) throws IOException {
		// access file under src/main/resources
		System.out.println();
		System.out.println("phase:" + phase);
		System.out.println("file:" + file);
		System.out.println("file path:" + "assignment/" + phase + "/material/" + file);
		System.out.println();
		ClassLoader classLoader = getClass().getClassLoader();
		File fileObj = new File(
				classLoader.getResource("assignment/" + phase + "/material/" + file + ".pdf").getFile());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentDispositionFormData("attachment", java.net.URLEncoder.encode(fileObj.getName(), "UTF-8"));
		httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(fileObj), httpHeaders, HttpStatus.OK);
	}
}
