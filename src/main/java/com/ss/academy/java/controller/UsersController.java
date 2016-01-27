package com.ss.academy.java.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ss.academy.java.model.user.User;
import com.ss.academy.java.service.book.BookService;
import com.ss.academy.java.service.message.MessageService;
import com.ss.academy.java.service.user.UserService;
import com.ss.academy.java.util.CommonAttributesPopulator;

/**
 * Handles requests for the application users page plus user login/registration.
 */
@Controller
@RequestMapping(value = "/")
public class UsersController {

	@Autowired
	BookService bookService;

	@Autowired
	UserService userService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	MessageService messageService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * This method will list all existing users.
	 */
	@RequestMapping(value = { "/users" }, method = RequestMethod.GET)
	public String listAllUsers(@AuthenticationPrincipal UserDetails userDetails, ModelMap model, Integer offset,
			Integer maxResults) {
		User currentUser = userService.findByUsername(userDetails.getUsername());
		
		List<User> allUsers = userService.listAllUsers(offset, maxResults);
		Long numberOfUsers = userService.countAllUsers();
		
		if (allUsers.isEmpty()) {
			model.addAttribute("emptyListOfUsers", true);
		} else {
		model.addAttribute("allUsers", allUsers);
		model.addAttribute("numberOfUsers", numberOfUsers);
		model.addAttribute("offset", offset);
		}

		CommonAttributesPopulator.populate(currentUser, model);

		return "users/allUsers";
	}

	/*
	 * This method provides the ability to search for user by its username.
	 */
	@RequestMapping(value = { "/users/search" }, method = RequestMethod.GET)
	public String searchUserByUsername(@RequestParam("username") String username, ModelMap model,
			@AuthenticationPrincipal UserDetails userDetails) {
		User currentUser = userService.findByUsername(userDetails.getUsername());
		List<User> users = userService.findUsersByUserName(username);

		model.addAttribute("noSuchUserFound", users.isEmpty());
		model.addAttribute("allUsers", users);

		CommonAttributesPopulator.populate(currentUser, model);

		return "users/allUsers";
	}

	/*
	 * This method provides the ability the admin to be able to update the
	 * users' status.
	 */
	@RequestMapping(value = { "/users/{user_id}" }, method = RequestMethod.PUT)
	public String updateUserStatus(ModelMap model, @PathVariable String user_id) {
		User user = userService.findById(user_id);
		userService.updateUserStatus(user);

		return "redirect:/users/";
	}

	/*
	 * This method will provide the medium to log in existing user into the
	 * system.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "users/login";
	}

	/*
	 * This method will provide the medium to register a new user.
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerForm(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);

		return "users/register";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input and check
	 * whether the username is unique.
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute @Valid User user, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			return "users/register";
		}

		if (userService.isUsernameUnique(user.getUsername())) {
			FieldError loginError = new FieldError("username", "username", messageSource
					.getMessage("non.unique.username", new String[] { user.getUsername() }, Locale.getDefault()));
			result.addError(loginError);
			return "users/register";
		}

		userService.saveUser(user);

		model.addAttribute("newUser", user.getUsername());

		return "users/registrationSuccess";
	}

	/*
	 * This method will provide the medium to update current user profile
	 * details.
	 */
	@RequestMapping(value = "users/{user_id}/editProfile", method = RequestMethod.GET)
	public String showMyProfile(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String user_id,
			ModelMap model) {
		User currentUser = userService.findByUsername(userDetails.getUsername());

		model.addAttribute("user", currentUser);
		model.addAttribute("username", currentUser.getUsername());

		CommonAttributesPopulator.populate(currentUser, model);

		return "users/profile/editProfile";
	}

	/*
	 * This method will provide access to the currently selected user's profile.
	 */
	@RequestMapping(value = "users/{user_id}/showProfile", method = RequestMethod.GET)
	public String showUserProfile(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String user_id,
			ModelMap model) {
		User currentUser = userService.findById(user_id);

		model.addAttribute("user", currentUser);
		model.addAttribute("username", currentUser.getUsername());

		CommonAttributesPopulator.populate(currentUser, model);

		return "users/profile/showProfile";
	}

	/*
	 * This method will be called on form submission, handling PUT request for
	 * editing user profile in database. It also validates the user input and
	 * check whether the entered password match with the one stored in the DB.
	 */
	@RequestMapping(value = "users/{user_id}/editProfile", method = RequestMethod.POST)
	public String editMyProfile(@ModelAttribute @Valid User user, BindingResult result,
			@AuthenticationPrincipal UserDetails userDetails, ModelMap model) {
		User currentUser = userService.findByUsername(userDetails.getUsername());

		model.addAttribute("username", currentUser.getUsername());

		CommonAttributesPopulator.populate(currentUser, model);

		if (result.hasFieldErrors("firstName") || result.hasFieldErrors("lastName") || result.hasFieldErrors("email")) {
			return "users/profile/editProfile";
		}

		userService.updateUser(user);

		return "users/profile/editProfileSuccess";
	}

	/*
	 * This method will handle user's password change request
	 */
	@RequestMapping(value = "users/{user_id}/changePassword", method = RequestMethod.GET)
	public String changePasswordForm(@ModelAttribute @Valid User user, BindingResult result,
			@AuthenticationPrincipal UserDetails userDetails, @PathVariable String user_id, ModelMap model) {
		User currentUser = userService.findById(user_id);
		currentUser.setPassword("");

		model.addAttribute("user", currentUser);
		model.addAttribute("username", currentUser.getUsername());

		CommonAttributesPopulator.populate(currentUser, model);

		return "users/userPassword/changePassword";
	}

	/*
	 * This method will do the validation of the passwords and will update the
	 * user's password.
	 */
	@RequestMapping(value = "users/{user_id}/changePassword", method = RequestMethod.POST)
	public String changePassword(@ModelAttribute @Valid User user, BindingResult result,
			@AuthenticationPrincipal UserDetails userDetails, @PathVariable String user_id, ModelMap model) {
		User currentUser = userService.findById(user_id);

		model.addAttribute("username", currentUser.getUsername());

		CommonAttributesPopulator.populate(currentUser, model);

		if (result.hasFieldErrors("password") || result.hasFieldErrors("newPassword")
				|| result.hasFieldErrors("newPassword2")) {
			return "users/userPassword/changePassword";
		}

		if (!passwordEncoder.matches(user.getPassword(), currentUser.getPassword())) {
			FieldError passwordDoNotMatch = new FieldError("password", "password", messageSource.getMessage(
					"non.matching.password", new String[] { currentUser.getUsername() }, Locale.getDefault()));
			result.addError(passwordDoNotMatch);

			return "users/userPassword/changePassword";
		}

		if (!user.getNewPassword().equals(user.getNewPassword2())) {
			FieldError passwordDoNotMatch = new FieldError("newPassword", "newPassword", messageSource.getMessage(
					"non.matching.passwords", new String[] { currentUser.getUsername() }, Locale.getDefault()));
			FieldError passwordDoNotMatch2 = new FieldError("newPassword2", "newPassword2", messageSource.getMessage(
					"non.matching.passwords", new String[] { currentUser.getUsername() }, Locale.getDefault()));

			result.addError(passwordDoNotMatch);
			result.addError(passwordDoNotMatch2);

			return "users/userPassword/changePassword";
		}

		if (user.getPassword().equals(user.getNewPassword())) {
			FieldError passwordDoMatch = new FieldError("newPassword", "newPassword", messageSource.getMessage(
					"matching.existing.password", new String[] { currentUser.getUsername() }, Locale.getDefault()));
			FieldError passwordDoMatch2 = new FieldError("newPassword2", "newPassword2", messageSource.getMessage(
					"matching.existing.password", new String[] { currentUser.getUsername() }, Locale.getDefault()));

			result.addError(passwordDoMatch);
			result.addError(passwordDoMatch2);

			return "users/userPassword/changePassword";
		}

		userService.changeUserPassword(currentUser, user.getNewPassword());

		return "users/userPassword/changePasswordSuccess";
	}
}
