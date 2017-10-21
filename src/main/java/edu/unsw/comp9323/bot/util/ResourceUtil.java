package edu.unsw.comp9323.bot.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import edu.unsw.comp9323.bot.constant.Constant;
import edu.unsw.comp9323.bot.model.Resource;

@Service
public class ResourceUtil {

	public String renderResourceReturnMsg(List<Resource> resources) {
		String returnMsg = "";

		String hostStringLocal = "http://127.0.0.1";
		String hostStringGCloud = "cmbot-b3f5e.appspot.com";
		List<List<BasicButton>> bOuterList= new ArrayList<List<BasicButton>>();
		for (Resource resource : resources) {
			String info = resource.toString().trim();
			String show_url_localhost = Constant.DOMAIN_NAME + "/file/download/resource/" + resource.getId();
			BasicButton bB = new BasicButton(info, show_url_localhost);
			List<BasicButton> bList = new ArrayList<>();
			bList.add(bB);
			bOuterList.add(bList);
			returnMsg = returnMsg + info + show_url_localhost;
		}
		Inline_Keyboard iKeyboard = new Inline_Keyboard(bOuterList);
		ButtonBuilder builder = new ButtonBuilder("Resource List: ", iKeyboard);
		return new Gson().toJson(builder);
	}
}
