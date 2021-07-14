package com.registration.signup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registration.signup.json.User;
import com.registration.signup.repo.UserRepo;
import com.registration.signup.service.UserSignUp;

@Service
public class UserSignUpImpl implements UserSignUp {
	
	@Autowired
	UserRepo userRepo;

	@Override
	public User registration(User user) {
		return userRepo.registration(user);		
	}

	
}
