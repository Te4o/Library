package com.ss.academy.java.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ss.academy.java.exception.ResourceNotFoundException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException() {
		return "commons/404";
	}
}
