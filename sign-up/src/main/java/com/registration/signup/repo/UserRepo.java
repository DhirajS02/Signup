package com.registration.signup.repo;

import java.time.LocalDate;

import org.springframework.stereotype.Repository;

import com.registration.signup.json.User;

public interface UserRepo {
	public User registration(User user,LocalDate date);

	public int isCustomerIdPresent(int customerId);

	public int isEmailPresent(String email);

	public int isUserNamePresent(String userName);
}
