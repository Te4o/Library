package com.ss.academy.java.model.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.Identifiable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ss.academy.java.model.book.BookHistory;
import com.ss.academy.java.model.comment.Comment;
import com.ss.academy.java.model.message.Message;
import com.ss.academy.java.model.rating.Rating;

@Entity
@Table(name = "users")
public class User implements Identifiable<String> {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "user_id", unique = true)
	private String id;

	@Size(min = 3, max = 20)
	@Column(name = "username", unique = true)
	@NotNull
	private String username;

	@Size(min = 4, max = 60)
	@Column(name = "password")
	@NotNull
	private String password;

	@Size(min = 2, max = 20)
	@Column(name = "first_name")
	@NotNull
	private String firstName;

	@Size(min = 2, max = 20)
	@Column(name = "last_name")
	@NotNull
	private String lastName;

	@Size(min = 3, max = 50)
	@Column(name = "email")
	@NotNull
	private String email;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	@OneToMany(mappedBy = "user")
	@JsonManagedReference(value = "user-ratings")
	private List<Rating> ratings;

	@OneToMany(mappedBy = "sender")
	@JsonManagedReference(value = "sent-messages")
	private List<Message> sentMessage;

	@OneToMany(mappedBy = "receiver")
	@JsonManagedReference(value = "received-messages")
	private List<Message> receivedMessage;

	@OneToMany(mappedBy = "user")
	@JsonManagedReference(value = "user-comments")
	private List<Comment> comments;

	@OneToMany(mappedBy = "user")
	@JsonManagedReference(value = "user-book-history")
	private List<BookHistory> booksHistory;
	
	@Size(min = 4, max = 60)
	@Transient
	@JsonIgnore
	private String newPassword;
	
	@Size(min = 4, max = 60)
	@Transient
	@JsonIgnore
	private String newPassword2;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public List<Message> getSentMessage() {
		return sentMessage;
	}

	public void setSentMessage(List<Message> sentMessage) {
		this.sentMessage = sentMessage;
	}

	public List<Message> getReceivedMessage() {
		return receivedMessage;
	}

	public void setReceivedMessage(List<Message> receivedMessage) {
		this.receivedMessage = receivedMessage;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<BookHistory> getBooksHistory() {
		return booksHistory;
	}

	public void setBooksHistory(List<BookHistory> booksHistory) {
		this.booksHistory = booksHistory;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPassword2() {
		return newPassword2;
	}

	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}
}