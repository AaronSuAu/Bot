package edu.unsw.comp9323.bot.service;

import java.util.List;

import edu.unsw.comp9323.bot.model.Resource;

public interface ResourceService {
	List<Resource> getResourceByAssignment(Long assignId);

	List<Resource> getResourceByClass(Long classId);

	Long uploadAssignResource(Resource resource);
}
