package com.registration.signup.json;

import lombok.Data;

@Data
public class UserResponseJson {
private User user;
private int errorCode;
private String errorMessage;

}
