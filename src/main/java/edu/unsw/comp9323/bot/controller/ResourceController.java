package edu.unsw.comp9323.bot.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.unsw.comp9323.bot.constant.Constant;
import edu.unsw.comp9323.bot.dao.ResourceDao;
import edu.unsw.comp9323.bot.model.Resource;
import edu.unsw.comp9323.bot.service.impl.ResourceServiceImpl;

@RestController
@RequestMapping("/resource/")
public class ResourceController {
	@Autowired
	ResourceServiceImpl resourceServiceImpl;
	@Autowired
	ResourceDao resourceDao;

	@RequestMapping(value = "/material/add", method = RequestMethod.POST)
	public String setMaterial(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("class_id") Long class_id, @RequestParam("author_zid") String author_zid) {
		Resource resource = new Resource();

		/*
		 * insert into resource table
		 */
		byte[] bytes;
		try {
			bytes = file.getBytes();
			String filePath = "src/main/resources/lecture/" + file.getOriginalFilename().replace(" ", "");
			Path path = Paths.get(filePath);
			Files.write(path, bytes);
			resource.setTitle(name);
			resource.setPath(filePath);
			resource.setTimestamp(new Timestamp(System.currentTimeMillis()));
			resource.setAuthor(author_zid);
			resource.setClass_id(class_id);
			System.out.println("inserting resource:" + resource.toString()); // debug
			resourceServiceImpl.uploadAssignResource(resource);
			System.out.println("new resource id: " + resource.getId().toString());
			return "success add new material";
		} catch (IOException e) {
			e.printStackTrace();
			return "fail add new material";
		}
	}

	@RequestMapping(value = "/material/update", method = RequestMethod.POST)
	public String updateMaterial(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("class_id") Long class_id, @RequestParam("author_zid") String author_zid) {
		Resource resource = new Resource();
		/*
		 * Delete 
		 */
		/*
		 * insert into resource table
		 */
		byte[] bytes;
		try {
			List<Resource> list = resourceDao.getResourceByClass(class_id);
			for (Resource toDeleteResource : list) {
				File toDeletefile = new File(toDeleteResource.getPath());
				toDeletefile.delete();
				resourceDao.deleteResource(toDeleteResource);
			}
			bytes = file.getBytes();
			String filePath = "src/main/resources/assignment/" + name + "/material/" + file.getOriginalFilename().replace(" ", "");
			Path path = Paths.get(filePath);
			Files.write(path, bytes);
			resource.setTitle(name);
			resource.setPath(filePath);
			resource.setTimestamp(new Timestamp(System.currentTimeMillis()));
			resource.setAuthor(author_zid);
			resource.setClass_id(class_id);
			System.out.println("inserting resource:" + resource.toString()); // debug
			resourceServiceImpl.uploadAssignResource(resource);
			System.out.println("new resource id: " + resource.getId().toString());
			return "success add new material";
		} catch (IOException e) {
			e.printStackTrace();
			return "fail add new material";
		}
	}
}
