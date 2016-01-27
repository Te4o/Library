package com.ss.academy.java.model.rating;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.Identifiable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ss.academy.java.model.book.Book;
import com.ss.academy.java.model.user.User;

@Entity
@Table(name = "book_ratings")
public class Rating implements Identifiable<Long> {

	@Id
	@Column(name = "rating_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Min(1)
	@Max(5)
	@Column(name = "rating")
	@NotNull
	private Byte ratingValue;

	@ManyToOne()
	@JoinColumn(name = "user_id")
	@JsonBackReference(value = "user-ratings")
	private User user;
	
	@ManyToOne()
	@JoinColumn(name = "book_id")
	@JsonBackReference(value = "book-ratings")
	private Book book;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Byte getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(Byte ratingValue) {
		this.ratingValue = ratingValue;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
}