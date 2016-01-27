package com.ss.academy.java.service.message;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.academy.java.dao.message.MessageDao;
import com.ss.academy.java.model.message.Message;

@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {

	private final byte NOT_REPLIED = 0;
	
	@Autowired
	private MessageDao dao;

	public Message findById(Integer message_id) {
		return dao.findById(message_id);
	}

	public void saveMessage(Message message) {
		dao.saveMessage(message);

	}

	public List<Message> findAllMessages() {
		return dao.findAllMessages();
	}

	// Update the message status from unread to read
	public void updateMessageStatus(Message message) {

		Message entity = dao.findById(message.getMessage_id());

		if (entity != null) {
			entity.setIsNew(message.getIsNew());
		}
	}

	// List portion of all sent messages of the authenticated user per page
	public List<Message> listAllSentMessages(Integer offset, Integer maxResults, String username) {
		return dao.listAllSentMessages(offset, maxResults, username);
	}

	// Returns the number of all sent messages of the authenticated user
	public Long countSentMessages(String username) {
		return dao.countSentMessages(username);
	}

	// List portion of all received messages of the authenticated user per page
	public List<Message> listAllReceivedMessages(Integer offset, Integer maxResults, String username) {
		return dao.listAllReceivedMessages(offset, maxResults, username);
	}

	// Returns the number of all received messages of the authenticated user
	public Long countReceivedMessages(String username) {
		return dao.countReceivedMessages(username);
	}

	// Generate the message thread when reply to an existing message
	public List<Message> generateMessageThread(Message parent) {
		List<Message> previousMessages = new ArrayList<Message>();
		previousMessages.add(parent);

		while (parent.getIn_reply_to() != NOT_REPLIED) {
			parent = dao.findById(parent.getIn_reply_to());
			previousMessages.add(parent);
		}

		return previousMessages;
	}
	
	
}
