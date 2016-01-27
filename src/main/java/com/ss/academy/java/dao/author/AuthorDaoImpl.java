package com.ss.academy.java.dao.author;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ss.academy.java.dao.AbstractDao;
import com.ss.academy.java.model.author.Author;

@Repository("authorDao")
public class AuthorDaoImpl extends AbstractDao<Long, Author> implements AuthorDao {

	public Author findById(Long id) {
		return getByKey(id);
	}

	public void saveAuthor(Author author) {
		save(author);
	}

	public void deleteAuthorById(Long id) {
		Query query = getSession().createSQLQuery("delete from authors where author_id = :id");
		query.setLong("id", id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Author> findAllAuthors() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("name")); // Order
																				// ascending
																				// by
																				// name
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); // To
																		// avoid
																		// duplicates.
		List<Author> authors = (List<Author>) criteria.list();

		return authors;
	}

	@SuppressWarnings("unchecked")
	public List<Author> findAuthorsByName(String authorName) {
		String searchBy = "%" + authorName + "%";
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("name")).add(Restrictions.like("name", searchBy));

		List<Author> authors = (List<Author>) criteria.list();

		return authors;
	}
	
	// List portion of all authors per page
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Author> listAllAuthors(Integer offset, Integer maxResults) {
		List<Author> authors = getSession()
				.createCriteria(Author.class)
				.addOrder(Order.asc("name"))
				.setFirstResult(offset != null ? offset : 0)
				.setMaxResults(maxResults != null ? maxResults : 5)
				.list();
		
		return authors;
				
	}
	
	// Returns the number of all authors
	public Long countAllAuthors() {
		Long numberOfAuthors = (Long)getSession()
				.createCriteria(Author.class)
				.setProjection(Projections.rowCount())
				.uniqueResult();
		
		return numberOfAuthors;
	}

	public Author findAuthorByName(String authorName) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("name", authorName));

		return (Author) crit.uniqueResult();
	}
}