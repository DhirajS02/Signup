package com.registration.signup.json;

import java.util.List;

import lombok.Data;

@Data
public class StatesJson {
	private List<String> states;
	private String errorCode;
	private String errorMessage;
}
