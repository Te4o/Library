package com.ss.academy.java.model.book;

import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;

public class BookResourceAssembler extends IdentifiableResourceAssemblerSupport<Book, BookResource> {

	public BookResourceAssembler() {
		super(Book.class, BookResource.class);
	}

	public BookResource toResource(Book book) {
		BookResource resource = new BookResource(book.getTitle(), book.getStatus(), book.getRatings(),
				book.getComments());

		return resource;
	}
}
