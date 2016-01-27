package com.ss.academy.java.dao.message;

import java.util.List;

import com.ss.academy.java.model.message.Message;

public interface MessageDao {

	Message findById(Integer message_id);

	void saveMessage(Message message);

	List<Message> findAllMessages();

	List<Message> listAllSentMessages(Integer offset, Integer maxResults, String username);

	List<Message> listAllReceivedMessages(Integer offset, Integer maxResults, String username);

	Long countReceivedMessages(String username);
	
	Long countSentMessages(String username);
}
