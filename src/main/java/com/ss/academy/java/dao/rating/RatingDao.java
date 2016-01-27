package com.ss.academy.java.dao.rating;

import java.util.List;

import com.ss.academy.java.model.rating.Rating;

public interface RatingDao {

	Rating findById(Long id);

	void saveRating(Rating rating);

	List<Rating> findAllRatings();
	
	List<Rating> findAllRatingsByBook(Long bookId);
	
	List<Rating> findAllRatingsByUser(Long userId);
}
