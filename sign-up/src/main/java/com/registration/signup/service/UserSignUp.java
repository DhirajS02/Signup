package com.registration.signup.service;

import org.springframework.stereotype.Service;

import com.registration.signup.json.User;


public interface UserSignUp {
	public User registration(User user);
	public boolean isEmailPresent(String  email);

}
