package com.registration.signup.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.registration.signup.json.UserResponseJson;

@ControllerAdvice
public class SignupExceptionHandler {

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<UserResponseJson> handleValidationExceptions(MethodArgumentNotValidException ex) {
		UserResponseJson userJson = new UserResponseJson();
//		Map<String, String> errors = new HashMap<>();
//		    ex.getBindingResult().getAllErrors().forEach((error) -> {
//		        String fieldName = ((FieldError) error).getField();
//		        String errorMessage = error.getDefaultMessage();
//		        errors.put(fieldName, errorMessage);});

		StringBuffer errorMessage = new StringBuffer();
		ex.getBindingResult().getAllErrors().stream().forEach((eacherror) -> {
			String error = eacherror.getDefaultMessage();
			errorMessage.append(",").append(error);
		});
		String errorMessages = errorMessage.substring(1, errorMessage.length());
		userJson.setErrorMessage(errorMessages);
		userJson.setErrorCode(-1);
		userJson.setUser(null);
		return new ResponseEntity<UserResponseJson>(userJson, HttpStatus.BAD_REQUEST);

	}
}
