package edu.unsw.comp9323.bot.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.unsw.comp9323.bot.model.Resource;
import edu.unsw.comp9323.bot.service.impl.ResourceServiceImpl;

@RestController
@RequestMapping("/resource/")
public class ResourceController {
	@Autowired
	ResourceServiceImpl resourceServiceImpl;
	
	@RequestMapping(value = "/resource/class/{id}", method = RequestMethod.GET)
	public List<Resource> getResourceByClass(@Param("id") Long id){
		return resourceServiceImpl.getResourceByClass(id);
		
	}
	
	@RequestMapping(value = "/resource/assign/{id}", method = RequestMethod.GET)
	public List<Resource> getResourceByAssignment(@Param("id") Long id){
		return resourceServiceImpl.getResourceByAssignment(id);
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String receiveResource(@RequestParam("file") MultipartFile file, 
			@RequestParam("name") String name,
			@RequestParam("due_date_string") String due_date_string,
			@RequestParam("zid") String zid){
		byte[] bytes;
		try {
			bytes = file.getBytes();
			String filePath = "src/main/resources/" + file.getOriginalFilename();
			Path path = Paths.get(filePath);
	        Files.write(path, bytes);
	        Resource resource = new Resource();
	        System.out.println(name+zid+due_date_string);
	        resource.setPath(filePath);
	        resource.setTitle(file.getOriginalFilename());
	        resource.setTimestamp(new Timestamp(System.currentTimeMillis()));
	        resourceServiceImpl.uploadAssignResource(resource);
	        return String.valueOf(resource.getId());
		} catch (IOException e) {
			e.printStackTrace();
			return "fail";
		}	
	}

}
