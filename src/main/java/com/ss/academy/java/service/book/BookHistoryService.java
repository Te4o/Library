package com.ss.academy.java.service.book;

import java.util.List;

import com.ss.academy.java.model.book.BookHistory;
import com.ss.academy.java.model.user.User;

public interface BookHistoryService {

	BookHistory findById(Long id);

	void saveBookHistory(BookHistory bookHistory);
	
	void updateBookHistory(BookHistory bookHistory);
	
	List<BookHistory> findAllBooksHistory();
	
	List<BookHistory> findAllBooksHistory(Integer offset, Integer maxResults, String username);
	
	List<BookHistory> findAllBooksHistory(Integer offset, Integer maxResults, byte isReturned);
	
	Long countAllBooksHistory(String username);
	
	Long countAllBooksHistory(byte isReturned);
	
	User getCurrentBookLoaner(Long book_id);
}