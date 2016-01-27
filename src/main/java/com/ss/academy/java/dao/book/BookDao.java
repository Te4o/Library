package com.ss.academy.java.dao.book;

import java.util.List;

import com.ss.academy.java.model.book.Book;

public interface BookDao {
	
	Book findById(Long id);

	void saveBook(Book book);

	void deleteBookById(Long id);

	List<Book> findAllBooks();
	
	List<Book> findBooksByTitle(String bookTitle);
	
	List<Book> listAllBooks(Integer offset, Integer maxResults, Long id);
      
    List<Book> listAllBooks(Integer offset, Integer maxResults);
    
    Long countAllBooks();
    
    Long countAllBooks(Long author_id);
}
