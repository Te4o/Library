package com.ss.academy.java.util;

import java.util.List;

import com.ss.academy.java.model.message.Message;

public class UnreadMessagesCounter {
	
	/*
	 * A private Constructor prevents any other class from instantiating.
	 */
	private UnreadMessagesCounter() {
	}

	public static int count(List<Message> messages) {
		int counter = 0;
		for (Message message : messages) {
			if (message.getIsNew() == 1) {
				counter++;
			}
		}
		
		return counter;
	}
}
