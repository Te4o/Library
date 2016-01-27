package com.ss.academy.java.service.author;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.academy.java.dao.author.AuthorDao;
import com.ss.academy.java.model.author.Author;

@Service("authorService")
@Transactional
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorDao dao;

	public Author findById(Long id) {
		return dao.findById(id);
	}

	public void saveAuthor(Author author) {
		dao.saveAuthor(author);
	}

	public void updateAuthor(Author author) {
		Author entity = dao.findById(author.getId());

		if (entity != null) {
			entity.setName(author.getName());
			entity.setCountry(author.getCountry());

			if (author.getBooks() != null) {
				entity.setBooks(author.getBooks());
			}
		}
	}

	public void deleteAuthor(Author author) {
		dao.deleteAuthorById(author.getId());
	}

	public List<Author> findAllAuthors() {
		return dao.findAllAuthors();
	}

	public List<Author> findAuthorsByName(String authorName) {
		return dao.findAuthorsByName(authorName);
	}

	// List portion of all authors per page
	public List<Author> listAllAuthors(Integer offset, Integer maxResults) {
		return dao.listAllAuthors(offset, maxResults);
	}

	// Returns the number of all authors
	public Long countAllAuthors() {
		return dao.countAllAuthors();
	}

	public Author findAuthorByName(String authorName) {
		return dao.findAuthorByName(authorName);
	}
}
