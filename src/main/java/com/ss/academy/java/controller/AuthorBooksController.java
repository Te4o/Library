package com.ss.academy.java.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ss.academy.java.exception.ResourceNotFoundException;
import com.ss.academy.java.model.author.Author;
import com.ss.academy.java.model.book.Book;
import com.ss.academy.java.model.rating.Rating;
import com.ss.academy.java.model.user.User;
import com.ss.academy.java.service.author.AuthorService;
import com.ss.academy.java.service.book.BookHistoryService;
import com.ss.academy.java.service.book.BookService;
import com.ss.academy.java.service.rating.RatingService;
import com.ss.academy.java.service.user.UserService;
import com.ss.academy.java.util.CommonAttributesPopulator;
import com.ss.academy.java.util.RatingCalculator;

import sun.misc.BASE64Encoder;

/**
 * Handles requests for the application authors' books page.
 */
@SuppressWarnings("restriction")
@Controller
@RequestMapping(value = "/authors/{author_id}/books")
public class AuthorBooksController {

	@Autowired
	BookService bookService;

	@Autowired
	AuthorService authorService;

	@Autowired
	BookHistoryService bookHistoryService;

	@Autowired
	RatingService ratingService;

	@Autowired
	UserService userService;

	// This method will list all existing books
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String listAllBooks(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long author_id,
			ModelMap model, Integer offset, Integer maxResults) {
		Author author = authorService.findById(author_id);

		if (author == null) {
			throw new ResourceNotFoundException();
		}

		User currentUser = userService.findByUsername(userDetails.getUsername());

		List<Book> books = bookService.listAllBooks(offset, maxResults, author_id);
		Long numberOfBooks = bookService.countAllBooks(author_id);

		if (books.isEmpty()) {
			model.addAttribute("emptyListOfAuthorBooks", true);
		}

		model.addAttribute("books", books);
		model.addAttribute("numberOfBooks", numberOfBooks);
		model.addAttribute("offset", offset);
		model.addAttribute("author", author);

		CommonAttributesPopulator.populate(currentUser, model);

		return "books/allAuthorBooks";
	}

	/*
	 * This method will display book's details and will check whether it has
	 * been rated so far by the current user.
	 */
	@RequestMapping(value = { "/{book_id}/preview" }, method = RequestMethod.GET)
	public String previewBook(@PathVariable Long author_id, @PathVariable Long book_id, ModelMap model,
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

		book.setAverageRating(RatingCalculator.calculate(book.getRatings()));
		List<Rating> bookRatings = book.getRatings();

		for (Rating rating : bookRatings) {
			if (rating.getUser().getUsername().equals(userDetails.getUsername())) {
				book.setIsRated(true);
				break;
			}
		}

		try {
			byte[] bytes = book.getImage();
			if (bytes.length == 0) {
				model.addAttribute("emptyListOfAuthorBooks", true);
			}
			BASE64Encoder base64Encoder = new BASE64Encoder();
			StringBuilder imageString = new StringBuilder();
			imageString.append("data:image/png;base64,");
			imageString.append(base64Encoder.encode(bytes));

			String image = imageString.toString();
			model.addAttribute("image", image);
		} catch (NullPointerException e) {
			model.addAttribute("emptyListOfAuthorBooks", true);
		}

		User currentBookLoaner = bookHistoryService.getCurrentBookLoaner(book_id);

		if (currentBookLoaner != null) {
			model.addAttribute("currentBookLoaner", currentBookLoaner);
			model.addAttribute("isBookLoaned", true);
		}

		model.addAttribute("book", book);

		CommonAttributesPopulator.populate(currentUser, model);

		return "books/bookPreview";
	}

	// This method provides the ability to search for books by their titles.
	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String searchBookByName(@PathVariable Long author_id, @RequestParam("bookTitle") String bookTitle,
			ModelMap model, @AuthenticationPrincipal UserDetails userDetails) {
		Author author = authorService.findById(author_id);

		if (author == null) {
			throw new ResourceNotFoundException();
		}

		User currentUser = userService.findByUsername(userDetails.getUsername());

		List<Book> books = bookService.findBooksByTitle(bookTitle);
		List<Book> authorBooks = new ArrayList<Book>();

		for (Book book : books) {
			if (book.getAuthor().getId() == author_id) {
				authorBooks.add(book);
			}
		}

		if (authorBooks.isEmpty()) {
			model.addAttribute("noSuchBookFound", true);
		}

		model.addAttribute("author", author);
		model.addAttribute("books", authorBooks);

		CommonAttributesPopulator.populate(currentUser, model);

		return "books/allAuthorBooks";
	}

	// This method will provide the medium to add a new book.
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String addNewBook(ModelMap model, @AuthenticationPrincipal UserDetails userDetails) {
		User currentUser = userService.findByUsername(userDetails.getUsername());
		Book book = new Book();

		model.addAttribute("book", book);
		model.addAttribute("edit", false);

		CommonAttributesPopulator.populate(currentUser, model);

		return "books/addNewBook";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving book in database. It also validates the user input.
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String saveBook(@Valid Book book, BindingResult result, @RequestParam CommonsMultipartFile[] fileUpload,
			@PathVariable Long author_id, ModelMap model, @AuthenticationPrincipal UserDetails userDetails) {
		User currentUser = userService.findByUsername(userDetails.getUsername());

		if (result.hasErrors()) {
			CommonAttributesPopulator.populate(currentUser, model);
			return "books/addNewBook";
		}

		try {
			if (fileUpload != null && fileUpload.length > 0) {
				for (CommonsMultipartFile aFile : fileUpload) {
					if (aFile.toString().startsWith("FF D8 FF")) {
						// check if format of file is JPG
					} else if (aFile.toString().startsWith("47 49 46 38 37 61")
							|| aFile.toString().startsWith("47 49 46 38 39 61")) {
						// check if format of file is GIF
					} else if (aFile.toString().startsWith("89 50 4E 47 0D 0A 1A 0A")) {
						// check if format of file is PNG
					}
					Author author = authorService.findById(author_id);
					author.getBooks().add(book);
					book.setAuthor(author);
					book.setImage(aFile.getBytes());

					bookService.saveBook(book);
				}
			}
		} catch (Exception e) {
			model.addAttribute("largeSizeOfImage", true);
		}

		return "redirect:/authors/{author_id}/books/";
	}

	// This method will provide the medium to update an existing book.
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = { "/{book_id}" }, method = RequestMethod.GET)
	public String editBook(@PathVariable Long book_id, ModelMap model,
			@AuthenticationPrincipal UserDetails userDetails) {
		User currentUser = userService.findByUsername(userDetails.getUsername());
		Book book = bookService.findById(book_id);

		if (book == null) {
			return "redirect:/authors/{author_id}/books/";
		}

		Author author = book.getAuthor();

		model.addAttribute("book", book);
		model.addAttribute("author", author);
		model.addAttribute("edit", true);

		CommonAttributesPopulator.populate(currentUser, model);

		return "books/addNewBook";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * updating book in database. It also validates the user input.
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = { "/{book_id}" }, method = RequestMethod.POST)
	public String updateBook(@Valid Book formBook, BindingResult result, ModelMap model,
			@RequestParam CommonsMultipartFile[] fileUpload, @PathVariable Long book_id, @PathVariable Long author_id,
			@AuthenticationPrincipal UserDetails userDetails) {
		User currentUser = userService.findByUsername(userDetails.getUsername());
		Author author = new Author();
		Book dbBook = new Book();

		if (result.hasErrors()) {
			CommonAttributesPopulator.populate(currentUser, model);
			return "books/addNewBook";
		}
		try {
			if (fileUpload != null && fileUpload.length > 0) {
				for (CommonsMultipartFile aFile : fileUpload) {
					if (aFile.toString().startsWith("FF D8 FF")) {
						// check if format of file is JPG
					} else if (aFile.toString().startsWith("47 49 46 38 37 61")
							|| aFile.toString().startsWith("47 49 46 38 39 61")) {
						// check if format of file is GIF
					} else if (aFile.toString().startsWith("89 50 4E 47 0D 0A 1A 0A")) {
						// check if format of file is PNG
					}
					author = authorService.findById(author_id);
					dbBook = bookService.findById(book_id);
					byte[] currentImage = dbBook.getImage();
					if (aFile.getSize() != 0) {
						formBook.setImage(aFile.getBytes());
					} else {
						formBook.setImage(currentImage);
					}

					dbBook = formBook;
					author.getBooks().add(dbBook);

					bookService.updateBook(dbBook);
				}
			}
		} catch (Exception e) {
			model.addAttribute("largeSizeOfImage", true);
		}
		return "redirect:/authors/{author_id}/books/{book_id}/preview";
	}

	// This method will delete a book by it's ID value.
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = { "/{book_id}" }, method = RequestMethod.DELETE)
	public String deleteBook(@PathVariable Long author_id, @PathVariable Long book_id) {
		Book book = bookService.findById(book_id);
		Author author = authorService.findById(author_id);

		author.getBooks().remove(book);
		bookService.deleteBook(book);

		return "redirect:/authors/{author_id}/books/";
	}
}