package com.ss.academy.java.util;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.ss.academy.java.model.message.Message;
import com.ss.academy.java.model.user.User;

public class CommonAttributesPopulator {
	
	/*
	 * A private Constructor prevents any other class from instantiating.
	 */
	private CommonAttributesPopulator() {
		
	}
	
	public static void populate(User currentUser, ModelMap modelMap) {
		List<Message> messages = currentUser.getReceivedMessage();
		int unreadMessages = UnreadMessagesCounter.count(messages);

		modelMap.addAttribute("unreadMessages", unreadMessages);
		modelMap.addAttribute("currentUserID", currentUser.getId());
	}
}
