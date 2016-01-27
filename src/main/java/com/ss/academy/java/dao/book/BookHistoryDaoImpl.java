package com.ss.academy.java.dao.book;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ss.academy.java.dao.AbstractDao;
import com.ss.academy.java.model.book.BookHistory;


@Repository("bookHistoryDao")
public class BookHistoryDaoImpl extends AbstractDao<Long, BookHistory> implements BookHistoryDao {

	public BookHistory findById(Long id) {
		return getByKey(id);
	}

	public void saveBookHistory(BookHistory bookHistory) {
		save(bookHistory);
	}
	
	@SuppressWarnings("unchecked")
	public List<BookHistory> findAllBooksHistory() {
		Criteria criteria = createEntityCriteria();
		List<BookHistory> booksHistory = (List<BookHistory>) criteria.list();		
		return booksHistory;
	}
	
	// List portion of all books history per page for the authenticated user
	@SuppressWarnings("unchecked")
	public List<BookHistory> findAllBooksHistory(Integer offset, Integer maxResults, String username) {
		List<BookHistory> booksHistory = getSession()
				.createCriteria(BookHistory.class, "bookHistory")
				.createAlias("bookHistory.user", "bu") // inner join by default				
				.add(Restrictions.eq("bu.username", username))	
				.addOrder( Order.desc("returnDate"))
				.setFirstResult(offset != null ? offset : 0)
				.setMaxResults(maxResults != null ? maxResults : 5)
				.list();			
		return booksHistory;
	}	

	// Returns the number of all books history
	public Long countAllBooksHistory(String username) {
		Long numberOfBooksHistory = (Long)getSession()
				.createCriteria(BookHistory.class, "bookHistory")
				.createAlias("bookHistory.user", "bu") // inner join by default				
				.add(Restrictions.eq("bu.username", username))
				.setProjection(Projections.rowCount())
				.uniqueResult();		
		return numberOfBooksHistory;
	}
	
	// List portion of all returned/not returned books per page
	@SuppressWarnings("unchecked")
	public List<BookHistory> findAllBooksHistory(Integer offset, Integer maxResults, byte isReturned) {
		List<BookHistory> booksHistory = getSession()
				.createCriteria(BookHistory.class)
				.addOrder( Order.desc("returnDate"))
				.add(Restrictions.eq("isReturned", isReturned))
				.setFirstResult(offset != null ? offset : 0)
				.setMaxResults(maxResults != null ? maxResults : 5)
				.list();		
		return booksHistory;
	}
	
	// Returns the number of all returned/not returned Books
	public Long countAllBooksHistory(byte isReturned) {
		Long numberOfBooksHistory = (Long)getSession()
				.createCriteria(BookHistory.class)
				.add(Restrictions.eq("isReturned", isReturned))				
				.setProjection(Projections.rowCount())
				.uniqueResult();		
		return numberOfBooksHistory;
	}
}
