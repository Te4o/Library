package com.ss.academy.java.service.rating;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.academy.java.dao.rating.RatingDao;
import com.ss.academy.java.model.rating.Rating;

@Service("ratingService")
@Transactional
public class RatingServiceImpl implements RatingService {

	@Autowired
	RatingDao dao;
	
	public List<Rating> findAllRatings() {
		return dao.findAllRatings();
	}

	public void saveRating(Rating rating) {
		dao.saveRating(rating);
		
	}

	public List<Rating> findAllRatingsByBook(Long bookId) {
		return findAllRatingsByBook(bookId);
	}

	public List<Rating> findAllRatingsByUser(Long userId) {
		return findAllRatingsByUser(userId);
	}
}
