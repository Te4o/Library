package com.ss.academy.java.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ss.academy.java.exception.ResourceNotFoundException;
import com.ss.academy.java.model.book.Book;
import com.ss.academy.java.model.book.BookHistory;
import com.ss.academy.java.model.book.BookStatus;
import com.ss.academy.java.model.user.User;
import com.ss.academy.java.service.book.BookHistoryService;
import com.ss.academy.java.service.book.BookService;
import com.ss.academy.java.service.user.UserService;
import com.ss.academy.java.util.CommonAttributesPopulator;

/**
 * Handles requests for the application books history page.
 */
@Controller
@RequestMapping(value = "/books")
public class BookHistoryController {

	private final byte NOT_RETURNED = 0;
	private final byte RETURNED = 1;
	private final int LOAN_PERIOD = 90;

	@Autowired
	UserService userService;

	@Autowired
	BookHistoryService bookHistoryService;

	@Autowired
	BookService bookService;

	// This method will list user's Books History
	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = { "/{user_id}" }, method = RequestMethod.GET)
	public String listBooksHistory(ModelMap model, @AuthenticationPrincipal UserDetails userDetails, Integer offset,
			Integer maxResults) {
		User currentUser = userService.findByUsername(userDetails.getUsername());

		List<BookHistory> booksHistory = bookHistoryService.findAllBooksHistory(offset, maxResults,
				currentUser.getUsername());
		Long numberOfBooksHistory = bookHistoryService.countAllBooksHistory(currentUser.getUsername());

		if (booksHistory.isEmpty()) {
			model.addAttribute("isEmpty", true);
		} else {
			model.addAttribute("isEmpty", false);
			model.addAttribute("booksHistory", booksHistory);
			model.addAttribute("numberOfBooksHistory", numberOfBooksHistory);
			model.addAttribute("offset", offset);
		}

		CommonAttributesPopulator.populate(currentUser, model);

		return "users/booksHistory";
	}

	// This method will add the loaned book in user's book history
	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = { "/{book_id}/{user_id}/addToHistory" }, method = RequestMethod.GET)
	public String addNewBookHistory(@PathVariable Long book_id, @AuthenticationPrincipal UserDetails userDetails) {
		Book book = bookService.findById(book_id);

		if (book == null) {
			throw new ResourceNotFoundException();
		}

		BookHistory bookHistory = new BookHistory();

		User currentUser = userService.findByUsername(userDetails.getUsername());

		if (book.getStatus().equals(BookStatus.Available)) {
			currentUser.getBooksHistory().add(bookHistory);
			book.getBooksHistory().add(bookHistory);
			bookService.changeBookStatus(book);
			bookHistory.setBook(book);
			bookHistory.setUser(currentUser);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(bookHistory.getGetDate());
			calendar.add(Calendar.DAY_OF_MONTH, LOAN_PERIOD);
			bookHistory.setReturnDate(calendar.getTime());

			bookHistoryService.saveBookHistory(bookHistory);
		}

		return "redirect:/books/{user_id}";
	}

	/*
	 * This method will change book history status to returned and also change
	 * the book status from loaned to available and book
	 */
	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = "/{user_id}/{history_id}/return", method = RequestMethod.GET)
	public String returnBook(@PathVariable Long history_id, @AuthenticationPrincipal UserDetails userDetails) {
		BookHistory bookHistory = bookHistoryService.findById(history_id);

		if (bookHistory == null) {
			throw new ResourceNotFoundException();
		}

		if (bookHistory.getIsReturned() == NOT_RETURNED
				&& bookHistory.getBook().getStatus().equals(BookStatus.Loaned)) {
			Date currentDate = new Date(System.currentTimeMillis());
			bookHistory.setReturnDate(currentDate);
			bookHistory.setIsReturned(RETURNED);
			bookHistoryService.updateBookHistory(bookHistory);
			bookService.changeBookStatus(bookHistory.getBook());
		}

		return "redirect:/books/{user_id}";
	}

	// This method will list all loaned books
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/loaned", method = RequestMethod.GET)
	public String showAllLoanedBooks(@AuthenticationPrincipal UserDetails userDetails, ModelMap model, Integer offset,
			Integer maxResults, BookHistory bookHistories) {
		User currentUser = userService.findByUsername(userDetails.getUsername());
		Date currentDate = new Date(System.currentTimeMillis());
		List<BookHistory> loanedBooks = bookHistoryService.findAllBooksHistory(offset, maxResults, NOT_RETURNED);
		Long numberOfLoanedBooks = bookHistoryService.countAllBooksHistory(NOT_RETURNED);

		if (loanedBooks.isEmpty()) {
			model.addAttribute("isEmpty", true);
		} else {
			model.addAttribute("isEmpty", false);
			model.addAttribute("loanedBooks", loanedBooks);
			model.addAttribute("numberOfLoanedBooks", numberOfLoanedBooks);
			model.addAttribute("offset", offset);
			model.addAttribute("currentDate", currentDate);
		}

		CommonAttributesPopulator.populate(currentUser, model);

		return "users/loanedBooks";
	}
}
