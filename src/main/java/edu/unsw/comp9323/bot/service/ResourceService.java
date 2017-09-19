package edu.unsw.comp9323.bot.service;

import java.util.List;

import edu.unsw.comp9323.bot.model.Resource;
import edu.unsw.comp9323.bot.service.impl.AIWebhookServiceImpl.AIWebhookRequest;

public interface ResourceService {
	

	String getAllLectureResource(AIWebhookRequest input);

	List<Resource> getResourceByAssignment(Long assignId);

	List<Resource> getResourceByClass(Long classId);

	Long uploadAssignResource(Resource resource);
	
	String getClassMaterialUrlByWeek(AIWebhookRequest input);
	String addClassMaterialUrlByWeek(AIWebhookRequest input);
	String deleteLectureResourceByWeek(AIWebhookRequest input);
	String updateClassMaterialUrlByWeek(AIWebhookRequest input);
}
