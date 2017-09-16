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
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/resource")
public class AccessFileController {

	@RequestMapping(value = "/downloadPDF/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public FileSystemResource download(HttpServletResponse response, @PathVariable("id") Long id) {

		// TODO get fail path from id
		// access file under src/main/resources
		String filePath = "";

		ClassLoader classLoader = getClass().getClassLoader();
		File fileObj = new File(classLoader.getResource(filePath).getFile());
		response.setHeader("Content-Disposition", "attachment; filename=" + fileObj.getName());
		return new FileSystemResource(fileObj);
	}

	@RequestMapping(value = "/showPDF/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> showPDF(HttpServletRequest httpServletRequest, @PathVariable("id") Long id)
			throws IOException {

		// TODO get fail path from id
		// access file under src/main/resources
		String filePath = "";

		ClassLoader classLoader = getClass().getClassLoader();
		File fileObj = new File(classLoader.getResource(filePath).getFile());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentDispositionFormData("attachment", java.net.URLEncoder.encode(fileObj.getName(), "UTF-8"));
		httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(fileObj), httpHeaders, HttpStatus.OK);
	}
	@RequestMapping(value = "/resource/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public FileSystemResource getReosource(HttpServletResponse response){
		File file = new File("src/bot.sql");
	    return new FileSystemResource(file);
	}
	

}
