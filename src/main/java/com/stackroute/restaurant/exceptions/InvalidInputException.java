package com.stackroute.restaurant.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;


public class InvalidInputException extends Exception {
	
	public  InvalidInputException(String message) {
		super(message);
		
	}
}