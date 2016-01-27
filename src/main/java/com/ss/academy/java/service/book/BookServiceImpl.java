package com.ss.academy.java.service.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.academy.java.dao.book.BookDao;
import com.ss.academy.java.model.book.Book;
import com.ss.academy.java.model.book.BookStatus;

@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao dao;

	public Book findById(Long id) {
		return dao.findById(id);
	}

	public void saveBook(Book book) {
		dao.saveBook(book);
	}

	public void updateBook(Book book) {
		Book entity = dao.findById(book.getId());

		if (entity != null) {
			entity.setTitle(book.getTitle());
			entity.setImage(book.getImage());
			entity.setPages(book.getPages());
			entity.setBookDescription(book.getBookDescription());
		}
	}

	public List<Book> findAllBooks() {
		return dao.findAllBooks();
	}

	public void deleteBook(Book book) {
		dao.deleteBookById(book.getId());
	}

	public List<Book> findBooksByTitle(String bookTitle) {
		return dao.findBooksByTitle(bookTitle);
	}
	
	// List portion of all author's books per page
	public List<Book> listAllBooks(Integer offset, Integer maxResults, Long author_id) {
		return dao.listAllBooks(offset, maxResults, author_id);
	}

	// Returns the number of all author's books
	public Long countAllBooks(Long author_id) {
		return dao.countAllBooks(author_id);
	}

	// List portion of all books per page
	public List<Book> listAllBooks(Integer offset, Integer maxResults) {
		return dao.listAllBooks(offset, maxResults);
	}
	
	// Returns the number of all books
	public Long countAllBooks() {
		return dao.countAllBooks();
	}	
	
	public void changeBookStatus(Book book) {
		Book entity = dao.findById(book.getId());

		if (entity != null && entity.getStatus().equals(BookStatus.Available)) {
			entity.setStatus(BookStatus.Loaned);
		} else if (entity != null && entity.getStatus().equals(BookStatus.Loaned)){
			entity.setStatus(BookStatus.Available);
		}
	}
}