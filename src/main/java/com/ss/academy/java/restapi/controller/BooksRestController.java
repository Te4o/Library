package com.ss.academy.java.restapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ss.academy.java.model.author.Author;
import com.ss.academy.java.model.book.Book;
import com.ss.academy.java.model.book.BookResource;
import com.ss.academy.java.model.book.BookResourceAssembler;
import com.ss.academy.java.service.author.AuthorService;
import com.ss.academy.java.service.book.BookService;

/**
 * Handles requests for the RESTful API authors' books end-point. Each response
 * is in JSON format.
 */
@RestController
@ExposesResourceFor(Book.class)
@RequestMapping(value = "restapi/authors/{id}/books")
public class BooksRestController {

	@Autowired
	BookService bookService;

	@Autowired
	AuthorService authorService;

	/**
	 * Retrieve All Author's Books
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ResponseEntity<Resources<BookResource>> listAllBooks(@PathVariable Long id) {
		Author currentAuthor = authorService.findById(id);

		if (currentAuthor == null) {
			return new ResponseEntity<Resources<BookResource>>(HttpStatus.NOT_FOUND);
		}

		List<Book> books = currentAuthor.getBooks();

		BookResourceAssembler assembler = new BookResourceAssembler();
		List<BookResource> listOfBookResources = new ArrayList<BookResource>();

		for (Book book : books) {
			BookResource bookResource = assembler.toResource(book);
			bookResource.add(linkTo(AuthorsRestController.class)
				.slash(currentAuthor)
				.slash("/books")
				.slash(book)
				.withSelfRel());

			bookResource.add(linkTo(AuthorsRestController.class)
				.slash(currentAuthor)
				.slash("/books")
				.slash(book)
				.slash("/ratings")
				.withRel("ratings"));

			bookResource.add(linkTo(AuthorsRestController.class)
				.slash(currentAuthor)
				.slash("/books")
				.slash(book)
				.slash("/comments")
				.withRel("comments"));

			listOfBookResources.add(bookResource);
		}

		Resources<BookResource> bookResources = new Resources<BookResource>(listOfBookResources);

		bookResources.add(linkTo(methodOn(BooksRestController.class)
			.createBook(id, null))
			.withRel("newBook"));

		return new ResponseEntity<Resources<BookResource>>(bookResources, HttpStatus.OK);
	}

	/**
	 * Retrieve All Author's Books
	 */
	@RequestMapping(value = { "/{book_id}" }, method = RequestMethod.GET)
	public ResponseEntity<BookResource> listBookById(@PathVariable Long id, @PathVariable Long book_id) {
		Author currentAuthor = authorService.findById(id);

		if (currentAuthor == null) {
			return new ResponseEntity<BookResource>(HttpStatus.NOT_FOUND);
		}

		List<Book> authorBooks = currentAuthor.getBooks();
		Book book = null;

		for (Book authorBook : authorBooks) {
			if (authorBook.getId() == book_id) {
				book = authorBook;
			}
		}

		if (book == null) {
			return new ResponseEntity<BookResource>(HttpStatus.NOT_FOUND);
		}

		BookResourceAssembler assembler = new BookResourceAssembler();
		BookResource bookResource = assembler.toResource(book);

		bookResource.add(linkTo(AuthorsRestController.class)
			.slash(id.toString())
			.slash("/books")
			.slash(book)
			.withSelfRel());

		return new ResponseEntity<BookResource>(bookResource, HttpStatus.OK);
	}

	/**
	 * Create new Book for the given Author
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ResponseEntity<Void> createBook(@PathVariable Long id, @RequestBody Book book) {
		Author currentAuthor = authorService.findById(id);

		if (currentAuthor == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}

		List<Book> authorBooks = currentAuthor.getBooks();

		for (Book authorBook : authorBooks) {
			if (authorBook.getTitle().equals(book.getTitle())) {
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
		}

		book.setAuthor(currentAuthor);
		bookService.saveBook(book);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(linkTo(AuthorsRestController.class)
			.slash(id.toString())
			.slash("/books")
			.slash(book)
			.toUri());

		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	/**
	 * Update Author's Book
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/{book_id}", method = RequestMethod.PUT)
	public ResponseEntity<BookResource> updateBook(@PathVariable("id") long id, @PathVariable("book_id") long book_id,
			@RequestBody Book book) {
		Author currentAuthor = authorService.findById(id);

		if (currentAuthor == null) {
			return new ResponseEntity<BookResource>(HttpStatus.NOT_FOUND);
		}

		List<Book> authorBooks = currentAuthor.getBooks();

		if (authorBooks == null) {
			return new ResponseEntity<BookResource>(HttpStatus.NOT_FOUND);
		}
		
		if (book.getId() != book_id) {
			return new ResponseEntity<BookResource>(HttpStatus.BAD_REQUEST);
		}

		boolean found = false;

		for (Book authorBook : authorBooks) {
			if (authorBook.getId() == book_id) {
				bookService.updateBook(book);
				found = true;
				break;
			}
		}

		if (!found) {
			return new ResponseEntity<BookResource>(HttpStatus.NOT_FOUND);
		}

		BookResourceAssembler assembler = new BookResourceAssembler();
		BookResource bookResource = assembler.toResource(book);

		bookResource.add(linkTo(AuthorsRestController.class)
			.slash(currentAuthor)
			.slash("/books")
			.slash(book)
			.withSelfRel());

		bookResource.add(linkTo(AuthorsRestController.class)
			.slash(currentAuthor)
			.slash("/books")
			.slash(book)
			.slash("/ratings")
			.withRel("ratings"));

		bookResource.add(linkTo(AuthorsRestController.class)
			.slash(currentAuthor)
			.slash("/books")
			.slash(book)
			.slash("/comments")
			.withRel("comments"));

		return new ResponseEntity<BookResource>(bookResource, HttpStatus.OK);
	}

	/**
	 * Delete Author's Book
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/{book_id}", method = RequestMethod.DELETE)
	public ResponseEntity<BookResource> deleteAuthor(@PathVariable("id") long id,
			@PathVariable("book_id") long book_id) {
		Author currentAuthor = authorService.findById(id);

		if (currentAuthor == null) {
			return new ResponseEntity<BookResource>(HttpStatus.NOT_FOUND);
		}

		List<Book> authorBooks = currentAuthor.getBooks();
		boolean found = false;

		for (Book book : authorBooks) {
			if (book.getId() == book_id) {
				bookService.deleteBook(book);
				found = true;
				break;
			}
		}

		if (!found) {
			return new ResponseEntity<BookResource>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<BookResource>(HttpStatus.NO_CONTENT);
	}
}
