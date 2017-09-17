package edu.unsw.comp9323.bot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unsw.comp9323.bot.dao.ResourceDao;
import edu.unsw.comp9323.bot.model.Resource;
import edu.unsw.comp9323.bot.service.ResourceService;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;
import edu.unsw.comp9323.bot.util.ResourceUtil;

@Service
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	Resource resource;
	@Autowired
	ResourceDao resourceDao;
	@Autowired
	ResourceUtil resourceUtil;

	@Override
	public String getAllLectureResource(AIWebhookRequest input) {
		List<Resource> resources = resourceDao.getAllLectureResource();
		return resourceUtil.renderResourceReturnMsg(resources);
	}

	public List<Resource> getResourceByAssignment(Long assignId) {
		return resourceDao.getResourceByAssignment(assignId);
	}

	public List<Resource> getResourceByClass(Long classId) {
		return resourceDao.getResourceByClass(classId);
	}

	public Long uploadAssignResource(Resource resource) {
		return resourceDao.uploadAssignResource(resource);
	}

}
