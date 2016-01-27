package com.ss.academy.java.dao.user;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ss.academy.java.dao.AbstractDao;
import com.ss.academy.java.model.user.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<String, User> implements UserDao {

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		Criteria criteria = createEntityCriteria();
		List<User> users = (List<User>) criteria.list();

		return users;
	}

	public void saveUser(User user) {
		save(user);
	}
	
	public void updateUser(User user) {
		update(user);
	}

	public User findById(String id) {
		return getByKey(id);
	}

	public User findByUsername(String username) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("username", username));

		return (User) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<User> findUsersByUserName(String userName) {
		String searchBy = "%" + userName + "%";
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("username"))
				.add(Restrictions.like("username", searchBy));

		List<User> users = (List<User>) criteria.list();

		return users;
	}

	// List portion of all users per page
	@SuppressWarnings("unchecked")
	@Transactional
	public List<User> listAllUsers(Integer offset, Integer maxResults) {
		List<User> users = getSession().createCriteria(User.class)
				.addOrder( Order.asc("username"))
				.setFirstResult(offset != null ? offset : 0)
				.setMaxResults(maxResults != null ? maxResults : 5).list();
		
		return users;
	}

	// Returns the number of all users
	public Long countAllUsers() {
		Long numberOfUsers = (Long) getSession()
				.createCriteria(User.class)
				.setProjection(Projections.rowCount()).uniqueResult();
		
		return numberOfUsers;
	}
}