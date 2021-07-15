package com.registration.signup.service.impl;

import java.time.LocalDate;

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
	String[] dobArray = user.getDob().split("/");
	LocalDate date=LocalDate.of(Integer.parseInt(dobArray[2]), Integer.parseInt(dobArray[1]), Integer.parseInt(dobArray[0]));
		return userRepo.registration(user,date);		
	}

	
}
