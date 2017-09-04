package com.comp9323.chatbot.springbootmybatis.resource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comp9323.chatbot.springbootmybatis.dao.UserDao;
import com.comp9323.chatbot.springbootmybatis.model.User;
import com.comp9323.chatbot.springbootmybatis.service.AIService;
import com.comp9323.chatbot.springbootmybatis.service.impl.UserServiceImpl;
import com.google.gson.JsonElement;

import ai.api.AIServiceContext;
import ai.api.AIServiceException;
import ai.api.model.AIResponse;

@RestController
@RequestMapping("/rest/")
public class UserResource {
	@Autowired
	UserServiceImpl userServiceImpl;
	
	HttpSession session = null;
	AIServiceContext serviceContext = null;
	@Autowired
	AIService aiService;
//	public UserResource(UserDao userMapper) {
//		this.userMapper = userMapper;
//	}
	
	@RequestMapping(value = "/all", method=RequestMethod.GET)
	public List<User> getAll(){
		return userServiceImpl.findAll();
	}
	
	@RequestMapping(value = "/ai", method=RequestMethod.GET)
	public String getAIResponse(@RequestParam("query") String query){
		//HttpSession session = null;
		try {
			AIResponse ai = aiService.request(query, serviceContext);
			//return ai.getResult().getFulfillment().getSpeech();
			HashMap<String, JsonElement> result = ai.getResult().getParameters();
			if(result.get("assignment-title")!=null){
				return ai.getResult().getFulfillment().getSpeech()+result.get("assignment-title").toString();
			}
			return ai.getResult().getFulfillment().getSpeech();
		} catch (AIServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
