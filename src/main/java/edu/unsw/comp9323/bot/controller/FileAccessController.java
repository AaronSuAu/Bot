package edu.unsw.comp9323.bot.controller;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
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
	@RequestMapping(value = "/download/resource/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
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
}
