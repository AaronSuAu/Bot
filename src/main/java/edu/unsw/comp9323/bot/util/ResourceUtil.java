package edu.unsw.comp9323.bot.util;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.unsw.comp9323.bot.constant.Constant;
import edu.unsw.comp9323.bot.model.Resource;

@Service
public class ResourceUtil {

	public String renderResourceReturnMsg(List<Resource> resources) {
		String returnMsg = "";

		String hostStringLocal = "localhost:8080";
		String hostStringGCloud = "cmbot-b3f5e.appspot.com";

		for (Resource resource : resources) {
			String info = " -" + resource.toString();
			String show_url_localhost = Constant.DOMAIN_NAME + "/file/download/resource/" + resource.getId();
			returnMsg = returnMsg + info + show_url_localhost;
		}
		return returnMsg;
	}
}
