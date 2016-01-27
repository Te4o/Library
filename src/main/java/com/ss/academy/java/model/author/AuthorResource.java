package com.ss.academy.java.model.author;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.ss.academy.java.model.book.Book;

public class AuthorResource extends ResourceSupport {

	private String name;
	private AuthorCountry country;
	private List<Book> books;

	public AuthorResource(String name, AuthorCountry country, List<Book> books) {
		super();
		this.name = name;
		this.country = country;
		this.books = books;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AuthorCountry getCountry() {
		return country;
	}

	public void setCountry(AuthorCountry country) {
		this.country = country;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
