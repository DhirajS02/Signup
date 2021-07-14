package com.registration.signup.repo;

import org.springframework.stereotype.Repository;

import com.registration.signup.json.User;

public interface UserRepo {
	public User registration(User user);
}
