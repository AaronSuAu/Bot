package edu.unsw.comp9323.bot.controller;

import java.io.File;
import java.io.IOException;

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
import org.springframework.web.bind.annotation.RestController;

import edu.unsw.comp9323.bot.dao.ResourceDao;
import edu.unsw.comp9323.bot.model.Resource;
import edu.unsw.comp9323.bot.service.ResourceService;

@RestController
@RequestMapping("/file")
public class FileAccessController {
	@Autowired
	ResourceService ResourceService;
	@Autowired
	Resource resource;
	@Autowired
	ResourceDao resourceDao;

	/**
	 * download file by given resource id
	 * 
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/download/resource/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public FileSystemResource downloadFile(HttpServletResponse response, @PathVariable("id") Long id) {
		System.out.println("downloadFile");
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
	@RequestMapping(value = "/showPDF/resource/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> showPDF(HttpServletRequest httpServletRequest, @PathVariable("id") Long id)
			throws IOException {
		System.out.println("showPDF");

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

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentDispositionFormData("attachment", java.net.URLEncoder.encode(fileObj.getName(), "UTF-8"));
		httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(fileObj), httpHeaders, HttpStatus.OK);
	}
}
