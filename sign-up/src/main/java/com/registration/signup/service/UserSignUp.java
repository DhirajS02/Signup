package com.registration.signup.service;

import java.util.List;

import com.registration.signup.json.FieldIntegrity;
import com.registration.signup.json.StatesJson;
import com.registration.signup.json.User;
import com.registration.signup.json.UserResponseJson;

public interface UserSignUp {
	public UserResponseJson registration(User user);

	public FieldIntegrity isEmailPresent(String email);

	public FieldIntegrity passwordValidity(String password);

	public StatesJson getStates(String countryId);

	public FieldIntegrity phoneNumberValidity(String phoneNumber);

	public FieldIntegrity userNameValidity(String userName);

	public FieldIntegrity firstNameValidity(String firstName);

	public FieldIntegrity lastNameValidity(String lastName);

	public List<String> suggestUserNames(String userName);

}
