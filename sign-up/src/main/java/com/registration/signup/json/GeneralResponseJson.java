package com.registration.signup.json;

import lombok.Data;

@Data
public class GeneralResponseJson<T> {
	private String data;
	private T value;
	private String errorCode;
	private String errorMessage;
}
