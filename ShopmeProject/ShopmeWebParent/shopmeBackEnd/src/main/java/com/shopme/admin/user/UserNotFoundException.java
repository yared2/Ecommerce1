package com.shopme.admin.user;

public class UserNotFoundException extends Exception {

	private String message;

	UserNotFoundException(String message){
		super(message);
	}
}
