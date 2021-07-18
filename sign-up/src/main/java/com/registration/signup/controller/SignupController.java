package com.registration.signup.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.registration.signup.json.User;
import com.registration.signup.service.UserSignUp;

@RestController
public class SignupController {
	
@Autowired	
UserSignUp userSignUpImpl;

@PostMapping(value="/users/register")
public ResponseEntity<User> registration(@Valid @RequestBody User user)
{
	return new ResponseEntity<User>(userSignUpImpl.registration(user),HttpStatus.CREATED);
}
	
}
