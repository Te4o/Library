package com.ss.academy.java.service.book;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.ss.academy.java.model.book.Book;

public interface BookService {

	Book findById(Long id);

	List<Book> findAllBooks();
	
	List<Book> findBooksByTitle(String bookTitle);

	@PreAuthorize("hasAuthority('ADMIN')")
	void saveBook(Book book);

	@PreAuthorize("hasAuthority('ADMIN')")
	void updateBook(Book book);

	@PreAuthorize("hasAuthority('ADMIN')")
	void deleteBook(Book book);
	
    void changeBookStatus(Book book);
	
	List<Book> listAllBooks(Integer offset, Integer maxResults, Long author_id);
 
    List<Book> listAllBooks(Integer offset, Integer maxResults);
    
    Long countAllBooks();
    
    Long countAllBooks(Long author_id);
}