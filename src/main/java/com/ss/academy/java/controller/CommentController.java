package com.ss.academy.java.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ss.academy.java.exception.ResourceNotFoundException;
import com.ss.academy.java.model.author.Author;
import com.ss.academy.java.model.book.Book;
import com.ss.academy.java.model.comment.Comment;
import com.ss.academy.java.model.user.User;
import com.ss.academy.java.service.author.AuthorService;
import com.ss.academy.java.service.book.BookService;
import com.ss.academy.java.service.comment.CommentService;
import com.ss.academy.java.service.user.UserService;
import com.ss.academy.java.util.CommonAttributesPopulator;

/**
 * Handles requests for the application comments page.
 */
@Controller
@RequestMapping(value = { "/authors/{author_id}/books/{book_id}" })
public class CommentController {

	@Autowired
	AuthorService authorService;

	@Autowired
	BookService bookService;

	@Autowired
	CommentService commentService;

	@Autowired
	UserService userService;

	// This method will show book's comments
	@RequestMapping(value = { "/comments" }, method = RequestMethod.GET)
	public String listAllComments(@PathVariable Long book_id, @PathVariable Long author_id, ModelMap model,
			@AuthenticationPrincipal UserDetails userDetails) {
		Author author = authorService.findById(author_id);

		if (author == null) {
			throw new ResourceNotFoundException();
		}

		Book book = bookService.findById(book_id);

		if (book == null) {
			throw new ResourceNotFoundException();
		}

		List<Comment> comments = book.getComments();

		User currentUser = userService.findByUsername(userDetails.getUsername());

		model.addAttribute("isEmpty", comments.isEmpty());
		model.addAttribute("comments", comments);
		model.addAttribute("author", author);
		model.addAttribute("book", book);

		CommonAttributesPopulator.populate(currentUser, model);

		return "comments/allComments";
	}

	// This method will provide the medium to add a new comment.
	@RequestMapping(value = { "/comments/new" }, method = RequestMethod.GET)
	public String addNewComment(@PathVariable Long author_id, @PathVariable Long book_id,
			@AuthenticationPrincipal UserDetails userDetails, ModelMap model) {
		Author author = authorService.findById(author_id);

		if (author == null) {
			throw new ResourceNotFoundException();
		}

		Book book = bookService.findById(book_id);

		if (book == null) {
			throw new ResourceNotFoundException();
		}

		User currentUser = userService.findByUsername(userDetails.getUsername());
		Comment comment = new Comment();

		model.addAttribute("comment", comment);
		model.addAttribute("book", book);

		CommonAttributesPopulator.populate(currentUser, model);

		return "comments/addNewComment";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving comment in the database.
	 */
	@RequestMapping(value = { "/comments/new" }, method = RequestMethod.POST)
	public String saveComment(@Valid Comment comment, BindingResult result, ModelMap model,
			@PathVariable Long author_id, @PathVariable Long book_id,
			@AuthenticationPrincipal UserDetails userDetails) {
		Author author = authorService.findById(author_id);

		if (author == null) {
			throw new ResourceNotFoundException();
		}

		Book book = bookService.findById(book_id);

		if (book == null) {
			throw new ResourceNotFoundException();
		}

		User currentUser = userService.findByUsername(userDetails.getUsername());

		if (result.hasErrors()) {
			model.addAttribute("book", book);
			CommonAttributesPopulator.populate(currentUser, model);
			return "comments/addNewComment";
		}

		currentUser.getComments().add(comment);
		book.getComments().add(comment);
		comment.setUser(currentUser);
		comment.setBook(book);

		commentService.saveComment(comment);

		return "redirect:/authors/{author_id}/books/{book_id}/comments";
	}

	// This method will delete a comment by it's ID value.
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = { "/comments/{comment_id}" }, method = RequestMethod.DELETE)
	public String deleteComment(@PathVariable Long book_id, @PathVariable Integer comment_id) {
		Comment comment = commentService.findById(comment_id);
		Book book = bookService.findById(book_id);

		book.getComments().remove(comment);

		commentService.deleteCommentById(comment_id);

		return "redirect:/authors/{author_id}/books/{book_id}/comments";
	}
}
