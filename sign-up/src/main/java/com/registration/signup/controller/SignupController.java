package com.registration.signup.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.registration.signup.json.FieldIntegrity;
import com.registration.signup.json.StatesJson;
import com.registration.signup.json.User;
import com.registration.signup.json.UserResponseJson;
import com.registration.signup.service.UserSignUp;

@RestController
public class SignupController {

	@Autowired
	UserSignUp userSignUpImpl;

	@PostMapping(value = "/users/register")
	public ResponseEntity<UserResponseJson> registration(@Valid @RequestBody User user) {
		UserResponseJson userResponseJson = new UserResponseJson();
		userResponseJson = userSignUpImpl.registration(user);
		if (userResponseJson.getErrorCode().isEmpty()) {
			return new ResponseEntity<UserResponseJson>(userResponseJson, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<UserResponseJson>(userResponseJson, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "emailExistence/{email}")
	public ResponseEntity<FieldIntegrity> registration(@PathVariable String email) {
		return new ResponseEntity<FieldIntegrity>(userSignUpImpl.isEmailPresent(email), HttpStatus.OK);
	}

	@GetMapping(value = "/country/{countryId}/states")
	public ResponseEntity<StatesJson> getCountries(@PathVariable String countryId) {
		StatesJson statesJson = new StatesJson();
		statesJson = userSignUpImpl.getStates(countryId);
		if (statesJson.getErrorCode().isEmpty()) {
			return new ResponseEntity<StatesJson>(statesJson, HttpStatus.OK);
		} else {
			return new ResponseEntity<StatesJson>(statesJson, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/usernameSuggestions/{userName}")
	public ResponseEntity<List<String>> suggestionUserNames(String userName) {

		return new ResponseEntity<List<String>>(userSignUpImpl.suggestUserNames(userName), HttpStatus.OK);
	}
}
