package com.ss.academy.java.model.book;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ss.academy.java.model.user.User;

@Entity
@Table(name = "books_history")
public class BookHistory {

	@Id
	@Column(name = "book_history_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "book_id")
	@JsonBackReference(value = "book-history")
	private Book book;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference(value = "user-book-history")
	private User user;

	@Type(type = "timestamp")
	@Column(name = "get_date")
	@NotNull
	private Date getDate = new Date();

	@Type(type = "timestamp")
	@Column(name = "return_date")
	@NotNull
	private Date returnDate;

	@Column(name = "is_returned")
	@NotNull
	private byte isReturned = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGetDate() {
		return getDate;
	}

	public void setGetDate(Date getDate) {
		this.getDate = getDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public byte getIsReturned() {
		return isReturned;
	}

	public void setIsReturned(byte isReturned) {
		this.isReturned = isReturned;
	}
}
