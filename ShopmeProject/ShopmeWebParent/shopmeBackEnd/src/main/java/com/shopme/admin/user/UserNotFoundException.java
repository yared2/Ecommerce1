package com.shopme.admin.user;

import com.shopme.common.entity.User;

public class UserNotFoundException extends Exception {
	
	private String message;

	UserNotFoundException(String message){
		super(message);
	}
}
