package com.ss.academy.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ss.academy.java.model.book.Book;
import com.ss.academy.java.model.user.User;
import com.ss.academy.java.service.book.BookService;
import com.ss.academy.java.service.user.UserService;
import com.ss.academy.java.util.CommonAttributesPopulator;

/**
 * Handles requests for the application books page.
 */
@Controller
@RequestMapping(value = { "/books" })
public class BooksController {

	@Autowired
	BookService bookService;

	@Autowired
	UserService userService;

	// This method will show the list of all books.
	@RequestMapping(value = { "/" })
	public String showAllBooks(@AuthenticationPrincipal UserDetails user, ModelMap model, Integer offset,
			Integer maxResults) {
		User currentUser = userService.findByUsername(user.getUsername());
		List<Book> books = bookService.listAllBooks(offset, maxResults);
		Long numberOfBooks = bookService.countAllBooks();

		if (books.isEmpty()) {
			model.addAttribute("emptyListOfBooks", true);
		} else {
			model.addAttribute("books", books);
			model.addAttribute("numberOfBooks", numberOfBooks );
			model.addAttribute("offset", offset);
		}

		CommonAttributesPopulator.populate(currentUser, model);

		return "books/allBooks";
	}

	// This method provides the ability to search for books by their titles.
	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String searchBookByTitle(@RequestParam("bookTitle") String bookTitle, ModelMap model,
			@AuthenticationPrincipal UserDetails user) {
		User currentUser = userService.findByUsername(user.getUsername());
		List<Book> books = bookService.findBooksByTitle(bookTitle);

		if (books.isEmpty()) {
			model.addAttribute("noSuchBookFound", true);
		} else {
			model.addAttribute("books", books);
		}

		CommonAttributesPopulator.populate(currentUser, model);

		return "books/allBooks";
	}
}
