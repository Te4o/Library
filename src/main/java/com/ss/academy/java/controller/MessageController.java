package com.ss.academy.java.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ss.academy.java.model.message.Message;
import com.ss.academy.java.model.user.User;
import com.ss.academy.java.service.message.MessageService;
import com.ss.academy.java.service.user.UserService;
import com.ss.academy.java.util.CommonAttributesPopulator;

/**
 * Handles requests for the application messaging service.
 */
@Controller
@RequestMapping(value = { "/messages/{user_id}" })
public class MessageController {

	private final byte UNREAD_MESSAGE = 1;
	private final byte READ_MESSAGE = 0;

	@Autowired
	MessageService messageService;

	@Autowired
	UserService userService;

	// This method will list all received messages of the Authenticated user
	@RequestMapping(value = { "/inbox" }, method = RequestMethod.GET)
	public String listAllReceivedMessages(ModelMap model, @AuthenticationPrincipal UserDetails userDetails,
			Integer offset, Integer maxResults, String username) {
		User currentUser = userService.findByUsername(userDetails.getUsername());
		List<Message> messages = messageService.listAllReceivedMessages(offset, maxResults, userDetails.getUsername());
		Long numberOfReceivedMessages = messageService.countReceivedMessages(userDetails.getUsername());

		model.addAttribute("isEmpty", messages.isEmpty());
		model.addAttribute("messages", messages);
		model.addAttribute("numberOfReceivedMessages", numberOfReceivedMessages);
		model.addAttribute("offset", offset);

		CommonAttributesPopulator.populate(currentUser, model);

		return "messages/inbox";
	}

	// This method will list all sent messages of the Authenticated user
	@RequestMapping(value = { "/outbox" }, method = RequestMethod.GET)
	public String listAllSentMessages(ModelMap model, @AuthenticationPrincipal UserDetails userDetails, Integer offset,
			Integer maxResults, String username) {
		User currentUser = userService.findByUsername(userDetails.getUsername());
		List<Message> messages = messageService.listAllSentMessages(offset, maxResults, userDetails.getUsername());
		Long numberOfSentMessages = messageService.countSentMessages(userDetails.getUsername());

		model.addAttribute("isEmpty", messages.isEmpty());
		model.addAttribute("messages", messages);
		model.addAttribute("numberOfSentMessages", numberOfSentMessages);
		model.addAttribute("offset", offset);

		CommonAttributesPopulator.populate(currentUser, model);

		return "messages/outbox";
	}

	// This method will create new message
	@RequestMapping(value = { "/new/{receiver_id}" }, method = RequestMethod.GET)
	public String sendNewMessage(ModelMap model, @PathVariable String receiver_id,
			@AuthenticationPrincipal UserDetails userDetails) {
		User receiver = userService.findById(receiver_id);

		if (receiver == null) {
			return "redirect:/messages/{user_id}/inbox";
		}

		User currentUser = userService.findByUsername(userDetails.getUsername());
		Message message = new Message();
		model.addAttribute("message", message);
		model.addAttribute("receiver", receiver);

		CommonAttributesPopulator.populate(currentUser, model);

		return "messages/new";
	}

	// This method will save and send the newly created message.
	@RequestMapping(value = { "/new/{receiver_id}" }, method = RequestMethod.POST)
	public String saveMessage(@Valid Message message, BindingResult result, ModelMap model,
			@AuthenticationPrincipal UserDetails userDetails, @PathVariable String receiver_id) {
		User sender = userService.findByUsername(userDetails.getUsername());
		User receiver = userService.findById(receiver_id);
		
		if (result.hasErrors()) {
			model.addAttribute("receiver", receiver);
			CommonAttributesPopulator.populate(sender, model);
			return "messages/new";
		}

		if (receiver.getUsername().equals(sender.getUsername())) {
			return "redirect:/messages/{user_id}/inbox";
		}

		sender.getSentMessage().add(message);
		receiver.getReceivedMessage().add(message);
		message.setReceiver(receiver);
		message.setSender(sender);
		messageService.saveMessage(message);

		return "redirect:/messages/{user_id}/outbox";
	}

	// This method will create new message and make relation with previous message
	@RequestMapping(value = { "/{message_id}/reply" }, method = RequestMethod.GET)
	public String replyToMessage(ModelMap model, @PathVariable Integer message_id,
			@AuthenticationPrincipal UserDetails userDetails) {
		User currentUser = userService.findByUsername(userDetails.getUsername());
		Message parent = messageService.findById(message_id);

		if (parent == null) {
			return "redirect:/messages/{user_id}/inbox";
		}

		if (!parent.getReceiver().getUsername().equals(userDetails.getUsername())
				&& !parent.getSender().getUsername().equals(userDetails.getUsername())) {
			return "redirect:/messages/{user_id}/inbox";
		}

		if (parent.getIsNew() == UNREAD_MESSAGE) {
			parent.setIsNew(READ_MESSAGE);
			messageService.updateMessageStatus(parent);
		}

		List<Message> previousMessages = messageService.generateMessageThread(parent);

		Message message = new Message();

		model.addAttribute("message", message);
		model.addAttribute("parents", previousMessages);
		model.addAttribute("receiver", parent.getSender().getUsername());

		CommonAttributesPopulator.populate(currentUser, model);

		return "messages/reply";
	}

	/*
	 * This method will save and reply to the previous message in the thread.
	 */
	@RequestMapping(value = { "/{message_id}/reply" }, method = RequestMethod.POST)
	public String replyToMessage(@Valid Message message, BindingResult result, ModelMap model,
			@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer message_id) {
		User sender = userService.findByUsername(userDetails.getUsername());
		Message parent = messageService.findById(message_id);
		
		if (result.hasErrors()) {
			List<Message> previousMessages = messageService.generateMessageThread(parent);
			model.addAttribute("parents", previousMessages);
			model.addAttribute("receiver", parent.getSender().getUsername());
			CommonAttributesPopulator.populate(sender, model);
			return "messages/reply";
		}
		
		User receiver = userService.findByUsername(parent.getSender().getUsername());
		
		sender.getSentMessage().add(message);
		receiver.getReceivedMessage().add(message);
		message.setReceiver(receiver);
		message.setSender(sender);
		message.setIn_reply_to(parent.getMessage_id());
		message.setHeader("Re: " + parent.getHeader());
		messageService.saveMessage(message);

		return "redirect:/messages/{user_id}/outbox";
	}

	// This method will display all messages in the message thread.
	@RequestMapping(value = { "/{message_id}/display" }, method = RequestMethod.GET)
	public String displayMessage(ModelMap model, @PathVariable Integer message_id,
			@AuthenticationPrincipal UserDetails userDetails) {
		User currentUser = userService.findByUsername(userDetails.getUsername());
		Message parent = messageService.findById(message_id);

		if (parent == null) {
			return "redirect:/messages/{user_id}/outbox";
		}

		if (!parent.getReceiver().getUsername().equals(userDetails.getUsername())
				&& !parent.getSender().getUsername().equals(userDetails.getUsername())) {
			return "redirect:/messages/{user_id}/outbox";
		}

		List<Message> previousMessages = messageService.generateMessageThread(parent);
		model.addAttribute("parents", previousMessages);

		CommonAttributesPopulator.populate(currentUser, model);

		return "messages/display";
	}
}
