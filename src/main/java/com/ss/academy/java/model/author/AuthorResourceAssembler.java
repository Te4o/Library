package com.ss.academy.java.model.author;

import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;

public class AuthorResourceAssembler extends IdentifiableResourceAssemblerSupport<Author, AuthorResource> {

	public AuthorResourceAssembler() {
		super(Author.class, AuthorResource.class);
	}

	public AuthorResource toResource(Author author) {
		AuthorResource resource = new AuthorResource(author.getName(), author.getCountry(), author.getBooks());

		return resource;
	}
}
