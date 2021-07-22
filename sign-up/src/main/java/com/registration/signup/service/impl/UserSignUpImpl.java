package com.registration.signup.service.impl;

import java.time.LocalDate;
import java.util.Random;

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
		LocalDate date = LocalDate.of(Integer.parseInt(dobArray[2]), Integer.parseInt(dobArray[1]),
				Integer.parseInt(dobArray[0]));
		user.setCustomerId(Integer.valueOf(createCustomerId()).toString());
		return userRepo.registration(user, date);
	}

	private int createCustomerId() {
		int customerId = 0;
		do {
			customerId = getRandomNumber(9);
		} while (userRepo.isCustomerIdPresent(customerId) != 0);
		return customerId;
	}

	private int getRandomNumber(int lengthOfRandomNumbers) {
		Random randomNumberGenerator = new Random();
		String bound="9";
		String additionalNumber="1";
		for(int i=0;i<lengthOfRandomNumbers-1;i++)
		{
			bound=bound+"0";
			additionalNumber=additionalNumber+"0";
			
		}
		int randomNumbers=randomNumberGenerator.nextInt(Integer.parseInt(bound)) + Integer.parseInt(additionalNumber);		
		return randomNumbers;

	}

	@Override
	public boolean isEmailPresent(String email) {
		// TODO Auto-generated method stub
		return false;
	}

}
