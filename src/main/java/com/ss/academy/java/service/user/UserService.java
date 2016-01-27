package com.ss.academy.java.service.user;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.ss.academy.java.model.user.User;

public interface UserService {

	List<User> findAllUsers();

	List<User> findUsersByUserName(String userName);

	void saveUser(User user);
	
	void updateUser(User candidateDbUser);
	
	void changeUserPassword(User user, String password);

	User findById(String id);

	User findByUsername(String username);

	@PreAuthorize("hasAuthority('ADMIN')")
	void updateUserStatus(User user);

	boolean isUsernameUnique(String username);
	
	List<User> listAllUsers(Integer offset, Integer maxResults);
    
	Long countAllUsers();

}