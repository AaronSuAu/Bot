package edu.unsw.comp9323.bot.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceContext;
import ai.api.AIServiceContextBuilder;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

@Component
public class AIService {
	private AIDataService aiDataService;

	public AIService() {
		AIConfiguration aiConfig = new AIConfiguration("cbba5c33841e4e6687acd87adcf9b72c");
		aiDataService = new AIDataService(aiConfig);
	}

	/**
	 * Perform request to AI data service
	 * 
	 * @param aiRequest
	 *            Request object. Cannot be <code>null</code>.
	 * @param serviceContext
	 *            Service context. If <code>null</code> then default context will be
	 *            used.
	 * @return Response object
	 * @throws AIServiceException
	 *             Thrown on server access error
	 */
	public final AIResponse request(AIRequest aiRequest, AIServiceContext serviceContext) throws AIServiceException {
		return aiDataService.request(aiRequest, serviceContext);
	}

	/**
	 * Perform request to AI data service
	 * 
	 * @param query
	 *            Request plain text string. Cannot be <code>null</code>.
	 * @param serviceContext
	 *            Service context. If <code>null</code> then default context will be
	 *            used.
	 * @return Response object
	 * @throws AIServiceException
	 *             Thrown on server access error
	 */
	public final AIResponse request(String query, AIServiceContext serviceContext) throws AIServiceException {
		return request(new AIRequest(query), serviceContext);
	}

	/**
	 * Perform request to AI data service
	 * 
	 * @param aiRequest
	 *            Request object. Cannot be <code>null</code>.
	 * @param session
	 *            Session object. If <code>null</code> then default context will be
	 *            used.
	 * @return Response object
	 * @throws AIServiceException
	 *             Thrown on server access error
	 */
	public final AIResponse request(AIRequest aiRequest, HttpSession session) throws AIServiceException {
		return request(aiRequest,
				(session != null) ? AIServiceContextBuilder.buildFromSessionId(session.getId()) : null);
	}

	/**
	 * Perform request to AI data service
	 * 
	 * @param query
	 *            Request plain text string. Cannot be <code>null</code>.
	 * @param session
	 *            Session object. If <code>null</code> then default context will be
	 *            used.
	 * @return Response object
	 * @throws AIServiceException
	 *             Thrown on server access error
	 */
	public final AIResponse request(String query, HttpSession session) throws AIServiceException {
		return request(new AIRequest(query),
				(session != null) ? AIServiceContextBuilder.buildFromSessionId(session.getId()) : null);
	}

	/**
	 * Perform request to AI data service
	 * 
	 * @param aiRequest
	 *            Request object. Cannot be <code>null</code>.
	 * @param sessionId
	 *            Session string id. If <code>null</code> then default context will
	 *            be used.
	 * @return Response object
	 * @throws AIServiceException
	 *             Thrown on server access error
	 */
	public final AIResponse request(AIRequest aiRequest, String sessionId) throws AIServiceException {
		return request(aiRequest, (sessionId != null) ? AIServiceContextBuilder.buildFromSessionId(sessionId) : null);
	}

	/**
	 * Perform request to AI data service
	 * 
	 * @param query
	 *            Request plain text string. Cannot be <code>null</code>.
	 * @param sessionId
	 *            Session string id. If <code>null</code> then default context will
	 *            be used.
	 * @return Response object
	 * @throws AIServiceException
	 *             Thrown on server access error
	 */
	public final AIResponse request(String query, String sessionId) throws AIServiceException {
		return request(new AIRequest(query),
				(sessionId != null) ? AIServiceContextBuilder.buildFromSessionId(sessionId) : null);
	}
}
