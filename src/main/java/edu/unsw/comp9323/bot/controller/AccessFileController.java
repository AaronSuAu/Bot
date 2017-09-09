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
public class AccessFileController {
	// path field can not including suffix
	@RequestMapping(value = "/download/{path}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public FileSystemResource download(HttpServletResponse response, @PathVariable("path") String path) {
		// access file under src/main/resources
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("test.pdf").getFile());
		response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
		return new FileSystemResource(file);
	}

	@RequestMapping(value = "/showPDF/{path}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> showPDF(HttpServletRequest httpServletRequest, @PathVariable("path") String path)
			throws IOException {
		// access file under src/main/resources
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("test.pdf").getFile());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentDispositionFormData("attachment", java.net.URLEncoder.encode(file.getName(), "UTF-8"));
		httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.OK);
	}

}
