package com.ss.academy.java.service.author;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.ss.academy.java.model.author.Author;

public interface AuthorService {

	Author findById(Long id);
	
	Author findAuthorByName(String authorName);

	List<Author> findAllAuthors();

	List<Author> findAuthorsByName(String authorName);

	@PreAuthorize("hasAuthority('ADMIN')")
	void saveAuthor(Author author);

	@PreAuthorize("hasAuthority('ADMIN')")
	void updateAuthor(Author author);

	@PreAuthorize("hasAuthority('ADMIN')")
	void deleteAuthor(Author author);
	
	List<Author> listAllAuthors(Integer offset, Integer maxResults);	
	
	Long countAllAuthors();
}
