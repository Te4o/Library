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
import com.ss.academy.java.model.author.AuthorResource;
import com.ss.academy.java.model.author.AuthorResourceAssembler;
import com.ss.academy.java.service.author.AuthorService;

/**
 * Handles requests for the RESTful API authors end-point. Produces JSON.
 */
@RestController
@ExposesResourceFor(Author.class)
@RequestMapping({ "/restapi/authors" })
public class AuthorsRestController {

	@Autowired
	AuthorService authorService;

	/**
	 * Retrieve All Authors
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ResponseEntity<Resources<AuthorResource>> listAllAuthors() {
		List<Author> authors = authorService.findAllAuthors();

		AuthorResourceAssembler assembler = new AuthorResourceAssembler();
		List<AuthorResource> authorsResources = new ArrayList<AuthorResource>();

		for (Author author : authors) {
			AuthorResource authorResource = assembler.toResource(author);

			authorResource.add(linkTo(AuthorsRestController.class).slash(author).withSelfRel());
			authorResource.add(linkTo(AuthorsRestController.class).slash(author).slash("/books").withRel("books"));

			authorsResources.add(authorResource);
		}

		Resources<AuthorResource> authorResources = new Resources<AuthorResource>(authorsResources);

		return new ResponseEntity<Resources<AuthorResource>>(authorResources, HttpStatus.OK);

	}

	/**
	 * Retrieve Author by ID
	 */
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<AuthorResource> getAuthorById(@PathVariable("id") long id) {
		Author author = authorService.findById(id);

		if (author == null) {
			return new ResponseEntity<AuthorResource>(HttpStatus.NOT_FOUND);
		}

		AuthorResourceAssembler assembler = new AuthorResourceAssembler();
		AuthorResource authorResource = assembler.toResource(author);
		authorResource.add(linkTo(AuthorsRestController.class).slash(author).withSelfRel());
		authorResource.add(linkTo(methodOn(AuthorsRestController.class).listAllAuthors()).withRel("authors"));
		authorResource.add(linkTo(methodOn(AuthorsRestController.class).createAuthor(null)).withRel("newAuthor"));
		authorResource.add(linkTo(AuthorsRestController.class).slash(author).slash("/books").withRel("books"));

		return new ResponseEntity<AuthorResource>(authorResource, HttpStatus.OK);
	}

	/**
	 * Create Author
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ResponseEntity<Void> createAuthor(@RequestBody Author author) {
		Author dbAuthor = authorService.findAuthorByName(author.getName());

		if (dbAuthor != null) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		authorService.saveAuthor(author);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(linkTo(AuthorsRestController.class).slash(author).toUri());

		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	/**
	 * Update Author
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<AuthorResource> updateAuthor(@PathVariable("id") long id, @RequestBody Author author) {
		Author urlAuthor = authorService.findById(id);

		if (urlAuthor == null) {
			return new ResponseEntity<AuthorResource>(HttpStatus.NOT_FOUND);
		}

		Author requestAuthor = authorService.findById(author.getId());

		if (requestAuthor == null) {
			return new ResponseEntity<AuthorResource>(HttpStatus.NOT_FOUND);
		}

		if (urlAuthor.getId() != requestAuthor.getId()) {
			return new ResponseEntity<AuthorResource>(HttpStatus.BAD_REQUEST);
		}

		authorService.updateAuthor(author);

		AuthorResourceAssembler assembler = new AuthorResourceAssembler();
		AuthorResource authorResource = assembler.toResource(author);
		authorResource.add(linkTo(AuthorsRestController.class).slash(author).withSelfRel());
		authorResource.add(linkTo(methodOn(AuthorsRestController.class).listAllAuthors()).withRel("authors"));
		authorResource.add(linkTo(AuthorsRestController.class).slash(author).slash("/books").withRel("books"));

		return new ResponseEntity<AuthorResource>(authorResource, HttpStatus.OK);
	}

	/**
	 * Delete Author
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<AuthorResource> deleteAuthor(@PathVariable("id") long id) {
		Author currentAuthor = authorService.findById(id);

		if (currentAuthor == null) {
			return new ResponseEntity<AuthorResource>(HttpStatus.NOT_FOUND);
		}

		authorService.deleteAuthor(currentAuthor);

		return new ResponseEntity<AuthorResource>(HttpStatus.NO_CONTENT);
	}
}
